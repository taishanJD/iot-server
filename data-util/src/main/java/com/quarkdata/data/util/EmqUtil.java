package com.quarkdata.data.util;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class EmqUtil {

    private static Logger logger = Logger.getLogger(EmqUtil.class);

    public static MqttClient getEmqConnect(String broker, String clientId){
        MqttClient sampleClient = null;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);

            /** 设置会话心跳和超时时间，避免以下异常
             * 已断开连接 (32109) - java.io.EOFException
             at org.eclipse.paho.client.mqttv3.internal.CommsReceiver.run(CommsReceiver.java:181)
             at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
             at java.util.concurrent.FutureTask.run(FutureTask.java:266)
             at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180)
             at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293)
             at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
             at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
             at java.lang.Thread.run(Thread.java:745)
             Caused by: java.io.EOFException
             at java.io.DataInputStream.readByte(DataInputStream.java:267)
             at org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream.readMqttWireMessage(MqttInputStream.java:92)
             at org.eclipse.paho.client.mqttv3.internal.CommsReceiver.run(CommsReceiver.java:133)
             ... 7 more
             */
            // 设置超时时间
            connOpts.setConnectionTimeout(10);
            // 设置会话心跳时间
            connOpts.setKeepAliveInterval(20);

            connOpts.setUserName("root");
            connOpts.setPassword("123456".toCharArray());
            sampleClient.connect(connOpts);
            logger.info("Connected to broker: " + broker);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return sampleClient;
    }

    public static MqttClient getEmqConnect(String broker, String clientId,String userName,String password){
    	MqttClient sampleClient = null;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);

            /** 设置会话心跳和超时时间，避免以下异常
             * 已断开连接 (32109) - java.io.EOFException
             at org.eclipse.paho.client.mqttv3.internal.CommsReceiver.run(CommsReceiver.java:181)
             at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
             at java.util.concurrent.FutureTask.run(FutureTask.java:266)
             at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180)
             at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293)
             at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
             at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
             at java.lang.Thread.run(Thread.java:745)
             Caused by: java.io.EOFException
             at java.io.DataInputStream.readByte(DataInputStream.java:267)
             at org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream.readMqttWireMessage(MqttInputStream.java:92)
             at org.eclipse.paho.client.mqttv3.internal.CommsReceiver.run(CommsReceiver.java:133)
             ... 7 more
             */
            // 设置超时时间
            connOpts.setConnectionTimeout(10);
            // 设置会话心跳时间
            connOpts.setKeepAliveInterval(20);

            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            sampleClient.connect(connOpts);
            logger.info("Connected to broker: " + broker);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return sampleClient;
    }
}
