package com.example.luftkvalitetalfa.start

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.luftkvalitetalfa.MainActivity
import com.example.luftkvalitetalfa.R
import kotlinx.android.synthetic.main.activity_permission.*

class Notification : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        get_notification.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        maybe_later.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
