package io.study.kafka.springkafka.config.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerListener<K, V> implements ProducerListener<K, V> {

    @Override
    public void onSuccess(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata) {
        ProducerListener.super.onSuccess(producerRecord, recordMetadata);

        log.info("Produced -> [{}] : [partition: {}, offset: {}] [key: {}, {}]",
            producerRecord.topic(),
            recordMetadata.partition(),
            recordMetadata.offset(),
            producerRecord.key(),
            producerRecord.value()
        );
    }

    @Override
    public void onError(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        ProducerListener.super.onError(producerRecord, recordMetadata, exception);

        log.error("Error on Produced -> [{}] : [partition: {}, offset: {}] [key: {}, value: {}][{}]",
            producerRecord.topic(),
            recordMetadata.partition(),
            recordMetadata.offset(),
            producerRecord.key(),
            producerRecord.value(),
            exception.getMessage()
        );
    }

}
