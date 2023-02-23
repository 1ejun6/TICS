package com.example.tics;

public class Student {
    private int StudentID;
    private String StudentName;
    private String StudentParentName;
    private String StudentParentNo;
    private String ClassName;

    public Student(int StudentID, String StudentName, String StudentParentName, String StudentParentNo, String ClassName) {
        this.StudentID = StudentID;
        this.StudentName = StudentName;
        this.StudentParentName = StudentParentName;
        this.StudentParentNo = StudentParentNo;
        this.ClassName = ClassName;
    }

    public int getStudentID() {
        return StudentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getStudentParentName() {
        return StudentParentName;
    }

    public String getStudentParentNo() {
        return StudentParentNo;
    }

    public String getClassName() {
        return ClassName;
    }
}
