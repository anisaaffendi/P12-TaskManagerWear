package sg.edu.rp.id19030019.p12_taskmanagerwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class NotificationReceiver extends BroadcastReceiver {

    int reqCode = 123;
    ArrayList<Task> taskArrayList;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            channel.enableLights(true);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        String task = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int id = intent.getIntExtra("id", 0);

        String content = task + "\n" + description;

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Launch Task Manager", pendingIntent).build();

        Intent intentReply = new Intent(context, ReplyActivity.class);
        intentReply.putExtra("id", id);

        PendingIntent pendingIntentReply = PendingIntent.getActivity(context, 0, intentReply, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new androidx.core.app.RemoteInput.Builder("status").setLabel("Status report").setChoices(new String [] {"Completed"}).build();

        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Reply", pendingIntentReply).addRemoteInput(remoteInput).build();

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        builder.setContentTitle("Task");
        builder.setContentText(content);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setStyle(bigTextStyle);
        builder.setAutoCancel(true);
        builder.setVibrate(new long[] { 1000, 1000});
        builder.setLights(Color.RED, 3000, 3000);

        builder.extend(extender);

        Notification n = builder.build();

        notificationManager.notify(123, n);
    }
}