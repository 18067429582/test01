package com.bjpowernode;

import com.bjpowernode.Util.MybatisUtil;
import com.bjpowernode.domain.Student;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class MyApp2 {
    public static void main(String[] args) throws IOException {
        SqlSession sqlSession=MybatisUtil.getSqlSession();
        String sqlid = "com.bjpowernode.dao.StudentDao" + "." + "selectStudent";
        List<Student> sl=sqlSession.selectList(sqlid);
        for (Student stu:sl) {
            System.out.println(stu);
        }
        sqlSession.close();
    }
}
