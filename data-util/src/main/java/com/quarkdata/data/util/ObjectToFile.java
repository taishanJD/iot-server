package com.quarkdata.data.util;

import java.io.*;
import java.util.HashMap;

/**
 * Created by maorl on 10/24/17.
 */
class ObjectToFile {
    static void writeObject(HashMap<String, Object> map, String key) {
        try {
            FileOutputStream outStream = new FileOutputStream("/home/user/pypy/"+key+".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(map);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static HashMap<String,Object> readObject(String key){
        FileInputStream freader;
        try {
            freader = new FileInputStream("/home/user/pypy/"+key+".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            HashMap<String,Object> map;
            map = (HashMap<String, Object>) objectInputStream.readObject();
            return map;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



}

