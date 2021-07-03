package com.bjpowernode.dao;

import com.bjpowernode.VO.MyStudent;
import com.bjpowernode.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//这是一个接口，操作student表
public interface StudentDao {
   /*
   传入一个简单类型的数据：
    */
   public Student selectStudentById(Integer id);

   /*

  多个参数：使用  @Param("自定义参数名")
  还需要改动mapper文件
    */
   public List<Student> selectStudentByIdOrByAge(
           @Param("studentId") Integer id,
           @Param("studentAge") Integer age);

   int selectCountStudent();

   /*
      返回值是一个Map集合：
    */
   Map<Object,Object> selectMapById(Integer id);


   /*
     使用resultMap定义映射关系，
    */

    List<Student> selectAllStudents();

    List<MyStudent> selectAllMyStudents();

    List<MyStudent> selectAllMyStudents2();


}
