package org.keiron.libraries.kafka.pt.producer;

public interface Producer<T> {

  void send(String topic, T message);

  void send(String topic, String key, T message);

  boolean runTest(String topic);

  void close();

}
