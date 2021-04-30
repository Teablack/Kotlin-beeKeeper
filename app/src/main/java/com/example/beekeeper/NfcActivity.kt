package com.example.beekeeper

import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag

import android.os.Bundle
import android.os.Parcelable
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.Apiary
import kotlin.experimental.and


class NfcActivity : AppCompatActivity() {
    lateinit var userID : String
    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.beekeeper.R.layout.activity_nfc)

        userID = intent.extras!!.getString("userID").toString()
        var nfcToolbal = findViewById<Toolbar>(R.id.nfcToolbar)

        //Initialise NfcAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        //If no NfcAdapter, display that the device has no NFC
        if (nfcAdapter == null) {
            Toast.makeText(
                this, "NO NFC Capabilities",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0
        )


        nfcToolbal.setNavigationOnClickListener() {
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

    companion object {
        const val TAG = "nfc_test"
    }

    override fun onResume() {
        super.onResume()
        assert(nfcAdapter != null)
        //nfcAdapter.enableForegroundDispatch(context,pendingIntent,
        //                                    intentFilterArray,
        //                                    techListsArray)
        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        //Onpause stop listening
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        resolveIntent(intent)

    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val tag: Tag = (intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?)!!
            val dbHelper = DBHelper(this)

            val payload = toDec(tag.id).toString()
            if (payload != null) {
                Log.d("TAFF", payload)
                val hive = dbHelper.findHiveByNfcId(payload)
                if(hive) {
                    Thread() {
                        run {
                            Thread.sleep(1000);e
                        }
                        runOnUiThread() {
                            val apiaryID = dbHelper.findHiveID(payload)
                            val hiveID = dbHelper.findApiaryID(payload)

                            val intent = Intent(this, HiveView::class.java)
                            intent.putExtra("apiaryID", apiaryID)
                            intent.putExtra("userID", userID)
                            intent.putExtra("hiveID",hiveID)
                            intent.putExtra("nfcID",payload)

                            startActivity(intent)
                            this.onPause()
                        }
                    }.start()
                }
                else{

                    val array = arrayOf("Nowy","Modyfikuj","Cofnij")

                    val array2: MutableList<String> = ArrayList()
                    val builder = AlertDialog.Builder(this)
                    val builder2 = AlertDialog.Builder(this)

                    builder.setTitle("Czy chcesz utworzyć nowy ul czy zmienić id istniejącego?")

                    builder.setItems(array) { _, which ->

                        val selected = array[which]

                        if(selected =="Nowy"){

                            builder2.setTitle("Wybierz pasieke")
                            var arrayList_details = dbHelper.getAllApiaries(userID) as ArrayList<Apiary>

                            arrayList_details.forEach {
                                array2.add(it.apiaryName)
                            }

                            builder2.setItems(array2.toTypedArray()){ _, which2 ->
                                Thread() {
                                    run {
                                        Thread.sleep(1000);
                                    }
                                    runOnUiThread() {
                                        val selectedApiary = array2[which2]
                                        var apiaryID = dbHelper.findApiaryByName(selectedApiary, userID)
                                        Log.d("APIARYNAME",selectedApiary)
                                        Log.d("APIARYID",apiaryID)

                                        val intent = Intent(this, HiveView::class.java)
                                        intent.putExtra("userID", userID)
                                        intent.putExtra("apiaryID",apiaryID)
                                        intent.putExtra("hiveID","newnfc")
                                        intent.putExtra("nfcID",payload)
                                        startActivity(intent)
                                        this.onPause()
                                    }
                                }.start()
                            }
                            val dialog2 = builder2.create()
                            dialog2.show()

                        }
                        else if(selected =="Modyfikuj"){

                        }
                    }


                    val dialog = builder.create()

                    dialog.show()


//                    Thread() {
//                        run {
//                            Thread.sleep(1000);
//                        }
//                        runOnUiThread() {
//                            val intent = Intent(this, HiveView::class.java)
//                            intent.putExtra("userID", userID)
//                            intent.putExtra("apiaryID", apiaryID)
//                            intent.putExtra("hiveID","new")
//                            startActivity(intent)
//                            this.onPause()
//                        }
//                    }.start()
                }
            }
        }
    }


    private fun toDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices) {
            val value: Byte = bytes[i] and 0xffL.toByte()
            result += value * factor
            factor *= 256L
        }
        return result
    }
}

