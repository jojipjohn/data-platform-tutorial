# Data Platform Tutorial: Ingestion with Kafka

### Objectives
* Set up a Kafka instance that we can push/pull messages to/from in subsequent exercises.
* Become familiar with simple Kafka operations.

### Prerequisites
* Docker
* Scala development environment

## Do This First

**IMPORTANT**: You need to set up a hosts file entry for a host named `kafka` that resides at `127.0.0.1`  (or docker-machine ip if using an older docker version).

The host file can be found at `/etc/hosts` on Unix-like systems.
On Windows machines machines it is located at `%SystemRoot%\System32\drivers\etc\host`

## Set up Kafka in a Container

We use the Spotify Kafka container because it comes with Zookeeper built in.
Let's start the container up in the foreground.

    $ docker pull spotify/kafka
    $ docker run -p 2181:2181 -p 9092:9092 --hostname kafka --name test_kafka --env ADVERTISED_PORT=9092 --env ADVERTISED_HOST=kafka spotify/kafka

If you stop the container, you can restart it this way:

    $ docker start test_kafka

Connect to the terminal using the following command:

    $ docker exec -it test_kafka /bin/bash

You can do this with several terminal sessions at once if needed.

Next, perform the following kafka operations to verify that Kafka is working:

### Create a Topic

    /opt/kafka_2.11-0.10.1.0/bin/kafka-topics.sh --zookeeper kafka:2181 --create --topic test_topic --partitions 3 --replication-factor 1

**IMPORTANT**: Remember the name of the topic you create.
You'll need it in a few minutes when we are writing code.

### Produce Messages

    /opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --topic test_topic --broker-list kafka:9092

Then type in strings, separated by pressing the enter key.

### Consume Messages

    /opt/kafka_2.11-0.10.1.0/bin/kafka-console-consumer.sh --topic test_topic --bootstrap-server kafka:9092 --from-beginning

You should be able to see the strings you typed into the producer.
Leave this consumer running. It is how we will verify later on that messages are getting pushed to Kafka.
