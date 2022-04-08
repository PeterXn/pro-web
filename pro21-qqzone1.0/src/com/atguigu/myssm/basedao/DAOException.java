package com.atguigu.myssm.basedao;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/27 22:04
 * @description TODO
 */
public class DAOException extends RuntimeException{
    public DAOException(String message) {
        super(message);
    }
}
