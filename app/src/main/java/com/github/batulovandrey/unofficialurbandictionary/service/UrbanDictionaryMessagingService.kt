package com.github.batulovandrey.unofficialurbandictionary.service

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.concurrent.atomic.AtomicInteger

class UrbanDictionaryMessagingService: FirebaseMessagingService() {

    private val notificationId = AtomicInteger(0)

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage?) {
        if (message == null || message.notification == null) {
            return
        }

        val notification = message.notification

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.search)
                .setContentTitle(notification?.title)
                .setContentText(notification?.body)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("some text"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notificationId.incrementAndGet(), builder.build())
    }
}