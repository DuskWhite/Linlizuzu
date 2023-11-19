package com.linli.linlizuzu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linli.linlizuzu.config.OssConfig;
import com.linli.linlizuzu.entity.ZuGood;
import com.linli.linlizuzu.service.ZuGoodService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Damumu
 */
@RestController
@RequestMapping("good")
@Log4j2
public class ZuGoodController {

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private ZuGoodService zuGoodService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostConstruct
    public void all() throws JsonProcessingException {
        List<ZuGood> list = zuGoodService.list();
        ObjectMapper o = new ObjectMapper();
        for (ZuGood zuGood : list) {
            stringRedisTemplate.opsForValue().set("good:"+zuGood.getId(),o.writeValueAsString(zuGood));
        }
    }



    @PostMapping("/fileUpload.json")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        if (file.isEmpty()) {
            return "上传文件内容为空，请重新选择";
        }
        String tempFilePath = this.getClass().getResource("/").getPath();
        String fileName = file.getOriginalFilename();
        File tempFile = new File(tempFilePath + fileName);
        try {
            file.transferTo(tempFile);
            //return "上传成功" + tempFilePath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ossConfig.fileUpload(fileName, tempFilePath + fileName);
    }
}
