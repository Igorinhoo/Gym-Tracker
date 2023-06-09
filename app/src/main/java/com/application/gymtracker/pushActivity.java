package com.application.gymtracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;

import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.application.gymtracker.Adapters.ExerciseAdapter;
import com.application.gymtracker.Database.RoomDBPush;
import com.application.gymtracker.Models.DateDialog;
import com.application.gymtracker.Models.Pull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class pushActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button add_btn, btn_search;
    TextView datepush, ab_time;
    EditText exe_name, reps_num, weight,sear;
    String currentDate;
    RecyclerView recycler_push;
    RoomDBPush database;
    List<Pull> push_list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ExerciseAdapter adapter;

    private final Handler handler = new Handler();
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);

        currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        datepush = findViewById(R.id.date);
        datepush.setText(currentDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        reps_num = findViewById(R.id.reps_num);
        exe_name = findViewById(R.id.exe_name);
        add_btn = findViewById(R.id.add_btn);
        btn_search = findViewById(R.id.btn_search);
        weight = findViewById(R.id.weight);
        datepush = findViewById(R.id.date);
        recycler_push = findViewById(R.id.recycler);
        sear = findViewById(R.id.ser);

        String ndate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        datepush.setText(ndate);

        androidx.appcompat.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(R.layout.actionbar);
        ab_time = findViewById(R.id.ab_time);
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ab_time.setText(time);


        datepush.setOnClickListener(view -> {
            DialogFragment datepicker = new DateDialog();
            datepicker.show(getSupportFragmentManager(), "Date picker");
        });


        database = RoomDBPush.getInstance(this);
        push_list = database.mainDAO().getAll();
        linearLayoutManager = new LinearLayoutManager(this);
        recycler_push.setLayoutManager(linearLayoutManager);

        adapter = new ExerciseAdapter(push_list,pushActivity.this, "01");
        recycler_push.setAdapter(adapter);

        add_btn.setOnClickListener((view) -> {
            Pull data = new Pull();
            data.setExe_name(exe_name.getText().toString());
            data.setReps_numb(reps_num.getText().toString());
            data.setWeight_pull(weight.getText().toString());
            data.setDate(datepush.getText().toString());

            database.mainDAO().insert(data);
            push_list.clear();
            push_list.addAll(database.mainDAO().getAll());
            adapter.notifyDataSetChanged();
        });


        btn_search.setOnClickListener(view -> {
            List<Pull> search = new ArrayList<>();
            for(Pull single : push_list){
                if (single.getExe_name().toLowerCase().contains(sear.getText().toString().toLowerCase())){
                    search.add(single);
                }
            }
            adapter.filter(search);
            adapter.notifyDataSetChanged();
        });
        doTheAutoRefresh();
    }
    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String ntime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                ab_time.setText(ntime);
                doTheAutoRefresh();
            }
        }, 1000);
    }
}