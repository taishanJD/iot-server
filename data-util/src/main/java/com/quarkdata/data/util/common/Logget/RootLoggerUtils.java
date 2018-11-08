package com.quarkdata.data.util.common.Logget;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;

import javax.annotation.PostConstruct;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Writer;

/**
 * @program: data
 * @description: 在任意位置获得操作日志的工具类
 * @author: ning
 * @create: 2018-08-03 10:44
 **/

public class RootLoggerUtils {
    /** 
    * @Description: 获得开启
    * @Param:  
    * @return:  
    * @Author: ning
    * @Date: 2018/8/3 
    */
    //public static PipedReader reader = new PipedReader();
    public static void OpenLogRecord() {
        Logger root = Logger.getRootLogger();
        try {
            Appender appender = root.getAppender("WriterAppender");
            PipedReader reader = new PipedReader();
            Writer writer = new PipedWriter(reader);
            ((WriterAppender) appender).setWriter(writer);
            Thread t = new AppThread (reader);
            System.err.println("-----------------");
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
