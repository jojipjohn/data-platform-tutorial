# Data Platform Tutorial: Web Service APIs

### Objectives
* Create a simple web service
* Connect it to a database
* Instrument the web service
* Collect metrics

### Prerequisites
* Maven 3
* Java 8
* Cassandra container from previous exercise
* cURL

### Containers

This tutorial makes use of the following containers:
* cassandra:3.0.13
* hopsoft/graphite-statsd

Since the graphite container hasn't been used before, you can start it up this way:

    $ docker run -d --name test_graphite -p 8081:80 -p2003-2004:2003-2004 -p 2023-2024:2023-2024 -p 8125:8125/udp -p 8126:8126 hopsoft/graphite-statsd

If you need to restart the container later:

    # docker start test_graphite

To verify it is working you can point your browser at `http://127.0.0.1:8081`.
You should see and empty graphite dashboard.

If you find that your Docker setup is getting low on resources, it would ok to stop the Kafka container (if it is running):

    $ docker stop test_kafka

## Springboot

Springboot is a popular framework for creating web services using Java.
It makes heavy use of dependency injection and conventions that may feel unfamiliar to newcomers.
Following the conventions ensure you don't have to worry about JSON, databases, threadpools, etc.

### Application Class

This is where Spring auto-configures itself by inspecting classes.

### Examine the Controller Class

#### Coding Steps
* <b>Step 1</b>: Enable Cassandra
* <b>Step 2</b>: ApiControllerTutorial: implement /getTweet
* <b>Step 3</b>: implement /getTweetsByUser
* <b>Step 4</b>: curl against the API
* <b>Step 5</b>: add instrumentation; enable metrics
* <b>Step 6</b>: curl the API some more
* <b>Step 7</b>: examine graphite

#### Running the API

    $ mvn clean package && java -jar target/dpt-apis-1.0.0.jar

#### Using cURL

Get a single tweet:

    $ curl http://localhost:8080/getTweet?tweetId=${TWEET_ID}

Get all tweets from a user:

    $ curl http://localhost:8080/getTweetsByUser?user=${SCREEN_NAME}

View metrics (in pretty format):

    $ curl http://localhost:8080/metrics | python -m json.tool

## Supplemental Help

### Bulk Loading Tweets

`Csv2Cass.java` can load a CSV of tweets into `demo.raw_tweets`.
It expects the CSV to have the following column ordering:

* col 0: tweet id
* col 1: timestamp
* col 2: screen name
* col 3: search term
* col 4: tweet contents

### Hammering Your API

We've created a script in `bin/hit_endpoint.sh` that can be used to call the `/getTweet` endpoint rapidly.
However, you first need to get the IDs of some valid tweets.
You can do this by logging into the Cassandra container and starting a `cqlsh` session:

    $ docker exec -it test_cassandra
    # cqlsh
    > select id from demo.raw_tweets limit 100;

Copy the tweet ids and paste them into the `hit_endpoint.sh` script (replacing the existing IDs).
Be sure to leave a few bogus IDs so that 404 responses will get measured.
