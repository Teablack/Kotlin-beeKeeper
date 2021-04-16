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
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.beekeeper.model.Weather
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null
    val client = OkHttpClient()

//    lateinit var timezone: TextView ;
//    lateinit var temp: TextView ;
//    lateinit var pressure: TextView ;
//    lateinit var wind_speed: TextView ;


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

                //creating json object
                val json_contact:JSONObject = JSONObject(str_response)
                //creating json array

                var json_timezone:String= json_contact.getString("timezone")
                var json_objectdetail:JSONObject=json_contact.getJSONObject("current")
                Log.d("TAG", json_timezone)
                Log.d("TAG", json_objectdetail.toString())
                var model:Weather= Weather();
                model.temp=json_objectdetail.getString("temp")
                model.pressure=json_objectdetail.getString("pressure")
                model.wind_speed=json_objectdetail.getString("wind_speed")


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
                    run("https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&appid=c89b7c4ee007d027409f973ffb7ab2e1")
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread() {
                refreshLocation()
        }.start()
    }
}