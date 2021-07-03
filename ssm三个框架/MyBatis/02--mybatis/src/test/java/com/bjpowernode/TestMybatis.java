package com.bjpowernode;

import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.dao.imp.studentDaoImp;
import com.bjpowernode.domain.Student;
import org.junit.Test;

import java.util.List;

public class TestMybatis {
    @Test
    public void testselectStudents(){
        /*
        *  需要获取：com.bjpowernode.dao.StudentDao
        *
        *  List<Student> students=studentDao.selectStudents();
        *  1,dao对象，类型是StudentDao，全限定名称是：com.bjpowernode.dao.StudentDao
        *      全限定名称 和 namespace是一致的
         *  2，dao的方法名称是selectStudents，这个方法名称就是mapper文件中的id值
         *
         *  mybatis 的动态代理：mybatis根据dao的方法调用，获取执行sql语句的信息，mybatis根据你的dao
         *  接口，创建出一个dao接口的实现类，被创建这个类的对象，完成sqlsession调用方法，访问数据库
         *
         *   具体操作看03-mybatis动态代理
         *
         */
        StudentDao studentDao=new studentDaoImp();
        List<Student> students=studentDao.selectStudents();
        for (Student stu:students
             ) {
            System.out.println(stu);
        }

    }
    @Test
    public void testinsertStudent(){
        StudentDao studentDao=new studentDaoImp();
        Student student1=new Student();
        student1.setId(7);
        student1.setName("习主席");
        student1.setEmail("xi@qq.com");
        student1.setAge(90);
        int num=studentDao.insertStudent(student1);
        System.out.println(num==1? "插入成功":"插入失败");
    }

    @Test
    public void testdeletetudent(){
        StudentDao studentDao=new studentDaoImp();
        Student student1=new Student();
        student1.setId(5);
        int num=studentDao.deleteStudent(student1);
        System.out.println(num==1? "删除成功":"删除失败");
    }

    @Test
    public void testupdatetudent(){
        StudentDao studentDao=new studentDaoImp();
        Student student1=new Student();
        student1.setEmail("zhaowuzhaowu@qq.com");
        student1.setId(3);
        int num=studentDao.updateStudent(student1);
        System.out.println(num==1? "更新成功":"更新失败");
    }
}
