package com.bjpowernode.VO;

//类名推荐和表名一致方便记忆
public class MyStudent {

    //属性名和列名保持一致
    private Integer StuId;
    private String StuName;
    private String StuEmail;
    private int StuAge;

    public MyStudent() {
    }

    public MyStudent(Integer stuId, String stuName, String stuEmail, int stuAge) {
        StuId = stuId;
        StuName = stuName;
        StuEmail = stuEmail;
        StuAge = stuAge;
    }

    public Integer getStuId() {
        return StuId;
    }

    public void setStuId(Integer stuId) {
        StuId = stuId;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getStuEmail() {
        return StuEmail;
    }

    public void setStuEmail(String stuEmail) {
        StuEmail = stuEmail;
    }

    public int getStuAge() {
        return StuAge;
    }

    public void setStuAge(int stuAge) {
        StuAge = stuAge;
    }

    @Override
    public String toString() {
        return "MyStudent{" +
                "StuId=" + StuId +
                ", StuName='" + StuName + '\'' +
                ", StuEmail='" + StuEmail + '\'' +
                ", StuAge=" + StuAge +
                '}';
    }
}
