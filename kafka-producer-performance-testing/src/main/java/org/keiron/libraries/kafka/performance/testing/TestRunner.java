package org.keiron.libraries.kafka.performance.testing;

import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.kafka.performance.testing.config.ConfigContext;
import org.keiron.libraries.kafka.performance.testing.config.OptionsConfig;
import org.keiron.libraries.kafka.performance.testing.monitor.PrometheusMonitor;
import org.keiron.libraries.kafka.performance.testing.producer.AvroProducer;
import org.keiron.libraries.kafka.performance.testing.producer.Producer;
import org.keiron.libraries.kafka.performance.testing.producer.ProtobufProducer;
import org.keiron.libraries.kafka.performance.testing.producer.StringProducer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

import static org.keiron.libraries.kafka.performance.testing.monitor.PrometheusMetricName.PRODUCE_MESSAGE;

@Slf4j
class TestRunner {

  private static final OptionsConfig config = ConfigContext.optionsConfig;
  private static final List<Producer<?>> producers = new ArrayList<>();

  public static void run() {
    int vus = config.getVus();
    int noOfProducersConf = config.getProducer().getNoOfProducers();
    int noOfProducers =
        noOfProducersConf > 0 ? noOfProducersConf : Math.min(Math.max(1, vus / 2000), 100);
    for (int i = 0; i < noOfProducers; i++) {
      producers.add(switch (config.getProducer().getProducerType()) {
        case "avro" -> new AvroProducer();
        case "protobuf" -> new ProtobufProducer();
        default -> new StringProducer();
      });
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
              runTask(index % noOfProducers);
              LockSupport.parkNanos(1_000);
            }
          });
        } catch (RejectedExecutionException e) {
          log.error("Rejected execution {}", e.getMessage());
        }
      });
    }
    producers.forEach(Producer::close);
    log.info("Run complete in {}", Duration.between(start, Instant.now()).toString());
  }

  private static void runTask(int index) {
    var startTime = Instant.now();
    boolean status = producers.get(index).runTest(config.getProducer().getTopic());
    PrometheusMonitor.timer(PRODUCE_MESSAGE.getName(), startTime,
        Tag.of("status", String.valueOf(status)));
  }

}
