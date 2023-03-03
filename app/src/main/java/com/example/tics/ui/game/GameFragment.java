package com.example.tics.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.IP;
import com.example.tics.R;
import com.example.tics.databinding.FragmentGameBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private AutoCompleteTextView studentIdAutoCompleteTextView;
    private ArrayAdapter<String> studentIdArrayAdapter;
    private GameViewModel viewModel;

    private TextView studentNameTextView;
    private TextView studentParentNameNoTextView;
    private TextView classIdTextView;

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private List<Game> gameList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        studentIdAutoCompleteTextView = binding.autoCompleteTextView;
        studentNameTextView = binding.GameStudentName;
        studentParentNameNoTextView = binding.GameStudentParentNameNo;
        classIdTextView = binding.GameClassID;

        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        studentIdArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.item_studentid, new ArrayList<>());
        studentIdAutoCompleteTextView.setAdapter(studentIdArrayAdapter);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        gameList = new ArrayList<>();
        adapter = new GameAdapter(gameList, getContext());
        recyclerView.setAdapter(adapter);

        studentIdAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedStudentId = studentIdArrayAdapter.getItem(position);
            viewModel.setSelectedStudentId(selectedStudentId);
            fetchGameData(selectedStudentId);
            fetchStudentData(selectedStudentId);
        });

        fetchStudentIdData();

        return root;
    }

    private void fetchStudentIdData() {
        String url = IP.BASE_URL+"gamestudentid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String StudentID = jsonArray.getString(i);
                            studentIdArrayAdapter.add(StudentID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void fetchGameData(String StudentID) {
        String url = IP.BASE_URL+"game.php?StudentID=" + StudentID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        gameList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Game game = new Game();
                            game.setGameID(jsonObject.getString("GameID"));
                            game.setGameDate(jsonObject.getString("GameDate"));
                            game.setGameStartTime(jsonObject.getString("GameStartTime"));
                            game.setGameEndTime(jsonObject.getString("GameEndTime"));
                            game.setGameTicCount(jsonObject.getInt("GameTicCount"));
                            gameList.add(game);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void fetchStudentData(String StudentID) {
        String url = IP.BASE_URL+"gamestudent.php?StudentID=" + StudentID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String StudentName = jsonObject.getString("StudentName");
                            String StudentParentName = jsonObject.getString("StudentParentName");
                            String StudentParentNo = jsonObject.getString("StudentParentNo");
                            String ClassID = jsonObject.getString("ClassID");
                            studentNameTextView.setText(StudentName);
                            studentParentNameNoTextView.setText(StudentParentName+"- "+StudentParentNo);
                            classIdTextView.setText(ClassID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}