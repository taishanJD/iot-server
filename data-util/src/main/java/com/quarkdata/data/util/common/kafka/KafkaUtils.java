//package com.quarkdata.iot.util.common.kafka;
//
//import PropertiesUtils;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.producer.Producer;
//
//import java.util.Properties;
//
///**
// * @author maorl
// * @date 1/25/18.
// */
//public  class KafkaUtils {
//    public static Producer getProduce(){
//        return KafkaProducer.getProducer();
//    }
//
//
//    public static Consumer getConsumer(){
//        Properties props = new Properties();
//        props.put("bootstrap.servers", PropertiesUtils.prop.get("bootstrap.servers"));
//        props.put("group.id",PropertiesUtils.prop.get("group.id"));
//        props.put("enable.auto.commit",PropertiesUtils.prop.get("enable.auto.commit"));
//        props.put("auto.commit.interval.ms",PropertiesUtils.prop.get("auto.commit.interval.ms"));
//        props.put("key.deserializer",PropertiesUtils.prop.get("key.deserializer"));
//        props.put("value.deserializer",PropertiesUtils.prop.get("value.deserializer"));
//        return new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
//    }
//}
