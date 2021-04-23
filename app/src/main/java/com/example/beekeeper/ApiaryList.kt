package com.example.beekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.beekeeper.adapter.ApiaryAdapter
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary

import com.google.android.material.floatingactionbutton.FloatingActionButton

class ApiaryList : AppCompatActivity() {
    lateinit var listView_details: ListView
    var arrayList_details :ArrayList<Apiary> = ArrayList();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_list)
        val dbHelper = DBHelper(this)
        val userID = intent.extras!!.getString("userID").toString()
        val newApiaryButton = findViewById<FloatingActionButton>(R.id.newApiary)
        listView_details = findViewById<ListView>(R.id.apiarylistView) as ListView

        run {
            arrayList_details = dbHelper.getAllApiaries(userID) as ArrayList<Apiary>
        }
        runOnUiThread {
            //stuff that updates ui
            val obj_adapter : ApiaryAdapter
            obj_adapter = ApiaryAdapter(applicationContext,arrayList_details)
            listView_details.adapter=obj_adapter
        }

        newApiaryButton.setOnClickListener(){
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, ApiaryView::class.java)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }
    }
}