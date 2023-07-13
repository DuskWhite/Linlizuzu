package com.xyxy.sys.service;

import com.xyxy.sys.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-19
 */
public interface IGoodService extends IService<Good> {

    void updateGoodStatus(int id);
}
