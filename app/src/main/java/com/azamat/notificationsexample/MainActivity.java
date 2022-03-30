package com.azamat.notificationsexample;

import static com.azamat.notificationsexample.App.CHANNEL_1_ID;
import static com.azamat.notificationsexample.App.CHANNEL_2_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;

import com.azamat.notificationsexample.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private ActivityMainBinding binding;
    private MediaSessionCompat mediaSession; //for coloring player.optional
    static List<Message> MESSAGES = new ArrayList<>(); // for chat notifications example

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        notificationManager = NotificationManagerCompat.from(this);

        //to match colors as on picture. creating fake player session
        mediaSession = new MediaSessionCompat(this, "tag");

        //creating a fake messages for chat notification example
        MESSAGES.add(new Message("Good morning!", "Jim"));
        MESSAGES.add(new Message("Hello", null));
        MESSAGES.add(new Message("Hi!", "Jenny"));

        setContentView(view);
    }

    //to use any of this methods just change a name of it to "sendOnChannel1" or "sendOnChannel2"

    public void sendOnChannel1_simple(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }

    public void sendOnChannel1_big_text(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()//big long text style
                        .bigText(getString(R.string.dummy_text)) //link to text itself
                        .setBigContentTitle("Big Content Title") //setting title
                        .setSummaryText("Summary Text"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE) //optional
                .setAutoCancel(true) //optional
                .setOnlyAlertOnce(true) //optional
                .build();

        notificationManager.notify(1, notification);

    }

    public void sendOnChannel1_press_intent_to_activity(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding link to activity. when user press notification it goes to designated activity. optional
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE) //optional
                .setContentIntent(contentIntent) //here it comes intent
                .setAutoCancel(true) //optional
                .setOnlyAlertOnce(true) //optional
                .build();

        notificationManager.notify(1, notification);

    }

    public void sendOnChannel1_action_button(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding an action button in notification. optional
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class); //class we created for showing toast
        broadcastIntent.putExtra("toastMessage", message); //sending a text to show in toast
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent) //adding action button. optional
                .build();

        notificationManager.notify(1, notification);

    }

    public void sendOnChannel1_small_icon_show(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding small icon image from anywhere. optional
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.tiktok); // just small icon like. square sized

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture) //adding picture here. it is optional
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel1_big_picture_show(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding icon image from anywhere. optional
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.tiktok);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture) //adding icon here. it is optional
                .setStyle(new NotificationCompat.BigPictureStyle() //adding big picture icon inside a notification. optional
                        .bigPicture(picture)
                        .bigLargeIcon(null)) // smaller icon set to null when expanded
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }


    public void sendOnChannel1_chat_notification(View view) {
        //for chat notification
        sendOnChannel1_for_update(this);
    }

    public static void sendOnChannel1_for_update(Context c) {
        //helper method for chat

        //adding link to activity. when user press notification it goes to designated activity. optional
        Intent activityIntent = new Intent(c, MainActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent contentIntent = PendingIntent.getActivity(c, 0, activityIntent, PendingIntent.FLAG_MUTABLE);
        }

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();

        Intent replyIntent = new Intent(c, DirectReplyReceiver.class);
        PendingIntent replyPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            replyPendingIntent = PendingIntent.getBroadcast(c, 0, replyIntent, PendingIntent.FLAG_MUTABLE);
        }
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.reply,
                "Reply...",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();


        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("Me");

        messagingStyle.setConversationTitle("Group chat");

        for (Message chatMessage : MESSAGES) {
            NotificationCompat.MessagingStyle.Message notificationMessage = new NotificationCompat.MessagingStyle.Message(
                    chatMessage.getText(),
                    chatMessage.getTimeStamp(),
                    chatMessage.getSender()
            );
            messagingStyle.addMessage(notificationMessage);
        }
        Notification notification = new NotificationCompat.Builder(c, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24) //required
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c); // to make it work
        notificationManager.notify(1, notification);
    }


    public void sendOnChannel2_newline_text(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        Notification notification;
        notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle(title) //artist name
                .setContentText(message) // song name
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("This is line 1") // adding seperated lines with text. we can add up to 7 lines
                        .addLine("This is line 2")
                        .addLine("This is line 3")
                        .addLine("This is line 4")
                        .addLine("This is line 5")
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);

    }

    public void sendOnChannel2_simple_player(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding media style notification
        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.tiktok); //or song album pic

        Notification notification;
        notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle(title) //artist name
                .setContentText(message) // song name
                .setLargeIcon(artwork) //adding media here
                .addAction(R.drawable.dislike, "Dislike", null) //adding fake buttons. optional
                .addAction(R.drawable.previous, "Previous", null)
                .addAction(R.drawable.pause, "Pause", null)
                .addAction(R.drawable.next, "Next", null)
                .addAction(R.drawable.like, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3))// adding fake player buttons to notification
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);

    }

    public void sendOnChannel2_colored_player(View view) {

        String title = binding.edittextTitle.getText().toString();
        String message = binding.edittextMessage.getText().toString();

        //adding media style notification
        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.tiktok); //or song album pic

        Notification notification;
        notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle(title) //artist name
                .setContentText(message) // song name
                .setLargeIcon(artwork) //adding media here
                .addAction(R.drawable.dislike, "Dislike", null) //adding fake buttons. optional
                .addAction(R.drawable.previous, "Previous", null)
                .addAction(R.drawable.pause, "Pause", null)
                .addAction(R.drawable.next, "Next", null)
                .addAction(R.drawable.like, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)// adding fake player buttons to notification
                        .setMediaSession(mediaSession.getSessionToken())) //adding color change to look the same as on picture
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    public void sendOnChannel2_progress_bar(View view) {

        final int max = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(max, 0, false);

        notificationManager.notify(2, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                for (int progress = 0; progress < max; progress += 10) {
                    notification.setProgress(max, progress, false);
                    notificationManager.notify(2, notification.build());
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Download finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                notificationManager.notify(2, notification.build());
            }
        }).start();
    }

    public void sendOnChannel2_group_example(View view) {
        String title1 = "title 1";
        String message1 = "message 1";
        String title2 = "title 2";
        String message2 = "message 2";

        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle(title1)
                .setContentText(message1)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .build();

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24) //required
                .setContentTitle(title2)
                .setContentText(message2)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .build();
        Notification summaryNotification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.reply) //required
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(title2 + " " + message2)
                        .addLine(title1 + " " + message1)
                        .setBigContentTitle("2 new mewssages")
                        .setSummaryText("user @example.com"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                .setGroupSummary(true)
                .build();


        SystemClock.sleep(2000);
        notificationManager.notify(2, notification1);
        SystemClock.sleep(2000);
        notificationManager.notify(3, notification2);
        SystemClock.sleep(2000);
        notificationManager.notify(4, summaryNotification);

    }


}