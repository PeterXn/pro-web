package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/31 10:45
 * @description TODO
 */
public class TopicController {
    private TopicService topicService = null;

    public String topicDetail(Integer id, HttpSession session) {
        Topic topic = topicService.getTopicById(id);
        session.setAttribute("topic", topic);

        return "frames/detail";
    }

    public String delTopic(Integer topicId) {
        topicService.delTopic(topicId);
        // 当前用户已保存在session中，故不用传
        return "redirect:topic.do?operate=getTopicList";
    }

    public String getTopicList(HttpSession session) {
        // 从session中获取当前用户信息
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        // 再次查询当前用户的关联的所有日志
        List<Topic> topicList = topicService.getTopicList(userBasic);
        // 设置一下关联的日志列表
        userBasic.setTopicList(topicList);
        // 再次更新friend信息，main.html页面中使用的friend这个key的信息
        session.setAttribute("friend", userBasic);
        return "frames/main";
    }

    public String addTopic(String title, String content,HttpSession session) {
        // 从session中获取当前用户信息
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        userBasic = new UserBasic(userBasic.getId());
        Topic topic = new Topic(0,title,content,new Date(),userBasic);
        topicService.addTopic(topic);

        // 当前用户已保存在session中，故不用传
        return "redirect:topic.do?operate=getTopicList";
    }
}
