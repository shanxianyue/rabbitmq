package com.xpj.ack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

public class AckProducer {

	
	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory() ;
		connectionFactory.setHost(Constants.IP);
		connectionFactory.setPort(Constants.PORT);
		connectionFactory.setUsername(Constants.USERNAME);
		connectionFactory.setPassword(Constants.PASSWORD);
		connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.confirmSelect();
		String exchange = "test_ack_exchange";
		String routingKey = "ack.save";


		for(int i =0; i<5; i ++){

			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("num", i);

			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.headers(headers)
					.build();
			String msg = "Hello RabbitMQ ACK Message " + i;
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());

		}
		
	}
}
