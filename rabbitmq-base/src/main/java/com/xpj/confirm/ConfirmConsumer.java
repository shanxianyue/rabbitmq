package com.xpj.confirm;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

public class ConfirmConsumer {

	
	public static void main(String[] args) throws Exception {
		
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory() ;
		connectionFactory.setHost(Constants.IP);
		connectionFactory.setPort(Constants.PORT);
		connectionFactory.setUsername(Constants.USERNAME);
		connectionFactory.setPassword(Constants.PASSWORD);
		connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
		
		//2 获取Connection
		Connection connection = connectionFactory.newConnection();
		
		//3 通过Connection创建一个新的Channel
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_confirm_exchange";
		String routingKey = "confirm.#";
		String queueName = "test_confirm_queue";
		
		//4 声明交换机和队列 然后进行绑定设置, 最后制定路由Key
		channel.exchangeDeclare(exchangeName, "topic", true);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//5 创建消费者 
		channel.basicConsume(queueName, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
				System.out.println(consumerTag);
				System.out.println(envelope);
				System.out.println(properties);
				System.out.println(new String(body));
			}
		});
	}
}
