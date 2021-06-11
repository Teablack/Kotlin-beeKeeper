package com.example.beekeeper

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.login.LoginActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        val logo = findViewById<ImageView>(R.id.logo)
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(logo, scaleX, scaleY)
        animator.repeatCount = 5
        animator.duration = 1000
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
        Thread(){

            run {
                Thread.sleep(3000);
                var sharedPref = this.getSharedPreferences("com.example.beekeeper.shared",0)
                val islogged = sharedPref.getBoolean("islogged",false)

                if(islogged){
                    runOnUiThread(){
                        val username_str = sharedPref.getString("username","")
                        //Log.d("!!!!!TAGG", username_str.toString())
                        val intent = Intent(this, MainActivity::class.java)
                        val dbHelper = DBHelper(this)
                        var userID = dbHelper.findIdByName(username_str.toString())
                        intent.putExtra("username", username_str)
                        intent.putExtra("userID", userID)
                        startActivity(intent)
                        finish()
                    }
                }
                else {
                    runOnUiThread(){

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }

        }.start()
    }
}