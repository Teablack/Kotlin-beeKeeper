package com.example.beekeeper.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.beekeeper.R
import com.example.beekeeper.model.Apiary

class ApiaryAdapter(context: Context, arrayListDetails:ArrayList<Apiary>) : BaseAdapter(){

        private val layoutInflater: LayoutInflater
        private val arrayListDetails:ArrayList<Apiary>

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
            val listRowHolder: ListRowHolder
            if (convertView == null) {
                view = this.layoutInflater.inflate(R.layout.apiary_adapter, parent, false)
                listRowHolder = ListRowHolder(view)
                view.tag = listRowHolder
            } else {
                view = convertView
                listRowHolder = view.tag as ListRowHolder
            }

            listRowHolder.apiaryName.text = arrayListDetails.get(position).apiaryName
            listRowHolder.apiaryLocalization.text = arrayListDetails.get(position).localization

            return view
        }
    }

    private class ListRowHolder(row: View?) {
        public val apiaryName: TextView
        public val apiaryLocalization: TextView


        init {
            this.apiaryName = row?.findViewById<TextView>(R.id.apiaryName) as TextView
            this.apiaryLocalization = row?.findViewById<TextView>(R.id.apiaryLocalization) as TextView

        }



}