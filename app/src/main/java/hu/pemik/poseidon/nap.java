package hu.pemik.poseidon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class nap extends AppCompatActivity {

    private Button button;
    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap);

        button = (Button) findViewById(R.id.readButton);
        button.setOnClickListener(buttonClickListener);

    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(nap.this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Notification title")
                    .setContentText("Content text");

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, mBuilder.build());

        }
    };

}
