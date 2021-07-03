package com.bjpowernode;

import com.bjpowernode.Util.MybatisUtil;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.domain.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TestMybatis {
    @Test
    public void testselectStudents(){
        /**
        * 使用mybatis的动态代理机制，使用SqlSession的getMapper(dao接口)方法
        *
         * getMapper(dao接口)方法能获取接口的实现类对象
        */
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        StudentDao studentDao=sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        List<Student> studentList=studentDao.selectStudents();
        //结果输出
        for (Student stu:studentList) {
            System.out.println(stu);
        }
    }
    @Test
    public void testinsertStudent(){
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        StudentDao studentDao=sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,操作数据
        Student student1=new Student();
        student1.setId(4);
        student1.setName("张飞");
        student1.setEmail("zhang@qq.com");
        student1.setAge(900);
        int num=studentDao.insertStudent(student1);
        System.out.println(num==1? "插入成功":"插入失败");
    }

    @Test
    public void testdeletetudent(){
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        StudentDao studentDao=sqlSession.getMapper(StudentDao.class);

        //调用dao接口中的方法,操作数据
        Student student1=new Student();
        student1.setId(4);
        int num=studentDao.deleteStudent(student1);
        System.out.println(num==1? "删除成功":"删除失败");
    }

    @Test
    public void testupdatetudent(){
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        StudentDao studentDao=sqlSession.getMapper(StudentDao.class);

        //调用dao接口中的方法,操作数据
        Student student1=new Student();
        student1.setEmail("zhaowuzhaowu@qq.com");
        student1.setId(3);
        int num=studentDao.updateStudent(student1);
        System.out.println(num==1? "更新成功":"更新失败");
    }
}
