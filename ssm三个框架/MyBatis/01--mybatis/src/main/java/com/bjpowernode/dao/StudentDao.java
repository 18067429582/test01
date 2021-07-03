package com.bjpowernode.dao;

import com.bjpowernode.domain.Student;

import java.util.List;

//这是一个接口，操作student表
public interface StudentDao {
    //1，查询所有student的所有数据，返回的应该是一个集合
    public List<Student> selectStudents();

    //2,插入操作

    //int 表示插入操作后影响数据库的行数
    public int insertStudent(Student student);
}
