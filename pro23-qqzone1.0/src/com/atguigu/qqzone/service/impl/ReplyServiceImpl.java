package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.HostReplyService;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/31 11:06
 * @description TODO
 */
public class ReplyServiceImpl implements ReplyService {
    private ReplyDAO replyDAO = null;
    /**
     * 引入的是其他pojo对应的service接口，而不是DAO；
     * 其他pojo对应的业务逻辑封装在service层，我需要调用别人的业务逻辑方法，而不要去深入考虑人家内部的细节。
     */
    private HostReplyService hostReplyService = null;

    private UserBasicService userBasicService = null;

    @Override
    public List<Reply> getReplyListByTopicId(Integer topicId) {
        List<Reply> replyList = replyDAO.getReplyList(new Topic(topicId));
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);
            // 1.设置关联的auth信息
            UserBasic author = userBasicService.getUserBasicById(reply.getAuthor().getId());
            reply.setAuthor(author);

            // 2.设置关联的HostReply信息
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            reply.setHostReply(hostReply);
        }

        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }

    @Override
    public void delReply(Integer replyId) {
        // 1.根据id获取reply
        Reply reply = replyDAO.getReplyById(replyId);
        if (reply != null) {
            // 2.如果reply有关联的hostReply，则先删除hostReply
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            if (hostReply != null) {
                hostReplyService.delHostReply(hostReply.getId());
            }
            // 3.删除reply
            replyDAO.delReply(replyId);
        }
    }

    @Override
    public void delReplyList(Topic topic) {
        List<Reply> replyList = replyDAO.getReplyList(topic);
        if (replyList != null || replyList.size() > 0) {
            for (Reply reply : replyList) {
                delReply(reply.getId());
            }
        }
    }
}
