package com.xpj.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xpj.utils.Constants;

public class Producer4FanoutExchange {

	
	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
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
		String exchangeName = "test_fanout_exchange";
		//5 发送
		for(int i = 0; i < 10; i ++) {
			String msg = "Hello World RabbitMQ " + i + " FANOUT Exchange Message ...";
			channel.basicPublish(exchangeName, "", null , msg.getBytes()); 			
		}
		channel.close();  
        connection.close();  
	}
	
}
