package com.quarkdata.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author maorl
 * @date 12/19/17.
 */
public class DownloadUtil {
    static Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    /**
     * 下载
     *
     * @param filePath
     * @param response
     */
    public static String download(String filePath, HttpServletResponse response) {
        logger.info("开始下载文件:"+filePath);
        String fileName = getFileName(filePath);
        try {
            // 设置Content-Disposition
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 读取文件
            InputStream ins = new FileInputStream(filePath);
            // 放到缓冲流里面
            BufferedInputStream bins = new BufferedInputStream(ins);
            // 获取文件输出IO流
            // 读取目标文件，通过response将目标文件写到客户端
            OutputStream outs = response.getOutputStream();
            BufferedOutputStream bouts = new BufferedOutputStream(outs);
            // 写文件
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            // 开始向网络传输文件流
            while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                bouts.write(buffer, 0, bytesRead);

            }
            bouts.flush();
            ins.close();
            bins.close();
            outs.close();
            bouts.close();
            logger.info("下载完成："+fileName);
            return fileName;
        } catch (Exception e) {
            logger.error("download from server error" + e);
        }
        return null;
    }


    /**
     * 截取文件名
     *
     * @param filePath
     * @return
     */
    private static String getFileName(String filePath) {
        int index = filePath.lastIndexOf("/");
        return filePath.substring(index + 1);
    }


}
