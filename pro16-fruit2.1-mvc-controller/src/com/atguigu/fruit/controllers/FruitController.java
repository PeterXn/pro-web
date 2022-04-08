package com.atguigu.fruit.controllers;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/22 23:33
 * @description TODO
 */
public class FruitController {
    FruitDAO fruitDAO = new FruitDAOImpl();

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        return "redirect:fruit.do";
    }

    private String edit(Integer fid, HttpServletRequest request) {
        if (fid != null) {
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            request.setAttribute("fruit", fruit);

            return "edit";
        }

        return "error";
    }

    private String delete(Integer fid) {
        if (fid != null) {
            fruitDAO.delFruit(fid);
            // 此处需要重定向，目的是重新给IndexServlet发请求。
            return "redirect:fruit.do";
        }

        return "error";
    }

    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 首次登录，初始化PageNo
        if (pageNo == null) {
            pageNo = 1;
        }

        // 处理pageNo和keyword
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            pageNo = 1;
            if (StringUtil.isEmpty(keyword)) {
                // 如果keyword为null，须设置为空字符串"",否则在查询时会拼接成 %null%
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            // 此处从上一页或下一页或直接地址栏输入网址;
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

        return "index";
    }


    private String add(String fname, Integer price, Integer fcount, String remark) {
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        return "redirect:fruit.do";
    }
}
