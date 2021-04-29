package com.example.beekeeper

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar

import com.example.beekeeper.adapter.ApiaryAdapter
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary

import com.google.android.material.floatingactionbutton.FloatingActionButton

class ApiaryList : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var listView_details: ListView
    var arrayList_details :ArrayList<Apiary> = ArrayList();
    lateinit var userID: String
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_list)
        val dbHelper = DBHelper(this)
        userID = intent.extras!!.getString("userID").toString()
        val newApiaryButton = findViewById<FloatingActionButton>(R.id.newApiary)
        val apiarytoolbar = findViewById<Toolbar>(R.id.apiaryListToolbar)

        listView_details = findViewById<ListView>(R.id.apiarylistView) as ListView

        listView_details.setOnItemClickListener(this);

        run {
            arrayList_details = dbHelper.getAllApiaries(userID) as ArrayList<Apiary>
        }
        runOnUiThread {
            //stuff that updates ui
            val obj_adapter : ApiaryAdapter
            obj_adapter = ApiaryAdapter(applicationContext,arrayList_details)
            listView_details.adapter=obj_adapter
        }

        //tworzymy nowa pasieke
        newApiaryButton.setOnClickListener(){
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, ApiaryView::class.java)
                    intent.putExtra("userID", userID)
                    intent.putExtra("apiaryID", "new")
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }

        //cofamy sie
        apiarytoolbar.setNavigationOnClickListener() {
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userID", userID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()

        }

    }


    override fun onResume() {
        super.onResume()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        Thread() {
            run {
                Thread.sleep(1000);
                Log.d("TAGG", arrayList_details[position].apiaryID.toString())
            }
            runOnUiThread() {
                val apiaryID = arrayList_details[position].apiaryID.toString() ;
                val intent = Intent(this, HiveList::class.java)
                intent.putExtra("apiaryID", apiaryID)
                intent.putExtra("userID", userID)
                startActivity(intent)
                this.onPause()
            }
        }.start()
    }
}