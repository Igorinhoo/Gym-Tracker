package com.application.gymtracker.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.gymtracker.Database.RoomDB;
import com.application.gymtracker.Database.RoomDBPush;
import com.application.gymtracker.Models.Pull;
import com.application.gymtracker.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener{

    private List<Pull> pull;
    private Activity context;
    private RoomDB database_pull;
    private RoomDBPush database_push;
    private String editDate, code;
    private EditText name, reps, weigth;
    private TextView date;
    private Button cancel_btn, edit_save;
    private DatePickerDialog datePickerDialog;
    private Calendar c;

//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
//        c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
//
//        editDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
//        date.setText(editDate);
//        try {
//            Toast.makeText(context,"Dziala", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }

    public ExerciseAdapter(List<Pull> pull, Activity context, String code) {
        this.pull = pull;
        this.context = context;
        this.code = code;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rows,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        Pull data = pull.get(position);

        database_pull = RoomDB.getInstance(context);
        database_push = RoomDBPush.getInstance(context);

        holder.exe_name_row.setText(data.getExe_name());
        holder.reps_row.setText(data.getReps_numb());
        holder.weight_row.setText(data.getWeight_pull());
        holder.date_row.setText(data.getDate());

        holder.settings.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.settings);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.edit:
                        showEditDialog(data);
                        break;
                    case R.id.delete:
                        if(code.equals("00")){
                            database_pull.mainDAO().delete(data);
                        }
                        else if(code.equals("01")){
                            database_push.mainDAO().delete(data);
                        }
                        pull.remove(data);
                        notifyDataSetChanged();
                        return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }
    @Override
    public int getItemCount() {
        return pull.size();
    }

    public void filter(List<Pull> search){
        pull = search;
        notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView exe_name_row, reps_row, weight_row, date_row;
        public ImageView settings;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exe_name_row =itemView.findViewById(R.id.exe_name_row);
            reps_row = itemView.findViewById(R.id.reps_row);
            weight_row = itemView.findViewById(R.id.weight_row);
            date_row = itemView.findViewById(R.id.date_row);
            settings = itemView.findViewById(R.id.threedot_img);
        }
    }

    private void showEditDialog(Pull data) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_dialog);

        name = dialog.findViewById(R.id.edit_name);
        reps = dialog.findViewById(R.id.edit_reps);
        weigth = dialog.findViewById(R.id.edit_weigth);
        date = dialog.findViewById(R.id.edit_date);
        name.setText(data.getExe_name());
        reps.setText(data.getReps_numb());
        weigth.setText(data.getWeight_pull());
        date.setText(data.getDate());

//        date.setOnClickListener(view -> {
//            DialogFragment datepicker = new DateDialog();
//            FragmentActivity activity = (FragmentActivity)(context);
//            FragmentManager fm = activity.getSupportFragmentManager();
//            datepicker.show(fm,"Date picker edit");
//        });

        date.setOnClickListener(view -> {
            java.util.Calendar cp = java.util.Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(context,
                    (datePicker, year, month, day) -> {
                        c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, day);
                        editDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                        date.setText(editDate);
                    }, cp.get(java.util.Calendar.YEAR),cp.get(java.util.Calendar.MONTH),cp.get(java.util.Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        cancel_btn = dialog.findViewById(R.id.edit_cancel);
        cancel_btn.setOnClickListener(view -> dialog.dismiss());

        edit_save = dialog.findViewById(R.id.edit_save);
        edit_save.setOnClickListener((view) -> {
            pull.clear();
            if(code.equals("01")){
                database_push.mainDAO().update(data.getID(), name.getText().toString(), reps.getText().toString(), weigth.getText().toString(), date.getText().toString());
                pull.addAll(database_push.mainDAO().getAll());
            }
            else if(code.equals("00")){
                database_pull.mainDAO().update(data.getID(), name.getText().toString(), reps.getText().toString(), weigth.getText().toString(), date.getText().toString());
                pull.addAll(database_pull.mainDAO().getAll());
            }
            notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }

}
