package com.example.beekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.beekeeper.adapter.HiveAdapter
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Hive
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HiveList : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var listView_details: ListView
    var arrayList_details :ArrayList<Hive> = ArrayList();

    lateinit var userID : String
    lateinit var apiaryID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hive_list)
        val dbHelper = DBHelper(this)

        userID = intent.extras!!.getString("userID").toString()
        apiaryID = intent.extras!!.getString("apiaryID").toString()

        val newHiveButton = findViewById<FloatingActionButton>(R.id.newHive)
        val hivetoolbar = findViewById<Toolbar>(R.id.hiveListToolbar)
        val editApiary = findViewById<ImageButton>(R.id.editApiary)


        listView_details = findViewById<ListView>(R.id.hivelistView) as ListView

        listView_details.setOnItemClickListener(this);

        run {
            arrayList_details = dbHelper.getAllHives(apiaryID) as ArrayList<Hive>
        }
        runOnUiThread {
            //stuff that updates ui
            val obj_adapter : HiveAdapter
            obj_adapter = HiveAdapter(applicationContext,arrayList_details)
            listView_details.adapter=obj_adapter
        }

        //tworzymy nowa pasieke
        newHiveButton.setOnClickListener(){
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, HiveView::class.java)
                    intent.putExtra("userID", userID)
                    intent.putExtra("apiaryID", apiaryID)
                    intent.putExtra("hiveID","new")
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
                    val intent = Intent(this, ApiaryList::class.java)
                    intent.putExtra("userID", userID)

                    startActivity(intent)
                    this.onPause()
                }
            }.start()

        }

        editApiary.setOnClickListener(){
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, ApiaryView::class.java)
                    intent.putExtra("userID", userID)
                    intent.putExtra("apiaryID", apiaryID)
                    startActivity(intent)
                    this.onPause()
                }
            }.start()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Thread() {
            run {
                Thread.sleep(1000);
            }
            runOnUiThread() {
                val hiveID = arrayList_details[position].hiveID ;
                val intent = Intent(this, HiveView::class.java)
                intent.putExtra("apiaryID", apiaryID)
                intent.putExtra("userID", userID)
                intent.putExtra("hiveID",hiveID)
                startActivity(intent)
                this.onPause()
            }
        }.start()
    }
}