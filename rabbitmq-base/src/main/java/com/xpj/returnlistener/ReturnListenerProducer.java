package com.xpj.returnlistener;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.xpj.utils.Constants;

public class ReturnListenerProducer {

	
	public static void main(String[] args) throws Exception {


		ConnectionFactory connectionFactory = new ConnectionFactory() ;

		connectionFactory.setHost(Constants.IP);
		connectionFactory.setPort(Constants.PORT);
		connectionFactory.setUsername(Constants.USERNAME);
		connectionFactory.setPassword(Constants.PASSWORD);
		connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String exchange = "test_return_exchange";
		String routingKey = "return.save";
		String routingKeyError = "abc.save";
		
		String msg = "Hello RabbitMQ Return Message";
		
		
		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange,
					String routingKey, BasicProperties properties, byte[] body) throws IOException {

				System.err.println("---------handle  return----------");
				System.err.println("replyCode: " + replyCode);
				System.err.println("replyText: " + replyText);
				System.err.println("exchange: " + exchange);
				System.err.println("routingKey: " + routingKey);
				System.err.println("properties: " + properties);
				System.err.println("body: " + new String(body));
			}
		});
		
		
//		channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
		
		channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
//		这里需要特别注意的是addReturnListener是一个非阻塞回调接口，如果提前使用了channel.close()，会关闭管道导致无法处理回调消息。
//		channel.close();
//		connection.close();
	}
}
