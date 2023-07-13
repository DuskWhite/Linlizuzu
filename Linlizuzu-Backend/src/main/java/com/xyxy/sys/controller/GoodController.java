package com.xyxy.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.Good;
import com.xyxy.sys.entity.User;
import com.xyxy.sys.service.IGoodService;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-19
 */
@Api(tags = {"商品接口列表"})
@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private IGoodService goodService;

    @GetMapping("/all")
    public Result<List<Good>> getAllGood(){
        List<Good>list = goodService.list();
        return Result.success(list,"查询成功");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getGoodList(@RequestParam(value = "goodname",required = false) String goodname,
                                                  @RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(goodname), Good::getGoodname, goodname);
        wrapper.eq(StringUtils.hasLength(username), Good::getUsername, username);
        return getMapResult(pageNo, pageSize, wrapper);
    }

    @GetMapping("/Marketlist")
    public Result<Map<String,Object>> getMarketGoodList(@RequestParam(value = "goodname",required = false) String goodname,
                                                  @RequestParam(value = "status",required = false) String status,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(goodname), Good::getGoodname, goodname);
        wrapper.eq(Good::getStatus,status);
        return getMapResult(pageNo, pageSize, wrapper);
    }

    @NotNull
    private Result<Map<String, Object>> getMapResult(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize, LambdaQueryWrapper<Good> wrapper) {
        wrapper.orderByDesc(Good::getId);
        Page<Good> page = new Page<>(pageNo, pageSize);
        goodService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>(16);
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }


    @PostMapping
    public Result<?> addGood(@RequestBody Good good){
        goodService.save(good);
        return Result.success("新增商品成功");
    }

    @PutMapping
    public Result<?> updateGood(@RequestBody Good good){
        goodService.updateById(good);
        return Result.success("修改商品信息成功");
    }
    /**根据id查询用户信息 用于修改用户信息  */
    @GetMapping("/{id}")
    public Result<Good> getGoodById(@PathVariable("id") Integer id){
        Good good = goodService.getById(id);
        return Result.success(good);
    }

    @DeleteMapping("/{id}")
    public Result<Good> deleteGoodById(@PathVariable("id") Integer id){
        goodService.removeById(id);
        return Result.success("删除商品成功");
    }
}
