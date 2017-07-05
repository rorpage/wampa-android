package com.rorpage.wampa.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.rorpage.wampa.MainActivity;
import com.rorpage.wampa.R;

import java.util.UUID;

public class PebbleMessageListener extends PebbleKit.PebbleDataReceiver {

    final static String TAG = PebbleMessageListener.class.getSimpleName();
    final static String AppUUID = "4b4a90b3-c5e1-4fdd-abd5-81c54c912e58";

    private static final int KEY_BUTTON = 0;

    public PebbleMessageListener() {
        super(UUID.fromString(AppUUID));
    }

    @Override
    public void receiveData(Context context, int transactionId, PebbleDictionary data) {
        PebbleKit.sendAckToPebble(context, transactionId);

        if(data.getInteger(KEY_BUTTON) != null) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            final int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION), 0);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("Wampa")
                    .setContentText(Html.fromHtml("Here's your phone!"))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

//            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
        }
    }
}