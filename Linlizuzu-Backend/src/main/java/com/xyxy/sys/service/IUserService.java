package com.xyxy.sys.service;

import com.xyxy.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyxy.sys.entity.UserVo;


import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    void addUser(User user);

    void addAvatar(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUserById(Integer id);


    void updateBalance(String rentername,String ownername, Double changenum);

    boolean updatePassword(UserVo userVo);

    String getUserByName(String username);

    void addBalance(String username, Double changenum);

    User getUserinfoByName(String username);

    void updateReputation(String username);
}
