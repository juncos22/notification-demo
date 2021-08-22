package dev.demo.notificationdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.demo.notificationdemo.R
import dev.demo.notificationdemo.adapters.UserModelAdapter.*
import dev.demo.notificationdemo.models.UserModel

class UserModelAdapter(private val context: Context,
                       private val users: List<UserModel>,
                       val onClickListener: View.OnClickListener)
    : RecyclerView.Adapter<UserModelViewHolder>() {

    class UserModelViewHolder(itemView: View, onClickListener: View.OnClickListener)
        : RecyclerView.ViewHolder(itemView) {

        val tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
        val tvUserToken = itemView.findViewById<TextView>(R.id.tvUserToken)

        init {
            itemView.setOnClickListener {
                onClickListener.onClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserModelViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int) {
        holder.tvUserName.text = users[position].name
        holder.tvUserToken.text = users[position].token
    }

    override fun getItemCount(): Int {
        return users.size
    }
}