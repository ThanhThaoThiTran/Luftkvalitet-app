package com.example.luftkvalitetalfa.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.luftkvalitetalfa.MainActivity
import com.example.luftkvalitetalfa.R
import com.example.luftkvalitetalfa.service.NetworkAPI
import com.example.luftkvalitetalfa.service.Stasjonsliste
import kotlinx.android.synthetic.main.activity_permission.*

class Welcome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Stasjonsliste.shared.loadStations()

        // checkFirstOpen()

        get_notification.setOnClickListener {
            val intent = Intent(this, Permission::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkFirstOpen() {
        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("isFirstRun", true)


        if (!isFirstRun) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean(
            "isFirstRun",
            false
        ).apply()
    }
}