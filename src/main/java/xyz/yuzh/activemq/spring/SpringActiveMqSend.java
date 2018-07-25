package xyz.yuzh.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


/**
 * Created with IntelliJ IDEA.
 * Author: yu_zh
 * DateTime: 2018/07/24 17:21
 * 消息发送端
 */
public class SpringActiveMqSend {
    public static void main(String[] args) throws Exception{
        //初始化容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-activemq.xml");
        //获得JMS模板对象
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        //获得消息队列目的地（注意是jms接口的包，不是activemq的包。）
        Destination queueDestination = context.getBean(Destination.class);
        //发送消息
        jmsTemplate.send(queueDestination, (s) -> {
            StringBuilder message = new StringBuilder();
            for (int i = 1 ; i <= 100;i++){
                message.append("测试消息"+i+"\n");
            }
            return s.createTextMessage(message.toString());
        });
        Thread.sleep(100000);
    }
}
