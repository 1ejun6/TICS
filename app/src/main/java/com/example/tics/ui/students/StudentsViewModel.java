package com.example.tics.ui.students;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsViewModel extends ViewModel {
    private MutableLiveData<List<Student>> studentsList;

    public StudentsViewModel() {
        studentsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Student>> getStudentsList() {
        return studentsList;
    }

    public void fetchStudentsList(Context context) { // add Context parameter
        String url = "http://192.168.1.14/studentlist.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Student> students = new ArrayList<>();

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject studentJson = response.getJSONObject(i);

                            String StudentName = studentJson.getString("StudentName");
                            int StudentID = studentJson.getInt("StudentID");
                            String StudentParentName = studentJson.getString("StudentParentName");
                            String StudentParentNo = studentJson.getString("StudentParentNo");
                            String StudentClassName = studentJson.getString("ClassName");

                            Student student = new Student(StudentID, StudentName, StudentParentName, StudentParentNo, StudentClassName);
                            students.add(student);
                        }

                        studentsList.setValue(students);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("StudentsViewModel", "Error fetching students list");
                }
        );

        queue.add(request);
    }
}
