package com.quarkdata.data.util.common.Logget;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * @program: data
 * @description: 测试log输出工具类
 * @author: ning
 * @create: 2018-08-02 09:50
 **/

public class AppThread extends Thread{
    PipedReader reader;
    //static StringBuffer sbLog=new StringBuffer();
    static List<String> logArray=new ArrayList<String>();
    public AppThread(PipedReader reader) {
        this.reader = reader;
    }

    public void run() {
        Scanner scanner = new Scanner(reader);
        int flag=1;
        logArray.clear();
        while (scanner.hasNext()) {
            // 获得下一行的数据（此处的nextLine，需要单独提取出来，因为有其他的操作，换句话说：如果存在有两个nextLine的话，获得的两条数据是不相同的）
            String needLog=scanner.nextLine();

            if(needLog.contains("|----------------")){
                flag=0;
                // 终止当前的子线程
                Thread.currentThread().interrupt();
            }
            if(flag==1){
                //sbLog.append(needLog+"\r\n");
                logArray.add(needLog+"\r\n");
                System.err.println(needLog+"---||||-----");
            }
        }
    }

    // 结束线程以后获取拼接的logg
    public static List<String> getAssembleLog(){
        System.err.println("----------------------------------------------------------------------------------------------------");
        return logArray;
    }
}
