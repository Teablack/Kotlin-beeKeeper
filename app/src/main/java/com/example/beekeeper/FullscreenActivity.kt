package com.example.beekeeper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.login.LoginActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        Thread(){
            run {
                Thread.sleep(2000);
                var sharedPref = this.getSharedPreferences("com.example.beekeeper.shared",0)
                val islogged = sharedPref.getBoolean("islogged",false)
                Log.d("!!!!!TAGG", islogged.toString())
                if(islogged){
                    runOnUiThread(){
                        val username_str = sharedPref.getString("username","")

                        val intent = Intent(this, MainActivity::class.java)
                        val dbHelper = DBHelper(this)
                        var userID = dbHelper.findIdByName(username_str.toString())
                        intent.putExtra("userIN", username_str)
                        intent.putExtra("userID", userID)
                        startActivity(intent)
                        finish()
                    }
                }

            }
            runOnUiThread(){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}