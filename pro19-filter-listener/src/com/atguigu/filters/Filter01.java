package com.atguigu.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/25 17:09
 * @description TODO
 */
@WebFilter("*.do")
public class Filter01 implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter01 before filter ....");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Filter01 after filter ....");
    }

    @Override
    public void destroy() {

    }
}
