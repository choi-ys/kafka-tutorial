package io.study.springkafkabatch.config.kafka;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class HashBasedPartitioner implements Partitioner {

    @Override
    public int partition(
        String topic,
        Object key,
        byte[] keyBytes,
        Object value,
        byte[] valueBytes,
        Cluster cluster
    ) {
        if (key == null) {
            return ThreadLocalRandom.current().nextInt(cluster.partitionCountForTopic(topic));
        }
        return Math.abs(key.hashCode()) % cluster.partitionCountForTopic(topic);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
    }

}
