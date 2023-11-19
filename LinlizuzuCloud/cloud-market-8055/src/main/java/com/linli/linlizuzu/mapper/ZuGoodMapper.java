package com.linli.linlizuzu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linli.linlizuzu.common.QueryPage;
import com.linli.linlizuzu.entity.ZuGood;

import java.util.List;

/**
* @author Damumu
* @description 针对表【zu_good】的数据库操作Mapper
* @createDate 2023-11-16 19:42:16
* @Entity linlizuzu.entity.ZuGood
*/
public interface ZuGoodMapper extends BaseMapper<ZuGood> {


    List<ZuGood> getProduces(QueryPage queryPage);
}




