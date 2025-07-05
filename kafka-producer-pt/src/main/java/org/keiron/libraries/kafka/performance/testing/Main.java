package org.keiron.libraries.kafka.performance.testing;

import org.keiron.libraries.kafka.performance.testing.monitor.PrometheusExporter;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    PrometheusExporter.run();
    TestRunner.run();
  }

}