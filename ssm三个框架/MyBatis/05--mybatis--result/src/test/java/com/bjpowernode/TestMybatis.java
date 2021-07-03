package com.bjpowernode;

import com.bjpowernode.Util.MybatisUtil;
import com.bjpowernode.VO.MyStudent;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.domain.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestMybatis {
    @Test
    public void testselectStudentById() {
        /**
         * 使用mybatis的动态代理机制，使用SqlSession的getMapper(dao接口)方法
         *
         * getMapper(dao接口)方法能获取接口的实现类对象
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        Student student = studentDao.selectStudentById(3);
        //结果输出

        System.out.println(student);

        //关闭资源
        sqlSession.close();

    }

    @Test
    public void testselectStudentByIdOrByAge() {
        /**
         * 测试@Param
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        List<Student> student = studentDao.selectStudentByIdOrByAge(2, 900);
        //结果输出,两种都行，第一种会自动调用对象的toString()方法
        //System.out.println(student);
        for (Student stu : student
        ) {
            System.out.println(stu);
        }

        //关闭资源
        sqlSession.close();

    }


    @Test
    public void testselectCountStudent() {
        /**
         * 测试resultType
         */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        int num = studentDao.selectCountStudent();
            System.out.println(num);

        //关闭资源
        sqlSession.close();

    }


    @Test
    public void testselectMapById() {
         /*
    测试返回值为Map
     */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        //调用dao接口中的方法,查询数据
        Map<Object,Object> map = studentDao.selectMapById(3);

        System.out.println(map);

        //关闭资源
        sqlSession.close();

    }

    @Test
    public void testselectAllStudent() {
        /*
          测试resultMap,定义列名和java属性的关系
        */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> student = studentDao.selectAllStudents();
        for (Student stu : student
        ) {
            System.out.println(stu);
        }

        //关闭资源
        sqlSession.close();

    }

    @Test
    public void testselectAllMyStudents() {
        /*
          测试resultMap,定义列名和java属性的关系，列名和属性名不一样时:第一种解决方式
        */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<MyStudent> student  = studentDao.selectAllMyStudents();
        for (MyStudent stu : student
        ) {
            System.out.println(stu);
        }
        sqlSession.close();

    }

    @Test
    public void testselectAllMyStudents2() {
        /*
          测试resultMap,定义列名和java属性的关系，列名和属性名不一样时:第二种解决方式
        */
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<MyStudent> student  = studentDao.selectAllMyStudents2();
        for (MyStudent stu : student
        ) {
            System.out.println(stu);
        }
        sqlSession.close();

    }

}
