package org.keiron.libraries.cassandra.performance.testing.monitor;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.keiron.libraries.cassandra.performance.testing.config.ConfigEnv;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

@Slf4j
public class PrometheusExporter {

  public static void run() throws IOException {
    var registry = PrometheusMonitor.getRegistry();
    int port = ConfigEnv.getEnvConfig("PORT", 8080);
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/metrics", httpExchange -> {
      String response = registry.scrape();
      if (response.isBlank()) {
        log.warn("Prometheus registry returned empty scrape");
        response = "# No metrics available\n";
      }

      byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
      httpExchange.getResponseHeaders()
          .set("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
      httpExchange.sendResponseHeaders(200, bytes.length);
      try (OutputStream os = httpExchange.getResponseBody()) {
        os.write(bytes);
        os.flush();
      }
    });
    server.setExecutor(Executors.newSingleThreadExecutor());
    log.info("Started Prometheus Exporter on port {}", port);
    server.start();
  }

}
