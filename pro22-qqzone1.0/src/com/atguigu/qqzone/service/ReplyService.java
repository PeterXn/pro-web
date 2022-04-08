package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/31 11:04
 * @description TODO
 */
public interface ReplyService {
    // 根据topic的id获取有关联的所有回复
    List<Reply> getReplyListByTopicId(Integer topicId);

    void addReply(Reply reply);

    /**
     * 删除指定的回复
     * @param replyId
     */
    void delReply(Integer replyId);

    /**
     * 删除指定的日志关联的所有回复
     * @param topic
     */
    void delReplyList(Topic topic);
}
