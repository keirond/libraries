package org.keiron.libraries.kafka.performance.testing.producer;

interface Producer<T> {

  void send(String topic, T value);

  void send(String topic, String key, T message);

}
