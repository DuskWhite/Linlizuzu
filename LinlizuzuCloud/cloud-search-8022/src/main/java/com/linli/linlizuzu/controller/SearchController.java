package com.linli.linlizuzu.controller;

import com.linli.linlizuzu.common.Result;
import com.linli.linlizuzu.entity.ZuGood;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Damumu
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("goods")
    public Result<?> search() throws IOException {

        GetIndexRequest request = new GetIndexRequest("good");
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);

        //响应状态
        restHighLevelClient.close();
        return Result.success();
    }
}
