package com.example.beekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary
import com.example.beekeeper.model.Hive

class HiveView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hive_view)
        val apiaryID = intent.extras!!.getString("apiaryID").toString()
        val userID = intent.extras!!.getString("userID").toString()

        val hiveName = findViewById<TextView>(R.id.hiveNameText)
        val hiveType = findViewById<TextView>(R.id.hiveType)
        val hiveQueen = findViewById<TextView>(R.id.hiveQueen)
        val queenPersonality = findViewById<TextView>(R.id.QueenPersonality)

        val frameCount = findViewById<TextView>(R.id.frameCount)
        val actualFrameCount = findViewById<TextView>(R.id.actualFrameCount)
        val honeybees = findViewById<TextView>(R.id.honeybees)

        val hiveButton = findViewById<Button>(R.id.hiveButton)


        //dodanie nowego ula- dodac weryfikacje wprowadzonych danych
        hiveButton.setOnClickListener(){
            Thread() {
                run {
                    var hiveNameText = hiveName.text.toString()
                    var hiveTypeText = hiveType.text.toString()

                    var hiveQueentext = hiveQueen.text.toString()
                    var queenPersonalityText = queenPersonality.text.toString()
                    var frameCountText = frameCount.text.toString()
                    var actualFrameCountText = actualFrameCount.text.toString()
                    var honeybeesText = honeybees.text.toString()

                    val dbHelper = DBHelper(this)
                    val hive = Hive( apiaryID,null, hiveNameText,hiveTypeText,hiveQueentext,queenPersonalityText,frameCountText, actualFrameCountText,honeybeesText )
                    dbHelper.addHive(hive)
                }
                runOnUiThread() {
                    val intent = Intent(this, HiveList::class.java)
                    intent.putExtra("apiaryID", apiaryID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }

    }
}