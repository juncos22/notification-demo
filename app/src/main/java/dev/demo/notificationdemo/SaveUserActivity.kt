package dev.demo.notificationdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.iid.FirebaseInstanceId
import dev.demo.notificationdemo.models.UserModel
import dev.demo.notificationdemo.services.DatabaseService
import dev.demo.notificationdemo.services.NotificationService

class SaveUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user)
        val etUserName: EditText = findViewById(R.id.etUserName)
        val btnSaveUser: Button = findViewById(R.id.btnSaveUser)

        NotificationService.sharedPrefs = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        if (NotificationService.token != null) {
            startActivity(Intent(this@SaveUserActivity, MainActivity::class.java))
            finish()
        }
//
//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
//            NotificationService.token = it.token
//        }
//
        btnSaveUser.setOnClickListener {
            if (etUserName.text.toString().isNotBlank()) {
                saveUser(etUserName.text.toString())
                startActivity(Intent(this@SaveUserActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun saveUser(userName: String) {
        DatabaseService().saveUser(UserModel(
            NotificationService.token!!,
            userName
        ))
    }
}