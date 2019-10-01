package com.example.luftkvalitetalfa.start

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.Manifest
import android.content.Intent
import com.example.luftkvalitetalfa.MainActivity
import com.example.luftkvalitetalfa.R
import kotlinx.android.synthetic.main.activity_permission.*

class Permission : AppCompatActivity() {

    private val permissionCode = 1
    private val TAG = "permissionLocation"
    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        get_notification.setOnClickListener{
            makeRequest()
        }

        maybe_later.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")

                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
