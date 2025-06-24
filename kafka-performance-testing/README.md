Performance Testing for Kafka

---
#### Configuration Guideline

- `plans/test-plan.yaml`
  - `vus`: number of virtual users, default = 0
  - `duration`: the time this script will be run, default = 0s
  - `iterations`: the total number of messages will be handled, default = -1 meaning not limit.
  - `producer.topic`: the topic name that message will be sent to. (required)


- `kafka-producer.yaml`
  - `bootstrap-servers`: the kafka broker endpoints (required) 