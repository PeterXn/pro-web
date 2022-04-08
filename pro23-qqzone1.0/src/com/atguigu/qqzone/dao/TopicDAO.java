package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/29 22:45
 * @description TODO
 */
public interface TopicDAO {
    // 获取指定用户的日志列表
    public List<Topic> getTopicList(UserBasic userBasic);

    // 添加日志
    public void addTopic(Topic topic);

    // 删除日志
    public void delTopic(Topic topic);

    // 获取特定日志信息
    public Topic getTopic(Integer id);
}
