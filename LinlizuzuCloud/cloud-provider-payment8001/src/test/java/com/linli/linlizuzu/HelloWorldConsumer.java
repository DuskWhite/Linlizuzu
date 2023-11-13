package com.linli.linlizuzu;

import com.rabbitmq.client.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class HelloWorldConsumer {
    //队列名
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //创建到服务器的连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //用于处理消费者接收到消息的回调函数
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            //`consumerTag`：是消费者在订阅队列时分配到的唯一标识符，用于在取消订阅时标识消费者。
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println(consumerTag + " [x] Received '" + s + "'");

            }
        };
        //
        //`QUEUE_NAME`：表示要消费的队列名。
        //`true`：表示自动确认消息，当消费者接收到一条消息后就会自动向消息队列发送确认消息，告诉消息队列这条消息已经被消费处理完成。
        //`deliverCallback`：表示接收到消息后的处理逻辑，将在 `handle` 方法中执行。
        //`consumerTag -> {}`：表示用于接收消费者标识的回调函数。在上面的 `basicCancel` 方法中同样需要传入该回调函数中的消费者标识才能成功取消消费者的订阅。
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            System.out.println(consumerTag);
        });
    }

}