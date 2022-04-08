package com.atguigu.myssm.basedao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/3/16 22:41
 * @description TODO
 */
public abstract class BaseDAO<T> {
    public Connection conn = null;
    public PreparedStatement psmt = null;
    public ResultSet rs = null;

    /**
     * T的Class对象
     */
    private Class entityClass;

    /**
     * <重点，难点>
     * <p>
     * 获取entityClass对象
     */
    public BaseDAO() {
        // getClass()是子类实例调用的。
        // getClass()获取Class对象，当前我们执行的是new FruitDAOImpl(),创建的是FruitDAOImpl的实例,
        // 那么子类的构造方法内部首先会调用父类(BaseDAO)的无参构造方法；
        // 所以此处的getClass()会被执行，但是getClass获取的是FruitDAOImpl的class,
        // 所以getGenericSuperclass()获取的是BaseDAO的Class.
        Type genericType = getClass().getGenericSuperclass();
        // ParameterizedType 参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        // BaseDAO只有一个泛型参数<T>,获取T的真实的类型
        Type actualType = actualTypeArguments[0];
        // actualType.getTypeName(): com.atguigu.fruit.pojo.Fruit
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO 构造方法出错，可能的原因是没有指定<>中的类型！");
        }
    }

    /**
     * 创建连接
     *
     * @return
     */
    protected Connection getConn() {
        return ConnUtil.getConn();
    }

    /**
     * 关闭资源
     *
     * @param rs
     * @param psmt
     * @param conn
     */
    protected void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
        // do nothing
    }

    /**
     * 执行更新，返回影响行数
     *
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql, Object... params) {
        boolean insertFlag = false;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = getConn();
            if (insertFlag) {
                psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                psmt = conn.prepareStatement(sql);
            }
            setParams(psmt, params);

            int count = psmt.executeUpdate();

            if (insertFlag) {
                // 获取自增的主键
                rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    return ((Long) rs.getLong(1)).intValue();
                }
            }

            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO ... executeUpdate 出错了");
        }
    }

    /**
     * 给预处理命令对象设置参数
     */
    private void setParams(PreparedStatement psmt, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * <重点，难点>
     * <p>
     * 执行查询，返回list
     */
    protected List<T> executeQuery(String sql, Object... params) {
        List<T> list = new ArrayList<>();
        try {
            conn = getConn();
            // 创建预处理命令对象
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            // 执行查询
            rs = psmt.executeQuery();

            // 通过rs可以获取结果集的元数据(描述结果集数据的数据)
            // 有什么列，什么类型
            ResultSetMetaData rsMetaData = rs.getMetaData();
            // 获取结果集的列数
            int columnCount = rsMetaData.getColumnCount();

            // 解析结果集
            while (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    // fid属性名称
                    /**
                     * select fid as id
                     * getColumnName()返回列名   fid
                     * getColumnLabel()返回别名  id
                     * String columnName = rsMetaData.getColumnName(i + 1);
                     */
                    String columnName = rsMetaData.getColumnLabel(i + 1);
                    // fid的值
                    Object columnValue = rs.getObject(i + 1);

                    // 给obj对象的property属性赋propertyValue值
                    setValue(entity, columnName, columnValue);
                }

                list.add(entity);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO ... executeQuery 出错了");
        }
        return list;
    }

    /**
     * <重点，难点>
     * <p>
     * <p>
     * 通过反射技术给obj对象的property属性赋propertyValue值
     */
    private void setValue(Object obj, String property, Object propertyValue) {
        Class clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(property);
            if (field != null) {
                // 获取当前字段的类型名称
                String typeName = field.getType().getName();
                // 字段类型如果是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义类的实例，
                // 然后将实例对象赋值给这个属性。
                if (isMyType(typeName)) {
                    // 假如是"com.atguigu.qqzoen.pojo.UserBasic"类型
                    Class typeNameClass = Class.forName(typeName);
                    // 带一个Integer参数的构造函数
                    Constructor constructor = typeNameClass.getDeclaredConstructor(Integer.class);
                    propertyValue = constructor.newInstance(propertyValue);
                }

                field.setAccessible(true);
                field.set(obj, propertyValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO ... setValue() 出错了");
        }
    }

    private static boolean isNotMyType(String typeName) {
        return "java.lang.Integer".equals(typeName)
                || "java.lang.String".equals(typeName)
                || "java.util.Date".equals(typeName)
                || "java.sql.Date".equals(typeName)
                || "java.lang.Double".equals(typeName);
    }

    private static boolean isMyType(String typeName) {
        return !isNotMyType(typeName);
    }

    /**
     * 执行查询，返回单个实体对象
     */
    protected T load(String sql, Object... params) {
        try {
            conn = getConn();
            // 创建预处理命令对象
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            // 执行查询
            rs = psmt.executeQuery();

            // 通过rs可以获取结果集的元数据(描述结果集数据的数据)
            // 有什么列，什么类型
            ResultSetMetaData rsMetaData = rs.getMetaData();
            // 获取结果集的列数
            int columnCount = rsMetaData.getColumnCount();

            // 解析结果集
            if (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    // fid
                    String columnName = rsMetaData.getColumnName(i + 1);
                    // fid的值
                    Object columnValue = rs.getObject(i + 1);

                    // 给obj对象的property属性赋propertyValue值
                    setValue(entity, columnName, columnValue);
                }

                return entity;
            }

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO ... load 出错了");
        }

        return null;
    }

    /**
     * 执行复杂查询，返回例如统计结果
     */
    protected Object[] executeComplexQuery(String sql, Object... params) {
        try {
            conn = getConn();
            // 创建预处理命令对象
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            // 执行查询
            rs = psmt.executeQuery();

            // 通过rs可以获取结果集的元数据(描述结果集数据的数据)
            // 有什么列，什么类型
            ResultSetMetaData rsMetaData = rs.getMetaData();
            // 获取结果集的列数
            int columnCount = rsMetaData.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];

            // 解析结果集
            if (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    // fid的值
                    Object columnValue = rs.getObject(i + 1);
                    columnValueArr[i] = columnValue;
                }
                return columnValueArr;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO ... executeComplexQuery 出错了");
        }

        return null;
    }
}
