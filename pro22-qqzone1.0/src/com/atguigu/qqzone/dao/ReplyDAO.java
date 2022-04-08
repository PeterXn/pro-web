package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/29 22:49
 * @description TODO
 */
public interface ReplyDAO {
    /**
     * 获取指定日志的回复列表
     * @param topic
     * @return
     */
    List<Reply> getReplyList(Topic topic);

    /**
     * 添加回复
     * @param reply
     */
    void addReply(Reply reply);

    /**
     * 删除回复
     * @param id
     */
    void delReply(Integer id);

    /**
     * 根据id获取指定的Reply
     */
    Reply getReplyById(Integer id);
}
