package sg.edu.rp.id19030019.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDesc, etTime;
    Button btnAdd, btnCancel;
    int reqCode = 123;
    ArrayList<Task> taskArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescription);
        etTime = findViewById(R.id.etTime);
        btnAdd = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etDesc.getText().toString().equals("") && !etName.getText().toString().equals("")) {
                    String nameInput = etName.getText().toString();
                    String descriptionInput = etDesc.getText().toString();
                    int remind = Integer.parseInt(etTime.getText().toString());

                    try {
                        DBHelper dbh = new DBHelper(AddActivity.this);
                        Task inserted_id = new Task(nameInput, descriptionInput);
                        taskArrayList = new ArrayList<Task>();
                        taskArrayList.addAll(dbh.getAllTasks());
                        dbh.insertTask(inserted_id);

                        int indexId = taskArrayList.size()-1;
                        Task task1 = taskArrayList.get(indexId);
                        int id = task1.getId() + 1;
                        Log.d("test", "gg" + String.valueOf(id));
                        Log.d("test2", "gg" + String.valueOf(indexId));


                        dbh.close();

                        int requestCode = 123;

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, remind);

                        Intent intent = new Intent(AddActivity.this, NotificationReceiver.class);
                        intent.putExtra("name", nameInput);
                        intent.putExtra("description", descriptionInput);
                        intent.putExtra("id", id);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager) getSystemService(AddActivity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                        finish();
                        return;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(AddActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}