package com.xyxy.common;



import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Damumu
 */
@Component
public class QuartzJob implements Job {
    @Autowired
    private Environment environment;

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        try {
            Process exec = Runtime.getRuntime().exec("mysqldump -uroot -p "+123456+" -A > /data/bak/all.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
