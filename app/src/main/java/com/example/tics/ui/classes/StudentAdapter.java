package com.example.tics.ui.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tics.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<String> studentList;
    private OnItemClickListener clickListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(String studentId);
    }

    public StudentAdapter(Context context, List<String> studentList, OnItemClickListener clickListener) {
        this.context = context;
        this.studentList = studentList;
        this.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.Student);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String student = studentList.get(position);
        holder.studentName.setText(student);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = student.split("\\s+")[0];
                clickListener.onItemClick(studentId);
            }
        });
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }
}