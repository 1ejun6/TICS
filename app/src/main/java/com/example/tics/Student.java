package com.example.tics;

public class Student {
    private String StudentName;
    private String StudentParentName;
    private int StudentID;
    private String StudentParentNo;

    public Student(int StudentID, String StudentName, String StudentParentName, String StudentParentNo) {
        this.StudentName = StudentName;
        this.StudentID = StudentID;
        this.StudentParentName = StudentParentName;
        this.StudentParentNo = StudentParentNo;
    }

    public String getStudentName() {
        return StudentName;
    }
    public int getStudentID() {
        return StudentID;
    }
    public String getStudentParentName() {
        return StudentParentName;
    }
    public String getStudentParentNo() { return StudentParentNo; }
}

