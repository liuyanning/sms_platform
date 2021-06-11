package com.hero.wireless.sender;

import com.drondea.wireless.util.SuperLogger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务启动类
 *
 * @author Administrator
 */
public class SenderApp {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"/spring/spring_xml_config.xml"});
        context.start();
        SuperLogger.debug("发送器服务启动成功");
        synchronized (SenderApp.class) {
            while (true) {
                try {
                    SenderApp.class.wait();
                } catch (InterruptedException e) {
                    SuperLogger.debug("服务提供者出现异常:" + e);
                }
            }
        }
    }

}
