package com.atguigu.myssm.listeners;

import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/27 23:28
 * @description TODO
 */

/**
 * 监听上下文启动，在上下文启动的时候去创建IOC容器，然后将其保存到application作用域中，
 * 后面中央控制器再从application作用域中去获取IOC容器。
 * @author Peter
 */
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized ... Servlet上下文对象初始化动作被监听到了......");
        // 1.获取ServletContext上下文对象
        ServletContext application = servletContextEvent.getServletContext();
        // 2.获取上下文的初始化参数
        String path = application.getInitParameter("contextConfigLocation");
        // 3.创建IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        // 4.将IOC容器保存到application作用域
        application.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
