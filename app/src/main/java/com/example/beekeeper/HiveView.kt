package com.example.beekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary
import com.example.beekeeper.model.Hive

class HiveView : AppCompatActivity() {

    lateinit var userID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hive_view)
        val apiaryID = intent.extras!!.getString("apiaryID").toString()
        userID = intent.extras!!.getString("userID").toString()
        val hiveID = intent.extras!!.getString("hiveID").toString()

        val dbHelper = DBHelper(this)
        val hiveName = findViewById<TextView>(R.id.hiveNameText)
        val hiveType = findViewById<TextView>(R.id.hiveType)
        val hiveQueen = findViewById<TextView>(R.id.hiveQueen)
        val queenPersonality = findViewById<TextView>(R.id.QueenPersonality)

        val frameCount = findViewById<TextView>(R.id.frameCount)
        val actualFrameCount = findViewById<TextView>(R.id.actualFrameCount)
        val honeybees = findViewById<TextView>(R.id.honeybees)

        val hiveButton = findViewById<Button>(R.id.hiveButton)

        val deleteHive = findViewById<Button>(R.id.deleteHive)

        val hivetoolbar = findViewById<Toolbar>(R.id.hiveviewtoolbar)
        var nfcID: String  = ""

        if(hiveID.equals("new")){
            hiveButton.text="Utwórz nowy ul"
        }
        else if (hiveID.equals("newnfc")){
            nfcID = intent.extras!!.getString("nfcID").toString()
        }
        else {
            hiveButton.text="Modyfikuj"
            deleteHive.setVisibility(View.VISIBLE);
        }
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


                    if(hiveID.equals("new") || hiveID.equals("newnfc")  ){
                        val hive = Hive( apiaryID,null, hiveNameText,hiveTypeText,hiveQueentext,queenPersonalityText,frameCountText, actualFrameCountText,honeybeesText , nfcID)
                        dbHelper.addHive(hive)
                    }
                    else {
                        val hive = Hive( apiaryID,hiveID, hiveNameText,hiveTypeText,hiveQueentext,queenPersonalityText,frameCountText, actualFrameCountText,honeybeesText , "")
                        dbHelper.updateHive(hive)
                    }
                }
                runOnUiThread() {
                    val intent = Intent(this, HiveList::class.java)
                    intent.putExtra("userID", userID)
                    intent.putExtra("apiaryID", apiaryID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }

        //cofamy sie
        hivetoolbar.setNavigationOnClickListener() {
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, HiveList::class.java)
                    intent.putExtra("userID", userID)
                    intent.putExtra("apiaryID", apiaryID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()

        }
        deleteHive.setOnClickListener(){
            dbHelper.deleteHive(hiveID)
            runOnUiThread() {
                val intent = Intent(this, HiveList::class.java)
                intent.putExtra("userID", userID)
                intent.putExtra("apiaryID", userID)
                startActivity(intent)
                this.onPause()
            }
        }



    }
}