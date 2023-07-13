package com.xyxy.sys.service;

import com.xyxy.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);
}
