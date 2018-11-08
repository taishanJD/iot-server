//package com.quarkdata.iot.util.common.kafka;
//import PropertiesUtils;
//import org.apache.kafka.clients.producer.*;
//import java.util.Properties;
//
///**
// * @author maorl
// * @date 1/25/18.
// */
//class KafkaProducer {
//        private volatile static Producer<String,String> producer;
//        private KafkaProducer(){}
//        static Producer<String,String> getProducer(){
//            if (producer == null) {
//                synchronized (KafkaProducer.class) {
//                    if (producer == null) {
//                        Properties props = new Properties();
//                        props.put("bootstrap.servers", PropertiesUtils.prop.get("bootstrap.servers"));
//                        props.put("acks", PropertiesUtils.prop.get("acks"));
//                        props.put("retries", PropertiesUtils.prop.get("retries"));
//                        props.put("batch.size", PropertiesUtils.prop.get("batch.size"));
//                        props.put("linger.ms", PropertiesUtils.prop.get("linger.ms"));
//                        props.put("buffer.memory", PropertiesUtils.prop.get("buffer.memory"));
//                        props.put("key.serializer", PropertiesUtils.prop.get("key.serializer"));
//                        props.put("value.serializer", PropertiesUtils.prop.get("value.serializer"));
//                        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
//                    }
//                }
//            }
//            return producer;
//        }
//
//}
