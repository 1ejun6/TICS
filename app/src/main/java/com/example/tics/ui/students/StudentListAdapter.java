package com.example.tics.ui.students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tics.R;
import com.example.tics.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    public List<Student> StudentList;

    public StudentListAdapter(List<Student> students) {
        StudentList = students;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = StudentList.get(position);

        // Bind the data to the views in the CardView layout
        holder.StudentIDNameClassName.setText(student.getClassName()+"- "+student.getStudentID());
        holder.StudentParentName.setText(student.getStudentParentName());
        holder.StudentParentNo.setText(student.getStudentParentNo());
        holder.StudentName.setText(student.getStudentName());
    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView StudentIDNameClassName;
        public TextView StudentParentName;
        public TextView StudentParentNo;
        public TextView StudentName;

        public ViewHolder(View itemView) {
            super(itemView);
            StudentIDNameClassName = itemView.findViewById(R.id.Student_StudentIDClassName);
            StudentParentName = itemView.findViewById(R.id.Student_ParentName);
            StudentParentNo = itemView.findViewById(R.id.Student_ParentNo);
            StudentName = itemView.findViewById(R.id.Student_StudentName);
        }
    }
}

