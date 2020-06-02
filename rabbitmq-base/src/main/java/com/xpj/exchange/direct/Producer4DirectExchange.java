package com.xpj.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xpj.utils.Constants;

public class Producer4DirectExchange {

	
	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory() ;
		connectionFactory.setHost(Constants.IP);
		connectionFactory.setPort(Constants.PORT);
		connectionFactory.setUsername(Constants.USERNAME);
		connectionFactory.setPassword(Constants.PASSWORD);
		connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_direct_exchange";
		String routingKey = "test.direct";
		//5 发送
		String msg = "Hello World RabbitMQ 4  Direct Exchange Message 111 ... ";
		channel.basicPublish(exchangeName, routingKey , null , msg.getBytes());
		System.out.println("---------------finish----------------");
		channel.close();
		connection.close();
	}
	
}
