package com.xyxy.sys.entity;

import lombok.Data;

@Data
public class UserVo extends User {
    String userName;
    String passwordNow;
    String passwordOld;
    String passwordSure;
    String passwordNew;
}
