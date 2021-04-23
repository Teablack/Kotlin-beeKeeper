package com.example.beekeeper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary

class ApiaryView : AppCompatActivity() {

    lateinit var apiaryName_str: String
    lateinit var apiaryLocalization_str: String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary)
        val userID = intent.extras!!.getString("userID").toString()
        val apiaryName = findViewById<TextView>(R.id.apiaryName)
        val apiaryLocalization = findViewById<TextView>(R.id.apiaryLocalization)
        val apiaryButton = findViewById<Button>(R.id.apiaryButton)
        val apiarytoolbar = findViewById<Toolbar>(R.id.apiarytoolbar)


        apiaryButton.setOnClickListener(){
            apiaryName_str = apiaryName.text.toString()
            apiaryLocalization_str = apiaryLocalization.text.toString()
            val dbHelper = DBHelper(this)
            val apiary = Apiary(userID,null, apiaryName_str, apiaryLocalization_str)
            dbHelper.addApiary(apiary)

            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, ApiaryList::class.java)
                    intent.putExtra("userID", userID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }


        apiarytoolbar.setNavigationOnClickListener() {
            Thread() {
            run {
                Thread.sleep(1000);
            }
            runOnUiThread() {
                val intent = Intent(this, ApiaryList::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
                this.onPause()
            }
        }.start()

        }
    }
}