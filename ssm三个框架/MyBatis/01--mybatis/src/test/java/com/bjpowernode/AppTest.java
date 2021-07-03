package com.bjpowernode;

import com.bjpowernode.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void insertStudent() throws IOException {
        //访问mybatis，读取student数据。
        //1，定义mybatis主配置文件的名称,从类路径的根开始（target/classes开始）
        String config = "mybatis.xml";
        //2,读取config文件
        InputStream in = Resources.getResourceAsStream(config);
        //3,创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //4,创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //5,【重要的】取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //6，指定要执行的sql语句的标识。sql映射文件中的namespace+"."+标签的id值
        String sqlid = "com.bjpowernode.dao.StudentDao" + "." + "insertStudent";
        //7,执行sql语句，通过sqlid找到语句
        Student student=new Student();
        student.setId(4);
        student.setName("赵六");
        student.setEmail("123123");
        student.setAge(40);
        int sl=sqlSession.insert(sqlid,student);
        //mybatis默认是不会提交事务的，所以在delete，insert，update之后手动提交事务
        sqlSession.commit();
        //8,输出
        System.out.println(sl);
        //9，关闭资源
        sqlSession.close();
        assertTrue( true );
    }
}
