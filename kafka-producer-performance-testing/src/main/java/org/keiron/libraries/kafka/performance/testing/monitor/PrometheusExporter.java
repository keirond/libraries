package org.keiron.libraries.kafka.performance.testing.monitor;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.kafka.performance.testing.config.ConfigEnv;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
public class PrometheusExporter {

  public static void run() throws IOException {
    var registry = PrometheusMonitor.getRegistry();
    int port = ConfigEnv.getEnvConfig("PORT", 8080);
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/metrics", httpExchange -> {
      String response = registry.scrape();
      byte[] bytes = response.getBytes();
      httpExchange.sendResponseHeaders(200, bytes.length);
      try (OutputStream os = httpExchange.getResponseBody()) {
        os.write(bytes);
      }
    });
    server.setExecutor(Executors.newSingleThreadExecutor());
    server.start();
    log.info("Started Prometheus Exporter on port {}", port);
  }

}
