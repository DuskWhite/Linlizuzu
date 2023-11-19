package com.linli.linlizuzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linli.linlizuzu.common.QueryPage;
import com.linli.linlizuzu.entity.ZuGood;


import java.util.List;

/**
* @author Damumu
* @description 针对表【zu_good】的数据库操作Service
* @createDate 2023-11-16 19:42:16
*/

public interface ZuGoodService extends IService<ZuGood> {
    List<ZuGood> getProduces(QueryPage queryPage);
}
