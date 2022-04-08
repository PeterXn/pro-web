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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/22 23:33
 * @description TODO
 */
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");

        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        /**
         * 获取当前类的所有方法,利用反射。
         */
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // 获取方法名
            String methodName = method.getName();
            System.out.println("method = " + method);
            if (operate.equals(methodName)) {
                try {
                    // 找到和operate同名的方法，再通过反射技术调用它
                    method.invoke(this, request, response);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("operate值不正确！");

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 设置编码
        // 2. 获取参数
        String fidStr = request.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = request.getParameter("remark");

        System.out.println("fid = " + fid);
        System.out.println("remark = " + remark);

        // 3. 执行更新
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        // 4. 资源跳转
        // 小BUG,跳转后的数据不是最新的。
        //super.processTemplate("index", request, response);

        // 此处需要重定向，目的是重新给IndexServlet发请求。
        response.sendRedirect("fruit.do");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);

            Fruit fruit = fruitDAO.getFruitByFid(fid);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            // 此处需要重定向，目的是重新给IndexServlet发请求。
            response.sendRedirect("fruit.do");
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oper = request.getParameter("oper");
        // 设置默认值，当前页为1
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
                // 如果keyword为null，须设置为空字符串"",否则在查询时会拼接成 %null%
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

        // 保存到session作用域，更新当前页的值
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        response.sendRedirect("fruit.do");
    }
}
