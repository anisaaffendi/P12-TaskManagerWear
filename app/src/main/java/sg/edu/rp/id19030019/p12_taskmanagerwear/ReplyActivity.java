package sg.edu.rp.id19030019.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if(remoteInput != null){
            reply = remoteInput.getCharSequence("status");
        }
        if(reply != null){
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply, Toast.LENGTH_SHORT).show();
            DBHelper dbh = new DBHelper(ReplyActivity.this);
            long inserted_id = dbh.deleteTask(id);
            dbh.close();
            finish();
        }
    }
}