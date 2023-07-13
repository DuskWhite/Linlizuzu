package com.xyxy.sys.service;

import com.xyxy.sys.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-18
 */
public interface ICommentService extends IService<Comment> {


    Comment getAllIdByName(String goodname, String ownername, String rentername);

    void addReputation(Integer score, Integer id);
}
