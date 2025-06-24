package org.keiron.libraries.kafka.performance.testing.monitor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class PrometheusExporter {

  public static void run() throws IOException {
    var registry = PrometheusMonitor.getRegistry();
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
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
  }

}
