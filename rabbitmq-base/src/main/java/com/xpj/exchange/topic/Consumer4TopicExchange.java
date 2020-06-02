package com.xpj.exchange.topic;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

import java.io.IOException;

public class Consumer4TopicExchange {

	public static void main(String[] args) throws Exception {


		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(Constants.IP);
		connectionFactory.setPort(Constants.PORT);
		connectionFactory.setUsername(Constants.USERNAME);
		connectionFactory.setPassword(Constants.PASSWORD);
		connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
		
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        
        Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_topic_exchange";
		String exchangeType = "topic";
		String queueName = "test_topic_queue";
		//String routingKey = "user.*";
		String routingKey = "user.*";
		// 1 声明交换机 
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		// 2 声明队列
        //durable 是否持久化消息
		channel.queueDeclare(queueName, true, false, false, null);
		// 3 建立交换机和队列的绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);

        //参数：队列名称、是否自动ACK、Consumer
        //参数：队列名称、是否自动ACK、Consumer
		channel.basicConsume(queueName, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println(new String(body));
			}
		});

	}
}
