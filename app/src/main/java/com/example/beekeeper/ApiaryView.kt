package com.example.beekeeper

import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ApiaryView : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary)

        val apiarytoolbar = findViewById<Toolbar>(R.id.apiarytoolbar)
        apiarytoolbar.setNavigationOnClickListener() {
            Thread() {
            run {
                Thread.sleep(1000);
            }
            runOnUiThread() {
                val intent = Intent(this, ApiaryList::class.java)
                //onResumeActivity (intent)
                //TODO:  to continue Activity
                this.onPause()
            }
        }.start()

        }
    }
}