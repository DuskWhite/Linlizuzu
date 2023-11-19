package com.linli.linlizuzu.service.Impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linli.linlizuzu.common.QueryPage;
import com.linli.linlizuzu.entity.ZuGood;
import com.linli.linlizuzu.mapper.ZuGoodMapper;
import com.linli.linlizuzu.service.ZuGoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Damumu
 */
@Service
public class ZuGoodServiceImpl extends ServiceImpl<ZuGoodMapper, ZuGood> implements ZuGoodService {

    @Autowired
    private ZuGoodMapper zuGoodMapper;

    @Override
    public List<ZuGood> getProduces(QueryPage queryPage) {
        return zuGoodMapper.getProduces(queryPage);
    }
}
