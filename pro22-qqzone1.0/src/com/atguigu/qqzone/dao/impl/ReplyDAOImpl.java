package com.atguigu.qqzone.dao.impl;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/31 15:20
 * @description TODO
 */
public class ReplyDAOImpl extends BaseDAO<Reply> implements ReplyDAO {
    @Override
    public List<Reply> getReplyList(Topic topic) {
        String sql = "select * from t_reply where topic = ?";
        return super.executeQuery(sql, topic.getId());
    }

    @Override
    public void addReply(Reply reply) {
        String sql = "insert into t_reply values(0,?,?,?,?)";
        super.executeUpdate(sql, reply.getContent(), reply.getReplyDate(), reply.getAuthor().getId(), reply.getTopic().getId());

    }

    @Override
    public void delReply(Integer id) {
        super.executeUpdate("delete from t_reply where id = ?", id);
    }

    @Override
    public Reply getReplyById(Integer id) {
        return load("select * from t_reply where id = ?",id);
    }
}
