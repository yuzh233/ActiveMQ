package xyz.yuzh.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * Author: yu_zh
 * DateTime: 2018/07/24 18:06
 * 消息接收监听器
 */
public class SpringActiveMqReciveListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage)message;
            String text;
            try {
                text = textMessage.getText();
                System.out.println("SpringActiveMqReciveListener-> "+text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
