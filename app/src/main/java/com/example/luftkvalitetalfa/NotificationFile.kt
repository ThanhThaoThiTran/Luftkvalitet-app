package com.example.luftkvalitetalfa

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.app.Notification.Builder
import android.support.v4.content.ContextCompat.getSystemService
import java.security.AccessController.getContext

class NotificationFile{

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Builder
    private var notificationsOn : Boolean = false
    private val channelId = "com.example.luftkvalitetalfa"
    private val description = "Test notification"
    private lateinit var intent : Intent
    private lateinit var pendingIntent : PendingIntent

    fun start(context: Context){
        notificationsOn = true
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        intent = Intent(context, MainActivity::class.java)
        pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)

    }
    fun sendNotification(context: Context){
        if(notificationsOn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                builder = Builder(context, channelId)
                    .setContentTitle("Varsel tittel")
                    .setContentText("Trykk her!")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            } else
                builder = Builder(context)
                .setContentTitle("Varsel tittel")
                .setContentText("Trykk her!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            //notificationManager.notify(1234,builder.build())
        }
    }
}