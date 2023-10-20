package com.xyxy.interceptor;

import com.alibaba.fastjson2.JSON;
import com.xyxy.common.utils.JwtUtil;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token  = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证： " + token);
        if(token != null){
            try {
                User user = jwtUtil.parseToken(token, User.class);
                String tokenRedis = (String) redisTemplate.opsForValue().get(user.getUsername());
                if (tokenRedis!=null&&tokenRedis.equals(token)){
                    log.debug(request.getRequestURI() + "验证通过");
                    return true;
                }else {
                    log.debug(request.getRequestURI()+"没有找到redis缓存");
                    Result<Object> fail = Result.fail(20004, "当前用户已在其他客户端登录，请重新登录");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt无效，请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        return false; // 拦截
    }
}
