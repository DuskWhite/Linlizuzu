package com.xyxy.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.User;

import com.xyxy.sys.entity.UserVo;
import com.xyxy.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
@Api(tags = {"用户接口列表"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list,"查询成功");
    }

    @ApiOperation("用户名查询用户")
    @GetMapping("/username/{username}")
    public Result<User> getUserinfoByName(@PathVariable("username") String username){
        User user = userService.getUserinfoByName(username);
        return Result.success(user);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        User banner = userService.getUserinfoByName(user.getUsername());
        Map<String,Object> data = userService.login(user);
        if(data != null && banner.getStatus() != 0){
            return Result.success(data);
        }else if(banner.getStatus() !=1){
            return Result.fail(20002,"账号被封禁或出现异常");
        }else{
            return Result.fail(20002,"用户名或密码错误");
        }
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"登录信息无效，请重新登录");
    }

    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false) String phone,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        wrapper.orderByDesc(User::getId);
        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);
        Map<String,Object> data = new HashMap<>(16);
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<?> registerUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return Result.success("注册新用户成功");
    }

    @ApiOperation("添加新用户")
    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody UserVo userVo){
        if(userService.updatePassword(userVo)){
            return Result.success("更改成功,请重新登录");
        }
        else{
            return Result.fail("输入有误，应重新输入");
        }
    }

    @ApiOperation("修改用户信息")
    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

    @ApiOperation("修改用户头像")
    @PutMapping("/avatar")
    public Result<?> addAvatar(@RequestBody User user){
        userService.addAvatar(user);
        return Result.success("修改头像成功");
    }

    @ApiOperation("用户余额充值")
    @PutMapping("/addbalance")
    public Result<?> addBalance(@RequestParam("username") String username,@RequestParam("changenum") Double changenum){
        userService.addBalance(username,changenum);
        return Result.success("充值用户余额成功");
    }

    @ApiOperation("订单生成时余额充值与扣除")
    @PutMapping("/balance")
    public Result<?> updateGoodBalance(@RequestParam("rentername") String rentername,@RequestParam("ownername") String ownername,@RequestParam("changenum") Double changenum){
        if (rentername != null && ownername != null && changenum != null) {
            userService.updateBalance(rentername, ownername, changenum);
            return Result.success("修改用户余额成功");
        }else{
            return Result.fail("数据错误");
        }
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @ApiOperation("获取商品主人的用户头像")
    @GetMapping("/name/{username}")
    public Result<String> getUserAvatarByName(@PathVariable("username") String username){
        String url = userService.getUserByName(username);
        return Result.success(url,"获取用户头像成功");
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return Result.success("删除用户成功");
    }



}
