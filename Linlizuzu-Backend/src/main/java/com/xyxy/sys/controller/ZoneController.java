package com.xyxy.sys.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Damumu
 */
@Slf4j
@Api(tags = {"个人空间接口列表"})
@RestController
@RequestMapping("/zone")
public class ZoneController {


    @PostMapping("/upload")
    //MultipartFile接受前台传过来的文件*/
    public String addFile(MultipartFile file, HttpServletRequest req){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //设置保存路径，首先获取类的位置
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String realPath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()+"\\src\\main\\resources\\static\\image/";
        //获取当前日期对所上传的文件进行归类保存
        System.out.println(realPath);
        String format = sdf.format(new Date());
        File folder = new File(realPath+format);
        boolean flag;
        //判断是否为目录
        if (!folder.isDirectory()){
            //不是目录就创建目录
            flag = folder.mkdirs();
            log.info("目录创建状态"+flag);
        }
        //给上传的文件重命名
        //获取文件名称
        String oldName = file.getOriginalFilename();
        //设置新文件名
        assert oldName != null : "文件名不能为空！";
        String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{
            //文件保存操作
            file.transferTo(new File(folder,newName));

            //生成文件访问路径
            return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/uploadFile/"+format+newName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "上传失败";
    }
}
