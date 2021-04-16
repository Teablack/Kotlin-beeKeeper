package com.example.beekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import com.example.beekeeper.login.LoginActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        Thread(){
            run {
                Thread.sleep(2000);
            }
            runOnUiThread(){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}