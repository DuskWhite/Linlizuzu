package com.xyxy.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/** @author 吴沈涛 */
@Slf4j
@Service
@ServerEndpoint(value = "/myService/{userId}")
public class WebSocketService {



        /**
         * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
         */
        private static AtomicInteger onlineCount = new AtomicInteger(0);;
        /**
         * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
         */
        private static ConcurrentHashMap<String,WebSocketService> webSocketMap = new ConcurrentHashMap<>();

        /**
         * concurrent包的线程安全Set，用来存放每个客户端对应的session对象。
         */
        private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

        /**
         * 与某个客户端的连接会话，需要通过它来给客户端发送数据
         */
        private Session session;

        private String userId = "";

        /**
         * 连接建立成功调用的方法
         */
        @OnOpen
        public void onOpen(Session session,@PathParam("userId") String userId) {
            this.session = session;
            this.userId = userId;
            if(webSocketMap.containsKey(userId) && sessionMap.containsKey(userId)){
                webSocketMap.remove(userId);
                sessionMap.remove(userId);
                sessionMap.put(userId,session);
                webSocketMap.put(userId,this);
            }else{
                webSocketMap.put(userId,this);
                sessionMap.put(userId,session);
                addOnlineCount();
            }
            log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
        }

        /**
         * 连接关闭调用的方法
         */
        @OnClose
        public void onClose() {
            if(webSocketMap.containsKey(userId)){
                webSocketMap.remove(userId);
                subOnlineCount();
            }
            log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
        }

        /**
         * 收到客户端消息后调用的方法
         */
        @OnMessage
        public void onMessage(String message, Session session) {
            this.session = session;
            log.info("收到客户端消息 -> {}",message);
            //服务端收到客户端的消息并推送给客户端
            sendMessage(message);
        }

        /**
         * 发生错误时调用
         */
        @OnError
        public void onError(Session session, Throwable error) {
            log.error(error.getMessage());
        }

        /**
         * 实现服务器主动推送   可以通过controller调用此方法实现主动推送
         */
        public void sendMessage(String message){
            try {
                Set<Map.Entry<String, Session>> entries = sessionMap.entrySet();
                for (Map.Entry<String, Session> next : entries) {
                    Session session = next.getValue();
                    session.getBasicRemote().sendText(this.userId + "说" + message);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        public static synchronized int getOnlineCount() {
            return onlineCount.get();
        }

        public static synchronized void addOnlineCount() {
            WebSocketService.onlineCount.getAndIncrement();
        }

        public static synchronized void subOnlineCount() {
            WebSocketService.onlineCount.getAndDecrement();
        }



}
