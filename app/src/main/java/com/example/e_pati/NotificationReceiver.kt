package com.example.e_pati

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val channelId = "petcare_channel"

        val notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "PetCare Bildirimleri",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(
            channel
        )

        val message =
            intent.getStringExtra("message")
                ?: "PetCare Hatırlatması"

        val notification =
            NotificationCompat.Builder(
                context,
                channelId
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("🐾 PetCare")
                .setContentText(message)
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .build()

        notificationManager.notify(
            1,
            notification
        )
    }
}