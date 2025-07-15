### TODO

#### Spring Boot Modules
<module>std-kafka</module>\
<module>std-redis</module>\
<module>std-mongo</module>\
<module>std-cassandra</module>\

#### Spark Modules
<module>std-etl-machine</module>\
<module>std-database-migrator</module> (backfill, .. )

#### CDC !!
<module>std-cassandra-cdc</module>

#### Performance Testing
<module>k6-http-client-caller<module>\
<module>plain-cassandra-inserter</module>

#### Chaos Testing
##### cassandra 

- scene 1: a write op, 3 nodes, 2 nodes logs committed but crash, consistency level is QUORUM)
-> include retriable, the write committed logs shouldn't be replayed after 2 nodes recovery.