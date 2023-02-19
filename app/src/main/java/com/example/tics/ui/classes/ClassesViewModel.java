package com.example.tics.ui.classes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassesViewModel extends AndroidViewModel {

    private ArrayList<String> classesList;

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


    interface Callback<T> {
        void onResult(T result);
    }
}
