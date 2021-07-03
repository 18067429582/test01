package com.bjpowernode.dao;

import com.bjpowernode.domain.Student;

import java.util.List;

//这是一个接口，操作student表
public interface StudentDao {
    //在使用动态sql的时候，穿的参数必须是java对象
    List<Student> selectStudentIf(Student student);

    List<Student> selectStudentWhere(Student student);

    //foreach的使用：
    List<Student> selectStudentFor(List<Integer> idList);

    List<Student> selectStudentForJavaObj(List<Student> studentList);
}
