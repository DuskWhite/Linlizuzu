package com.xyxy.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.ZuMsg;
import com.xyxy.sys.service.ZuMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Damumu
 */
@RestController
@RequestMapping("message")
public class ZuMsgController {

    @Autowired
    private ZuMsgService zuMsgService;

    @GetMapping("history")
    public Result<List<ZuMsg>> getHistory(@RequestParam("senderId") int senderId, @RequestParam("receiverId") int receiverId){
        LambdaQueryWrapper<ZuMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZuMsg::getSenderId,senderId);
        queryWrapper.eq(ZuMsg::getReceiverId,receiverId);
        queryWrapper.orderByAsc(ZuMsg::getUpdateTime);
        List<ZuMsg> list = zuMsgService.list(queryWrapper);
        return Result.success(list,"成功获取消息记录");
    }

}
