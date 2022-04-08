package com.atguigu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/24 15:57
 * @description TODO
 */

@WebServlet(urlPatterns = {"/demo02"},
        initParams = {@WebInitParam(name = "initAnnName", value = "注解初始化")})
public class Demo01Servlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletConfig servletConfig = getServletConfig();
        String initName = servletConfig.getInitParameter("initName");
        System.out.println("initName = " + initName);
        String initAnnName = servletConfig.getInitParameter("initAnnName");
        System.out.println("initAnnName = " + initAnnName);

        ServletContext servletContext = getServletContext();
        String namespace = servletContext.getInitParameter("namespace");
        System.out.println("namespace = " + namespace);
    }
}
