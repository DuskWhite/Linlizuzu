package com.xyxy.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.Comment;
import com.xyxy.sys.entity.Good;
import com.xyxy.sys.entity.User;
import com.xyxy.sys.service.ICommentService;
import com.xyxy.sys.service.IGoodService;
import com.xyxy.sys.service.IUserService;
import com.xyxy.sys.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-18
 */
@Slf4j
@RestController
@Api(tags = {"评论接口列表"})
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IGoodService goodService;
    @Autowired
    private IUserService userService;

    @PostMapping
    public Result<?> addComment(@RequestBody Comment comment){
        commentService.save(comment);
        return Result.success("新增评论成功");
    }

    @GetMapping("/getAllIdByName")
    public Result<?> getCommentInfo(@RequestParam("goodname") String goodname,@RequestParam("ownername") String ownername,@RequestParam("rentername") String rentername){
        Comment comment =  commentService.getAllIdByName(goodname,ownername,rentername);
        return Result.success(comment);
    }

    @ApiOperation("根据评价星级添加信誉分")
    @PutMapping("/score")
    public Result<?> addReputation(@RequestParam("score") Integer score,@RequestParam("id") Integer id){
        final int score3 = 3;
        final int score5 = 5;
        int addscore = 0;
        if(score <= 1){
            addscore = 0;
        }else if(score <= score3){
            addscore = 1;
        }else if (score <= score5){
            addscore = 2;
        }
        commentService.addReputation(addscore,id);
        return Result.success("添加信誉分成功");
    }

    @ApiOperation("根据被评价用户id获取所有评价")
    @GetMapping("/{id}")
    public Result<?> getCommentByUserId(@PathVariable("id") Integer id){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getOwnerId,id);
        List<Comment> commentList = commentService.list(wrapper);
        log.debug(String.valueOf(commentList));
        return Result.success(commentList);
    }

    @ApiOperation("转换goodid为name")
    @GetMapping("/changeGood")
    public Result<?> getGoodNameById(@RequestParam("goodId") String goodId){
        LambdaQueryWrapper<Good> goodser = new LambdaQueryWrapper<>();
        goodser.eq(Good::getId,goodId);
        Good good = goodService.getOne(goodser);
        return Result.success(good);
    }

    @ApiOperation("转换userid为name")
    @GetMapping("/changeUser")
    public Result<?> getUserNameById(@RequestParam("userId") String userId){
        LambdaQueryWrapper<User> userser = new LambdaQueryWrapper<>();
        userser.eq(User::getId,userId);
        User user = userService.getOne(userser);
        return Result.success(user);
    }

}
