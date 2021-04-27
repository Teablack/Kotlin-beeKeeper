package com.example.beekeeper.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.beekeeper.R

import com.example.beekeeper.model.Hive

class HiveAdapter(context: Context, arrayListDetails:ArrayList<Hive>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Hive>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: HiveListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.hive_adapter, parent, false)
            listRowHolder = HiveListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as HiveListRowHolder
        }

        listRowHolder.hiveName.text = arrayListDetails.get(position).hiveName


        return view
    }
}

private class HiveListRowHolder(row: View?) {
    public val hiveName: TextView



    init {
        this.hiveName = row?.findViewById<TextView>(R.id.hiveName) as TextView
    }



}