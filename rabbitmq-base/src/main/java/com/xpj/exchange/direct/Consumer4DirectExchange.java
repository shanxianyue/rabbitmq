package com.xpj.exchange.direct;

import com.rabbitmq.client.*;
import com.xpj.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4DirectExchange {

	public static void main(String[] args) throws Exception {
		
        ConnectionFactory connectionFactory = new ConnectionFactory() ;
        
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
		String exchangeName = "test_direct_exchange";
		String exchangeType = "direct";
		String queueName = "test_direct_queue";
		String routingKey = "test.direct";
		
		//表示声明了一个交换机
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		//表示声明了一个队列
		channel.queueDeclare(queueName, true, false, false, null);
		//建立一个绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);

		//推模式
		channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(consumerTag);
                System.out.println(envelope);
                System.out.println(properties);
                System.out.println(new String(body));
            }
        });

		//拉模式
       /* while (true){
            GetResponse response = channel.basicGet(queueName, true);
            if (response != null){
                System.out.println(new String(response.getBody()));
            }
        }*/

	}
}
