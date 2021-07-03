package com.bjpowernode.Util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    private static SqlSessionFactory factory =null;
    static {
        //1，定义mybatis主配置文件的名称,从类路径的根开始（target/classes开始）
        String config = "mybatis.xml";
        try {
            //2,读取config文件
            InputStream in = Resources.getResourceAsStream(config);
            //3,创建SqlSessionFactory对象,使用SqlSessionFactoryBuilder对象
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取SqlSession的方法
    public static SqlSession getSqlSession(){
        SqlSession sqlSession=null;
        if (factory!=null){
            sqlSession=factory.openSession(true);//自动提交事务
        }
        return sqlSession;
    }
}
