package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/20 19:03
 * @description TODO
 */

/**
 * servlet3.0开始支持注解方式的注册
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost ...");
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String oper = request.getParameter("oper");
        Integer pageNo = 1;
        HttpSession session = request.getSession();
        String keyword = null;

        // 处理pageNo和keyword
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            // 说明是点击表单查询发送过来的请求
            // 此时 pageNo还原为1，
            pageNo = 1;
            // keyword应从请求参数中获取，然后存入session
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            // 此处从上一页或下一页或直接地址栏输入网址;
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);
            }
            // 从session作用域中获取keyword
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        // 保存到session作用域
        session.setAttribute("pageNo", pageNo);
        List<Fruit> fruitList = fruitDAO.getFruitListByPageNo(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);

        // 获取总记录数
        int fruitCount = fruitDAO.getFruitCount(keyword);
        // 总页数；每页12条记录
        int pageCount = (fruitCount + 12 - 1) / 12;
        // 传到页面
        session.setAttribute("pageCount", pageCount);


        // 此处的视图名称是 index
        // 那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去。
        // 逻辑视图名称： index
        // 物理视图名称： view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是：/         index       .html
        super.processTemplate("index", request, response);
    }
}
