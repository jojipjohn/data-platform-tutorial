# Data Platform Tutorial: Streaming and Batch ETL

### Objectives
* Pull data from Kafka
* Transform data
* Publish to Cassandra and a flat file

### Prerequisites
* Kafka setup and running `TwitterIngestTutorial` (or `TwitterIngestFinal`) to ingest twitter stream.

## Do This First

**IMPORTANT:** You need to set up a hosts file entry for a host named `cassandra` that resides at `127.0.0.1` (or docker-machine ip if using an older docker version).

## Set up Cassandra in a Container

    $ docker pull cassandra:3.0.13
    $ docker run -p 9042:9042 --hostname cassandra --name test_cassandra --env CASSANDRA_BROADCAST_ADDRESS=127.0.0.1 --env CASSANDRA_LISTEN_ADDRESS=127.0.0.1 cassandra:3.0.13

If you somehow stop the container, you can restart it again this way:

    $ docker start test_cassandra

We need to get our keyspace set up, so we'll connect to Cassandra and run some CQL commands.

    $ docker exec -it test_cassandra /bin/bash

### Create a Keyspace and Several Tables

    # cqlsh
    cqlsh> create keyspace if not exists demo with replication = {'class':'SimpleStrategy', 'replication_factor':1};
    cqlsh> create table if not exists demo.window_snapshots (when bigint, term text, value int, primary key ((when), term));
    cqlsh> create table if not exists demo.raw_tweets(id bigint, when bigint, sender text, value text, primary key((id), when, sender));
    cqlsh> create materialized view if not exists demo.tweets_by_user as select sender, id, when, value from demo.raw_tweets where when is not null and sender is not null primary key ((sender), when, id);

## Spark Batch Transform

We will use the Spark DataFrame API to read all data currently in the kafka topic, do a simple transform, and dump it to a csv file for later use.

### Start with `BatchTransformTutorial`

It has been stubbed out for this tutorial

### Coding Steps

1. Set master, app name, and cassandra host for the SparkSession builder.
2. Specify the format (kafka), kafka bootstrap servers, and topic to read data from.
3. Transform raw input into tweets dataframe.
    1. Filter inputs with null keys from when we were testing kafka.
    2. Select the value column and cast it from binary to string.
    3. Use `.as` to make it into a `Dataset[String]`.
    4. Decode the base 64 encoded tweet and extract the required fields.
4. Register a UDF for `Helpers.termSearch` with your search terms.
5. Select columns and explode of UDF in order wanted for output.
6. Write to CSV with header.

If you run into problems, `BatchTransformFinal` has the completed code.

#### (No IDE) Run `BatchTransformTutorial` from the command line

`mvn exec:java -Dexec.mainClass="com.svds.data_platform_tutorial.BatchTransformTutorial"`


## Spark Structured Streaming Transform

We will use the Spark Structured Streaming DataFrame API to read the stream of tweets from the kafka topic, archive them to Cassandra in decoded form, and do a windowed count of terms that will also be persisted in Cassandra.

**Note:** Spark Structured Streaming was released about a year ago with 2.0 and with 2.2 (releasing soon) will no longer be experimental. If you prefer the older RDD based Spark Streaming see `StreamTransformTutorial` and final version `StreamTransformFinal` for equivalent code.

### Start with `StructuredStreamingTutorial`

It has been stubbed out for this tutorial

### Coding Steps

1. We start code copied verbatim from the batch tutorial, for a stream make a small chang.
2. Create a `CassandraConnector`.
3. Write stream using `CassandraWriter`.
4. (optional) Test it! (add wait for termination).
5. Write windowed counts to Cassandra.
    1. Convert `when` to timestamp named `ts`.
    2. Register a watermark on `ts`.
    3. Search for terms with the UDF.
    4. Group by window (1 minute, 5 sec slide) and term then get the count.
    5. Convert to expected schema
    6. Write stream with CassandraWriter
6. Wait for termination.

If you run into problems, `StructuredStreamingFinal` has the completed code.

#### (No IDE) Run `StructuredStreamingTutorial` from the command line

`mvn exec:java -Dexec.mainClass="com.svds.data_platform_tutorial.StructuredStreamingTutorial"`
