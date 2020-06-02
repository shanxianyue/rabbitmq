package com.xpj.exchange.fanout;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

import java.io.IOException;

public class Consumer4FanoutExchange {

	public static void main(String[] args) throws Exception {

		//1 创建ConnectionFactory
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
		String exchangeName = "test_fanout_exchange";
		String exchangeType = "fanout";
		String queueName = "test_fanout_queue";
		String routingKey = "";	//不设置路由键
        //durable 是否持久化消息
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println(new String(body));
			}
		});

	}
}
