package com.linli.linlizuzu.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
	/**
	* 使用自己真实的地址
	*/
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    /**
    * 之前复制的 accessKeyId
    */
    private String accessKeyId = "LTAI5t9nxgWeTryaxmNpUAKC";
    /**
    * 之前复制的 accessKeySecret
    */
    private String accessKeySecret = "C5oeVvYYCM0HJPPy1mWipHMdtnu2Yz";
    /**
    * 自己新建的 bucketName
    */
    private String bucketName = "linlizuzu-bucket";

    /**
     * @param fileName     文件上传时的名字
     * @param tempFilePath 文件保存到本地时的临时目录，用于生产文件流
     * @return
     * @throws FileNotFoundException
     */
    public String fileUpload(String fileName, String tempFilePath) throws FileNotFoundException {
        // 用于在OSS上命名，建议格式 ：年月日/文件名.后缀名，此时可以 以时间建立一个文件夹保存上传的图片
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String transformDate = simpleDateFormat.format(new Date());
        String objectName = transformDate + "/" + System.currentTimeMillis() + "_" + fileName;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = new FileInputStream(tempFilePath);
        
        ossClient.putObject(bucketName, objectName, inputStream);

		// 返回一个带有时间限制的 访问连接，（此处坑很大）
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
        // 关闭OSSClient。
        ossClient.shutdown();
        return url.split("\\?")[0];
    }

   
    /*省略get set*/
}