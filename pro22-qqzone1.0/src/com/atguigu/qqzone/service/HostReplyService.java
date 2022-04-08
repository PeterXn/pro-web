package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.HostReply;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/31 11:12
 * @description TODO
 */
public interface HostReplyService {
    HostReply getHostReplyByReplyId(Integer replyId);

    /**
     * 根据id删除
     */
    void delHostReply(Integer id);
}
