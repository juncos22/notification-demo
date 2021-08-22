package dev.demo.notificationdemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import dev.demo.notificationdemo.adapters.UserModelAdapter
import dev.demo.notificationdemo.models.NotificationData
import dev.demo.notificationdemo.models.PushNotification
import dev.demo.notificationdemo.models.RetrofitInstance
import dev.demo.notificationdemo.models.UserModel
import dev.demo.notificationdemo.services.DatabaseService
import dev.demo.notificationdemo.services.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//const val TOPIC = "/topics/myTopic"

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerUsers = findViewById(R.id.recyclerUsers)

        loadUsers()
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

    }

    private fun loadUsers() {
        DatabaseService().loadUsers()
        recyclerUsers.layoutManager = LinearLayoutManager(applicationContext)
        Log.i("USERS", DatabaseService().users.toString())

        val userAdapter = UserModelAdapter(applicationContext, DatabaseService().users) { view ->
            val itemIndex = recyclerUsers.getChildAdapterPosition(view!!)
            Log.i("Item clicked", DatabaseService().users[itemIndex].name)
        }
        recyclerUsers.adapter = userAdapter
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.i("RESPONSE", Gson().toJson(response))
            }else {
                Log.e("RESPONSE ERROR", response.errorBody().toString())
            }
        }catch (e: Exception) {
            Log.e("NOTIFICATION ERROR", e.message!!)
        }
    }
}