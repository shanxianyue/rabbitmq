package com.xpj.ack;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

import java.io.IOException;

public class AckConsumer {


    public static void main(String[] args) throws Exception {


        ConnectionFactory connectionFactory = new ConnectionFactory() ;
        connectionFactory.setHost(Constants.IP);
        connectionFactory.setPort(Constants.PORT);
        connectionFactory.setUsername(Constants.USERNAME);
        connectionFactory.setPassword(Constants.PASSWORD);
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "test_ack_exchange";
        String queueName = "test_ack_queue";
        String routingKey = "ack.#";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 手工签收 必须要关闭 autoAck = false
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("-----------consume message----------");
                System.err.println("body: " + new String(body));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((Integer) properties.getHeaders().get("num") == 0) {
                    //requeue为true 表示要重回队列
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });

    }
}