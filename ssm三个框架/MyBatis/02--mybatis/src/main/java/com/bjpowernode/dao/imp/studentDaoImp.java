package com.bjpowernode.dao.imp;

import com.bjpowernode.Util.MybatisUtil;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.domain.Student;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class studentDaoImp implements StudentDao {
    @Override
    public List<Student> selectStudents() {
        //1,获取SqlSession对象
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        //2,组织唯一标识
        String sqlid = "com.bjpowernode.dao.StudentDao.selectStudent";
        //3，执行sql语句
        List<Student> sl=sqlSession.selectList(sqlid);
        sqlSession.close();
        return sl;
    }

    @Override
    public int insertStudent(Student student) {
        Student student1=new Student();

        //1,获取SqlSession对象
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        //2,组织唯一标识
        String sqlid = "com.bjpowernode.dao.StudentDao.insertStudent";
        //3，执行sql语句
        int sl=sqlSession.insert(sqlid,student1);
        sqlSession.close();
        return sl;
    }

    @Override
    public int deleteStudent(Student student) {
        Student student1=new Student();

        //1,获取SqlSession对象
        SqlSession sqlSession= MybatisUtil.getSqlSession();
        //2,组织唯一标识
        String sqlid = "com.bjpowernode.dao.StudentDao.deleteStudent";
        //3，执行sql语句
        int num=sqlSession.delete(sqlid,student1);
        sqlSession.close();
        return num;
    }

    public int updateStudent(Student student) {
        Student student1 = new Student();

        //1,获取SqlSession对象
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        //2,组织唯一标识
        String sqlid = "com.bjpowernode.dao.StudentDao.updateStudent";
        //3，执行sql语句
        int num = sqlSession.update(sqlid, student1);
        sqlSession.close();
        return num;
    }
}