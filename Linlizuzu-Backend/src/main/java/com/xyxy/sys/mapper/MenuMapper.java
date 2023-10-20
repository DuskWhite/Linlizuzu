package com.xyxy.sys.mapper;

import com.xyxy.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
