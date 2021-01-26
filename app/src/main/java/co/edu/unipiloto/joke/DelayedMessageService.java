package co.edu.unipiloto.joke;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = "message";
    public static final int NOTIFICATION_ID = 5453;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{
                wait(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("notification","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        showText(text);
    }

    private void showText(final String text){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"notification");

        builder.setContentTitle("What is the secret of comedy?")
                .setContentText("Timing!!!!!")
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setAutoCancel(true);

        Intent actionIntent=new Intent(this,MainActivity.class);
        PendingIntent actionPendingIntent=PendingIntent.getActivity(
                this,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(DelayedMessageService.this);
        managerCompat.notify(NOTIFICATION_ID,builder.build());

    }

}