package com.xyxy.sys.service.impl;

import com.xyxy.sys.entity.Good;
import com.xyxy.sys.mapper.GoodMapper;
import com.xyxy.sys.service.IGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-19
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private GoodMapper goodMapper;

    @Override
    public void updateGoodStatus(int id){
        goodMapper.updateGoodStatus();
    }
}
