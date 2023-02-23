package com.example.tics.ui.classes;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClassesViewModel extends AndroidViewModel {

    private ArrayList<String> classesList;
    private ArrayList<String> studentsList;

    public ClassesViewModel(@NonNull Application application) {
        super(application);
    }

    public void getClasses(Callback<ArrayList<String>> callback) {
        classesList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = "http://192.168.1.14/class.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("class");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                classesList.add(obj.getString("ClassName"));
                            }
                            callback.onResult(classesList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onResult(null);
            }
        });

        queue.add(stringRequest);
    }

    public void getStudentsForClass(String className, Callback<ArrayList<String>> callback) {
        studentsList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = "http://192.168.1.14/students.php?className=" + className;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String StudentName = obj.getString("StudentName");
                                int StudentID = obj.getInt("StudentID");
                                studentsList.add(StudentID + "    " + StudentName);
                            }
                            callback.onResult(studentsList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onResult(new ArrayList<>());
            }
        });
        queue.add(stringRequest);
    }

    public void getStudentDetails(int StudentID, Callback<Student> callback) {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        Log.d("URLStudentID", String.valueOf(StudentID));
        String url = "http://192.168.1.14/studentdetails.php?StudentID=" + StudentID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String StudentName = response.getString("StudentName");
                            String StudentParentName = response.getString("StudentParentName");
                            String StudentParentNo = response.getString("StudentParentNo");
                            Student student = new Student(StudentID, StudentName, StudentParentName, StudentParentNo, null);
                            callback.onResult(student);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onResult(null);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getTicCount(int studentID, Callback<Integer> callback) {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = "http://192.168.1.14/ticcount.php?StudentID=" + studentID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int ticCount = response.getInt("TicCount");
                            callback.onResult(ticCount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onResult(0);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onResult(0);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getTic(int studentID, Callback<ArrayList<Tic>> callback) {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = "http://192.168.1.14/tic.php?StudentID=" + studentID;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Tic> ticList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject ticJson = response.getJSONObject(i);
                                int ticCount = ticJson.getInt("TicCount");
                                String ticDate = ticJson.getString("TicDate");
                                String ticTime = ticJson.getString("TicTime");
                                byte[] picture = Base64.decode(ticJson.getString("Picture"), Base64.DEFAULT);
                                Tic tic = new Tic(ticCount, ticDate, ticTime, picture);
                                ticList.add(tic);
                            }
                            callback.onResult(ticList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onResult(new ArrayList<>());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onResult(new ArrayList<>());
            }
        });
        queue.add(jsonArrayRequest);
    }

    interface Callback<T> {
        void onResult(T result);
    }
}