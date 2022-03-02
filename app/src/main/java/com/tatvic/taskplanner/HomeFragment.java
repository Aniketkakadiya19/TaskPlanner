package com.tatvic.taskplanner;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tatvic.taskplanner.Adapters.ToDoAdapter;
import com.tatvic.taskplanner.Model.ToDoModel;
import com.tatvic.taskplanner.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements DialogCloseListener {

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private DialogCloseListener listener;

    private List<ToDoModel> taskList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).hide();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        fab = view.findViewById(R.id.fab);

        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksAdapter = new ToDoAdapter(db, ((MainActivity) getActivity()), listener);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new com.tatvic.taskplanner.RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance(listener).show(getActivity().getSupportFragmentManager(), AddNewTask.TAG);

            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        if (db != null) {
            taskList = db.getAllTasks();
            Collections.reverse(taskList);
            tasksAdapter.setTasks(taskList);
        }
        tasksAdapter.notifyDataSetChanged();
    }

}