package com.linli.linlizuzu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author Damumu
 * 分页类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPage {
    private int page;
    private int pageSize;
    private HashMap<String,Object> queryMap;
}
