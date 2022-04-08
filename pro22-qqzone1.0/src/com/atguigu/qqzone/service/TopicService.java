package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/29 23:37
 * @description TODO
 */
public interface TopicService {
    // 查询特定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic);

    // 根据id获取特定topic
    Topic getTopicById(Integer id);

    /**
     * 根据id获取指定的topic信息，包含这个topic关联的author信息
     */
    public Topic getTopic(Integer id);

    /**
     * 删除日志
     * @param id
     */
    void delTopic(Integer id);

    void addTopic(Topic topic);
}
