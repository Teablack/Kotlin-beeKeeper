package com.example.beekeeper.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.beekeeper.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Intent
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null
    val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()

                //creating json array
                val jsonarray_info:JSONArray = JSONArray (str_response)

                var size:Int = jsonarray_info.length()
//                arrayList_details= ArrayList();
//                for (i in 0.. size-1) {
//                    var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
//                    var userInfo:UserInfo= UserInfo();
//                    userInfo.id=json_objectdetail.getString("id")
//                    userInfo.name=json_objectdetail.getString("name")
//                    userInfo.email=json_objectdetail.getString("email")
//                    arrayList_details.add(userInfo)
//                }
//
//                runOnUiThread {
//                    //stuff that updates ui
//                    val obj_adapter : UserAdapter
//                    obj_adapter = UserAdapter(applicationContext,arrayList_details)
//                    listView_details.adapter=obj_adapter
//
//                }
            }
        })

    }

    fun refreshLocation() {
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    latitude = location?.latitude
                    longitude = location?.longitude
                    Log.d("TAGG", longitude.toString())
                    Log.d("TAGG", latitude.toString())
                    run("https://api.openweathermap.org/data/2.5/onecall?lat=latitude&lon=longitude&appid=c89b7c4ee007d027409f973ffb7ab2e1")
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread() {
                refreshLocation()
        }.start()
    }
}