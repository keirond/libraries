package org.keiron.libraries.kafka.performance.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.TestPlanConfig;
import org.keiron.libraries.kafka.performance.testing.monitor.PrometheusMonitor;
import org.keiron.libraries.kafka.performance.testing.producer.StringProducer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

import static org.keiron.libraries.kafka.performance.testing.monitor.PrometheusMetricName.PRODUCE_MESSAGE;

@Slf4j
class TestRunner {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final TestPlanConfig config = ConfigContext.testPlanConfig;
  private static final List<StringProducer> stringProducers = new ArrayList<>();

  public static void run() {
    int vus = config.getVus();
    for (int i = 0; i < vus; i++) {
      stringProducers.add(new StringProducer());
    }

    Duration duration = config.getDuration();
    int iterations = config.getIterations();
    var atomicIterations = new AtomicInteger(iterations == -1 ? 0 : iterations);

    var start = Instant.now();
    try (ExecutorService executor = Executors.newFixedThreadPool(vus)) {

      IntStream.range(0, vus).parallel().forEach(index -> {
        try {
          executor.submit(() -> {
            while (Duration.between(start, Instant.now()).compareTo(duration) < 0 &&
                       (iterations == -1 || atomicIterations.getAndDecrement() > 0)) {
              StringProducer producer = stringProducers.get(index);
              runTask(producer);
              LockSupport.parkNanos(1_000);
            }
          });
        } catch (RejectedExecutionException e) {
          log.error("Rejected execution {}", e.getMessage());
        }
      });
    }
    log.info("Run complete in {}", Duration.between(start, Instant.now()).toString());
  }

  private static void runTask(StringProducer stringProducer) {
    var startTime = Instant.now();
    boolean status = true;
    try {
      String topic = config.getProducer().getTopic();
      var message = new TestMessage().setId(UUID.randomUUID().toString());
      stringProducer.send(topic, OBJECT_MAPPER.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      status = false;
      log.warn("Error parsing {}", e.getMessage());
    } finally {
      PrometheusMonitor.timer(PRODUCE_MESSAGE.getName(), startTime,
          Tag.of("status", String.valueOf(status)));
    }
  }

}
