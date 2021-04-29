package com.example.beekeeper

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag

import android.os.Bundle
import android.os.Parcelable
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.experimental.and


class NfcActivity : AppCompatActivity() {
    lateinit var userID : String
    //Intialize attributes
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
        //Create a PendingIntent object so the Android system can
        //populate it with the details of the tag when it is scanned.
        //PendingIntent.getActivity(Context,requestcode(identifier for
        //                           intent),intent,int)
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
        Log.d("TAGG", "skjdaklfjls")
        resolveIntent(intent)

    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val tag: Tag = (intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?)!!
            val payload = detectTagData(tag)?.toByteArray()
        }
    }

    private fun detectTagData(tag: Tag): String? {
        val sb = StringBuilder()
        val id = tag.id
        Log.d("TAGG", "detectTag Date")
        sb.append("ID (dec): ").append(toDec(id)).append('\n')
        Log.d("TAGG", sb.toString())
        return sb.toString()
    }

    private fun toDec(bytes: ByteArray): Long {
        Log.d("TAGG", "toDEC")
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

