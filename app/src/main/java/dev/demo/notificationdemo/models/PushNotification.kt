package dev.demo.notificationdemo.models

data class PushNotification(
    val data: NotificationData,
    val to: String
)