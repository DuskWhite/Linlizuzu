package com.xyxy.sys.service.impl;

import com.xyxy.sys.entity.Comment;
import com.xyxy.sys.mapper.CommentMapper;
import com.xyxy.sys.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;


    @Override
    public Comment getAllIdByName(String goodname, String ownername, String rentername) {
        Integer goodId = commentMapper.getGoodIdByName(goodname);
        Integer ownerId = commentMapper.getOwnerIdByName(ownername);
        Integer renterId = commentMapper.getRenterIdByName(rentername);
        Comment comment = new Comment();
        comment.setGoodId(goodId);
        comment.setOwnerId(ownerId);
        comment.setRenterId(renterId);
        return comment;
    }

    @Override
    public void addReputation(Integer score, Integer id) {
        commentMapper.addReputation(score,id);
    }
}
