package com.bjpowernode;

import com.bjpowernode.Util.MybatisUtil;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.domain.Student;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMybatis {
    @Test
    public void testselectStudentByIf() {
        /*
           测试if
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        Student stu = new Student();
        //stu.setName("张三");
        //stu.setAge(25);


        stu.setName("张三");
        stu.setAge(50);
        List<Student> studentList = studentDao.selectStudentIf(stu);
        //结果输出
        for (Student stu1:studentList
             ) {
            System.out.println(stu1);
        }
        //关闭资源
        sqlSession.close();

    }

    @Test
    public void testselectStudentByWhere() {
        /*
        测试where和if，联合使用
         */
        //获取第 1 页，3 条内容,
        //PageHelper.startPage(2,3);
        PageHelper.startPage(1,3);
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        Student stu = new Student();
        stu.setName("张三");
        stu.setAge(5);
        List<Student> studentList = studentDao.selectStudentWhere(stu);
        for (Student stu1:studentList
        ) {
            System.out.println(stu1);
        }
        sqlSession.close();

    }


    @Test
    public void testFor() {
        /*
        测试foreach循环:传参list集合中的数据是基本类型
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(4);
        List<Student> studentList = studentDao.selectStudentFor(list);
        for (Student stu1:studentList
        ) {
            System.out.println(stu1);
        }
        sqlSession.close();

    }

    @Test
    public void testFor2() {
        /*
        测试foreach循环:传参list集合中的数据是java对象
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> list=new ArrayList<>();
        Student stu1=new Student();
        stu1.setId(1);
        list.add(stu1);
        Student stu2=new Student();
        stu2.setId(4);
        list.add(stu2);
        List<Student> studentList = studentDao.selectStudentForJavaObj(list);
        for (Student stu:studentList
        ) {
            System.out.println(stu);
        }
        sqlSession.close();

    }

}
