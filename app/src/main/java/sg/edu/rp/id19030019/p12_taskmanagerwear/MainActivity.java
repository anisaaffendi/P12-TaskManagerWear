package sg.edu.rp.id19030019.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd;
    ArrayAdapter aa;
    ArrayList<Task> alTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvTask);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        alTask = new ArrayList<Task>();

        DBHelper dbh = new DBHelper(MainActivity.this);
        alTask.addAll(dbh.getAllTasks());
        dbh.close();

        aa = new TaskAdapter(MainActivity.this, R.layout.row, alTask);
        listView.setAdapter(aa);
    }
}