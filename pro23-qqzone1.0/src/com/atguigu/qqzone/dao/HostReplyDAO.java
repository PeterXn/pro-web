package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.HostReply;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/29 22:52
 * @description TODO
 */
public interface HostReplyDAO {
    /**
     * 根据replyId查询关联的HostReply实体
     * @param replyId
     * @return
     */
    HostReply getHostReplyByReplyId(Integer replyId);

    /**
     * 根据id删除
     */
    void delHostReply(Integer id);
}
