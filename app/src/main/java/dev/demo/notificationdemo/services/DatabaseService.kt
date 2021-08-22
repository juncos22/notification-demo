package dev.demo.notificationdemo.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dev.demo.notificationdemo.models.UserModel

class DatabaseService {
    private val firestore = FirebaseFirestore.getInstance()
    val users = arrayListOf<UserModel>()

    fun saveUser(user: UserModel) {
        firestore.collection("users")
            .add(user)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("TASK ERROR", task.exception.toString())
                }
            }.addOnFailureListener { e ->
                Log.e("TASK EXCEPTION", e.message!!)
            }
    }

    fun loadUsers() {
        firestore.collection("users").addSnapshotListener { snapshot, error ->
            if (!snapshot?.isEmpty!!) {
                snapshot.documents.forEach { document ->
                    val data = document.data
                    val user = UserModel(
                        data?.get("token").toString(),
                        data?.get("name").toString()
                    )
                    users.add(user)
//                    if (data?.get("token").toString() != NotificationService.token) {
//
//                    }
                }
            }else {
                print("NO DATA")
            }
            if (error != null) {
                Log.e("ERROR", error.message!!)
            }
        }
    }
}