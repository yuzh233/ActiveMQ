package xyz.yuzh.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * Author: yu_zh
 * DateTime: 2018/07/24 12:50
 */
public class TopicConsumer {
    public static void main(String[] args) throws Exception {
        // 1.创建一个连接工厂 （Activemq的连接工厂）参数：指定连接的activemq的服务
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.184.130:61616");
        // 2.获取连接
        Connection connection = connectionFactory.createConnection();
        // 3.开启连接
        connection.start();
        // 4.根据连接对象创建session
        // 第一个参数：表示是否使用分布式事务（JTA）
        // 第二个参数：如果第一个参数为false,第二个参数才有意义；表示使用的应答模式 ：自动应答，手动应答.这里选择自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.根据session创建Destination(目的地，queue topic,这里使用的是queue)
        Topic topic = session.createTopic("topic-test");
        // 6.创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 7.接收消息
        while (true) {
            //接收消息 （参数的值表示的是超过一定时间 以毫秒为单位就断开连接）
            Message message = consumer.receive(100000);
            //如果message为空，没有接收到消息了就跳出
            if (message == null) {
                break;
            }
            if (message instanceof TextMessage) {
                TextMessage messaget = (TextMessage) message;
                System.out.println(">>>获取的消息内容：" + messaget.getText());//获取消息内容
            }
        }
        // 第二种：
        // 设置监听器,其实开启了一个新的线程。
       /* consumer.setMessageListener((m) -> {
            // 接收消息，如果有消息才进入，如果没有消息就不会进入此方法
            if (m instanceof TextMessage) {
                TextMessage messaget = (TextMessage) m;
                try {
                    // 获取消息内容
                    System.out.println(">>>获取的消息内容：" + messaget.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(10000);// 睡眠10秒钟。*/

        // 9.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
