package com.xyxy.sys.mapper;

import com.xyxy.sys.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-18
 */
public interface CommentMapper extends BaseMapper<Comment> {
    

    Integer getGoodIdByName(String goodname);

    Integer getOwnerIdByName(String ownername);

    Integer getRenterIdByName(String rentername);

    void addReputation(Integer score, Integer id);
}
