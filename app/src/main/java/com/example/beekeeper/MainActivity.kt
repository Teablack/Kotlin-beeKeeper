package com.example.beekeeper

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var greeting : TextView
    lateinit var loggedUser : String

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null
    val client = OkHttpClient()

    lateinit var timezoneText: TextView
    lateinit var tempText: TextView
    lateinit var pressureText: TextView
    lateinit var wind_speedText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loggedUser = intent.extras!!.getString("userIN").toString()
        greeting = findViewById<TextView>(R.id.greeting)
        timezoneText = findViewById<TextView>(R.id.textLocation)
        tempText = findViewById<TextView>(R.id.textTempMax)
        pressureText = findViewById<TextView>(R.id.textAirPressure)
        wind_speedText = findViewById<TextView>(R.id.textWindSpeed)

        greeting.text ="Witaj, "+ loggedUser + "!"

        Thread() {
            run {
                refreshLocation()
            }
        }.start()
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
                Log.d("TAG", str_response )
                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)

                //creating json array
                val json_timezone= json_contact.getString("timezone")
                Log.d("TAGG", json_timezone)
                var json_objectdetail: JSONObject =json_contact.getJSONObject("current")

                Log.d("TAG", json_objectdetail.toString())


                runOnUiThread() {
                    timezoneText.text = json_timezone
                    tempText.text = (json_objectdetail.getString("temp").toDouble() - 273.15).roundToInt().toString() + "°C"
                    pressureText.text =json_objectdetail.getString("pressure") + "hPa"
                    wind_speedText.text= "wind:"+json_objectdetail.getString("wind_speed")+"km/h"

                }

            }
        })

    }

    fun refreshLocation() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Lokalizacja")
        builder.setMessage("Nadaj dostęp do lokalizacji!")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, iLInt -> }

        if (ActivityCompat.checkSelfPermission(
                 this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                latitude = location?.latitude
                longitude = location?.longitude
                Log.d("TAGG", longitude.toString())
                Log.d("TAGG", latitude.toString())
                if(latitude!=null)
                run("https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&appid=c89b7c4ee007d027409f973ffb7ab2e1")
                else{
                    val dialog: AlertDialog = builder.create();
                    dialog.show();
                }

            }
    }
}
