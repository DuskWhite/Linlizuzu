package com.linli.linlizuzu;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class HelloWorldProducer {

    //队列名
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //创建到服务器的连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             //在 RabbitMQ 中，Channel 指的是在连接（Connection）上创建的一个逻辑通道，用来进行发送和接收消息的操作。每一个 Channel 都会拥有独立的 ID，可以根据这个 ID 与 RabbitMQ 服务器进行通信。
             //通过 channel，应用程序可以进行以下操作：
             //    声明队列（Queue）和交换器（Exchange）。
             //    将队列绑定到交换器上。
             //    发布消息到指定的交换器上。
             //    消费指定队列上的消息。
             //在 RabbitMQ 中，每个 Connection 都支持多个 Channel，应用程序可以根据自己的需求创建多个 Channel，从而实现并发和优化网络带宽的利用。但是需要注意，对于一个 Connection 可能存在的并发限制，在应用程序中需要合理控制 Channel 的数量。
             Channel channel = connection.createChannel()) {
            //这段代码是通过 RabbitMQ 的 Java 客户端创建一个名为 `QUEUE_NAME` 的队列。其中，代码参数的含义如下：
            //    `QUEUE_NAME`：队列名，即要创建的队列的名称。
            //    `false`：指定是否为持久化队列。设置为 `false` 表示创建的队列在 RabbitMQ 服务器重启后会被删除。
            //    `false`：指定是否为排他队列。设置为 `false` 表示队列可以被其他连接访问。
            //    `false`：指定队列是否应该自动删除。设置为 `false` 表示当没有任何消费者使用该队列时，该队列不会自动删除。
            //    `null`：指定队列的属性。设置为 `null` 表示不需要为队列设置任何属性。
            //当该方法被成功执行后，就可以使用 `channel.basicPublish()` 方法向队列发送消息，并使用 `channel.basicConsume()` 方法从队列中获取消息。该队列的状态信息也可以通过 `com.rabbitmq.client.AMQP.Queue.DeclareOk` 对象来进行监控。
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            String message = "Hello World";
            //这段代码是通过 RabbitMQ 的 Java 客户端向名为 `QUEUE_NAME` 的队列发送消息。具体来说，代码参数的含义如下：
            //    `""`：交换机名，这里使用空字符串表示直接发送到队列中而不经过交换机。
            //    `QUEUE_NAME`：队列名，即要发送消息的队列的名称。
            //    `null`：消息的其他属性，这里未设置任何属性。
            //    `message.getBytes(StandardCharsets.UTF_8)`：消息体的字节数组，即要发送的消息内容。
            //当该方法被成功执行后，消息就会被发送到 `QUEUE_NAME` 队列中等待消费者来处理。如果需要将消息发送到指定的交换机（Exchange），则需要将第一个参数 `""` 修改为实际的交换机名称，并设置好 Exchange 的类型、路由键等信息。
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

        }

    }
}