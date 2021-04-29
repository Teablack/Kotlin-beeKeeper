package com.example.beekeeper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
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
        val apiaryID = intent.extras!!.getString("apiaryID").toString()
        val apiaryName = findViewById<TextView>(R.id.apiaryName)
        val apiaryLocalization = findViewById<TextView>(R.id.apiaryLocalization)
        val apiaryButton = findViewById<Button>(R.id.apiaryButton)
        val apiarytoolbar = findViewById<Toolbar>(R.id.apiarytoolbar)
        val deleteApiary = findViewById<Button>(R.id.deleteApiary)


        val dbHelper = DBHelper(this)

        if(apiaryID.equals("new")){
            apiaryButton.text="Utwórz nową pasiekę"
        }
        else {
            apiaryButton.text="Modyfikuj"
            deleteApiary.setVisibility(View.VISIBLE);
        }
//dodanie nowej pasieki - dodac weryfikacje wprowadzonych danych
        apiaryButton.setOnClickListener(){
            Thread() {
                run {
                    apiaryName_str = apiaryName.text.toString()
                    apiaryLocalization_str = apiaryLocalization.text.toString()


                    if(apiaryID.equals("new")){
                        val apiary = Apiary(userID,null, apiaryName_str, apiaryLocalization_str)
                        dbHelper.addApiary(apiary)
                    }
                    else {
                        val apiary = Apiary(userID,apiaryID, apiaryName_str, apiaryLocalization_str)
                        dbHelper.updateApiary(apiary)
                    }

                }
                runOnUiThread() {
                    val intent = Intent(this, ApiaryList::class.java)
                    intent.putExtra("userID", userID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }

        //cofanie
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

        deleteApiary.setOnClickListener(){
            dbHelper.deleteApiary(apiaryID)
            runOnUiThread() {
                val intent = Intent(this, ApiaryList::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
                this.onPause()
            }
        }
    }
}