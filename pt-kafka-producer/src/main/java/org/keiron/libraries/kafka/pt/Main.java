package org.keiron.libraries.kafka.pt;

import org.keiron.libraries.kafka.pt.monitor.PrometheusExporter;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    PrometheusExporter.run();
    TestRunner.run();
  }

}