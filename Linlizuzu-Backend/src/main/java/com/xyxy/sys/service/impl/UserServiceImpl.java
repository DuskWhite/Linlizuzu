package com.xyxy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyxy.sys.entity.Menu;
import com.xyxy.sys.entity.User;
import com.xyxy.sys.entity.UserRole;

import com.xyxy.sys.entity.UserVo;
import com.xyxy.sys.mapper.UserMapper;
import com.xyxy.sys.mapper.UserRoleMapper;
import com.xyxy.sys.service.IMenuService;
import com.xyxy.sys.service.IUserService;
import com.xyxy.common.utils.JwtUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private IMenuService menuService;


    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            loginUser.setPassword(null);
            // 创建jwt
            String token = jwtUtil.createToken(loginUser);
            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 根据token获取用户信息，redis
        //Object obj = redisTemplate.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginUser != null) {
            //User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
//           写入store缓存
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            data.put("id", loginUser.getId());
            // 角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            // 权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        // redisTemplate.delete(token);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // 写入用户表
        this.baseMapper.insert(user);
        // 写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }

    //添加用户头像
    @Override
    public void addAvatar(User user) {
        this.baseMapper.updateById(user);
    }

    @Override
    public void updateBalance(String rentername, String ownername, Double changenum) {
        this.userMapper.changeBalance(changenum, rentername);
        this.userMapper.changeBalanceAdd(changenum, ownername);
    }





    @Override
    public boolean updatePassword(UserVo userVo) {
        if(userVo.getPasswordNew().equals("")||userVo.getPasswordOld().equals("")||userVo.getPasswordSure().equals("")){
            return false;
        }
        else if (userVo.getPasswordNew().equals(userVo.getPasswordSure())){
            return false;
        }
        if(new BCryptPasswordEncoder().matches(userVo.getPasswordOld(),userVo.getPasswordNow())){
            userVo.setPassword(new BCryptPasswordEncoder().encode(userVo.getPasswordNew()));
            System.out.println(userVo.getUserName());
            userMapper.updateByUsername(userVo);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                .map(userRole -> {return userRole.getRoleId();})
                .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户表
        this.baseMapper.updateById(user);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null,user.getId(),roleId));
            }
        }
    }

    @Override
    public void deleteUserById(Integer id) {
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);
    }

    @Override
    public String getUserByName(String username) {
        return userMapper.getUserByName(username);
    }


    @Override
    public void addBalance(String username, Double changenum) {
        this.userMapper.changeBalanceAdd(changenum, username);
    }

    @Override
    public User getUserinfoByName(String username) {
        return this.userMapper.getUserinfoByName(username);
    }

    @Override
    public void updateReputation(String username){
        this.userMapper.updateReputation(username);
    }

}
