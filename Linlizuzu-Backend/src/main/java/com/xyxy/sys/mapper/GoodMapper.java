package com.xyxy.sys.mapper;

import com.xyxy.sys.entity.Good;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-19
 */
public interface GoodMapper extends BaseMapper<Good> {

    void updateGoodStatus();
}
