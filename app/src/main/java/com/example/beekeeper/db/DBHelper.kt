package com.example.beekeeper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.beekeeper.model.Apiary
import com.example.beekeeper.model.Hive
import com.example.beekeeper.model.User

class DBHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "main.db"

        private val TABLE_NAME = "Users"
        private val COL_ID = "userID"
        private val COL_USERNAME = "userName"
        private val COL_PASSWORD = "password"

        private val TABLE2_NAME = "Apiary"
        private val COL_USER_ID = "userID2"
        private val COL_AP_ID = "apiaryID"
        private val COL_NAME = "apiaryName"
        private val COL_LOCALIZATION = "localization"

        private val TABLE3_NAME = "Hive"
        private val COL_APIARY_ID = "apiaryID"
        private val COL_HIVE_ID = "hiveID"
        private val COL_HIVE_NAME = "hiveName"
        private val COL_TYPE = "hiveType"
        private val COL_QUEEN_BEE = "queenbee"
        private val COL_PERSONALITY = "queenPersonality"
        private val COL_FRAME = "frameCount"
        private val COL_AC_FRAME = "actualFrameCount"
        private val COL_HONEYBEES = "honeybees"
        private val COL_NFC = "nfcID"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_USERNAME TEXT UNIQUE, $COL_PASSWORD TEXT )")
        db!!.execSQL(CREATE_TABLE_QUERY)
        val CREATE_TABLE2_QUERY =
            ("CREATE TABLE $TABLE2_NAME ($COL_AP_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_USER_ID INTEGER, $COL_NAME TEXT, $COL_LOCALIZATION TEXT )")
        db!!.execSQL(CREATE_TABLE2_QUERY)
        val CREATE_TABLE3_QUERY =
                ("CREATE TABLE $TABLE3_NAME ($COL_HIVE_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_APIARY_ID INTEGER, $COL_HIVE_NAME TEXT , $COL_TYPE TEXT,$COL_QUEEN_BEE TEXT,$COL_PERSONALITY TEXT,$COL_FRAME TEXT,$COL_AC_FRAME TEXT,$COL_HONEYBEES TEXT , $COL_NFC TEXT )")
        db!!.execSQL(CREATE_TABLE3_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE2_NAME")
        onCreate(db!!)

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE3_NAME")
        onCreate(db!!)
    }

    //znajdz apiary u usera z taka nazwa ktora ma inne id
    fun findApiary(apiaryName: String, userID : String , apiaryID: String) : Boolean{
        val db = this.readableDatabase
        val query = "Select * from $TABLE2_NAME WHERE $COL_NAME LIKE \"$apiaryName\" AND $COL_AP_ID LIKE \"$apiaryID\" AND $COL_USER_ID NOT LIKE \"$userID\""
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            if (result.getString(result.getColumnIndex(COL_NAME)) == apiaryName) {
                db.close()
                return true
            }
        }
        db.close()
        return false

    }

    fun findHiveByNfcId(nfcID: String) : Boolean {
        val db = this.readableDatabase
        val query = "Select * from $TABLE3_NAME WHERE $COL_NFC LIKE \"$nfcID\""
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            if (result.getString(result.getColumnIndex(COL_NFC)) == nfcID) {
                db.close()
                return true
            }
        }
        db.close()
        return false
    }

    fun findHiveByID(hiveID: String) : Hive {
        val db = this.readableDatabase
        val query = "Select * from $TABLE3_NAME WHERE $COL_HIVE_ID LIKE \"$hiveID\""
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        var hive = Hive()
        hive.hiveName =  result.getString(result.getColumnIndex(COL_HIVE_NAME))
        hive.hiveType =  result.getString(result.getColumnIndex(COL_TYPE))

        hive.queenbee =  result.getString(result.getColumnIndex(COL_QUEEN_BEE))
        hive.queenPersonality =  result.getString(result.getColumnIndex(COL_PERSONALITY))

        hive.frameCount =  result.getString(result.getColumnIndex(COL_FRAME))
        hive.actualFrameCount =  result.getString(result.getColumnIndex(COL_AC_FRAME))
        hive.honeybees =  result.getString(result.getColumnIndex(COL_HONEYBEES))
        Log.d("FMKKMF",hive.hiveType.toString() )
        db.close()
        return hive
    }

    fun findHiveID(nfcID: String) : String{
        val db = this.readableDatabase
        val query = "Select * from $TABLE3_NAME WHERE $COL_NFC LIKE \"$nfcID\""
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        db.close()
        return result.getString(result.getColumnIndex(COL_HIVE_ID))

    }

    fun findApiaryID(nfcID: String) : String{
        val db = this.readableDatabase
        val query = "Select * from $TABLE3_NAME WHERE $COL_NFC LIKE \"$nfcID\""
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        db.close()
        return result.getString(result.getColumnIndex(COL_APIARY_ID))

    }

    fun findApiaryByName(apiaryName: String, userID : String ) : String {
        val db = this.readableDatabase
        val query = "Select * from $TABLE2_NAME WHERE $COL_NAME LIKE \"$apiaryName\" AND $COL_USER_ID LIKE \"$userID\""
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        val id = result.getString(result.getColumnIndex(COL_AP_ID))
        db.close()
        return id
    }

    fun findUserByName(username: String): Boolean {
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\""
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            if (result.getString(result.getColumnIndex(COL_USERNAME)) == username) {
                //val id = result.getString(result.getColumnIndex(COL_ID)) ;
                db.close()
                return true
            }
        }
        db.close()
        return false
    }

    fun findIdByName(username: String): String {
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\""
        val result = db.rawQuery(query, null)
        lateinit var id : String
        if (result.moveToFirst()) {
            id = result.getString(result.getColumnIndex(COL_ID))
            db.close()
        }
        return id
    }

    fun updateApiary(apiary : Apiary) {
        val db = this.readableDatabase
        val newValues = ContentValues()
        newValues.put(COL_NAME, apiary.apiaryName)
        newValues.put(COL_LOCALIZATION, apiary.localization)

        db.update(TABLE2_NAME, newValues, "$COL_AP_ID='${apiary.apiaryID}'", null)

        db.close()
    }



    fun deleteApiary(apiaryID : String) {
        val db = this.readableDatabase
        db.delete(TABLE2_NAME, "$COL_AP_ID='${apiaryID}'", null)
        db.delete(TABLE3_NAME, "$COL_APIARY_ID='${apiaryID}'", null)
        db.close()
    }

    fun updateHive(hive : Hive) {
        val db = this.readableDatabase
        val newValues = ContentValues()
        newValues.put(COL_APIARY_ID, hive.apiaryID)
        newValues.put(COL_HIVE_ID, hive.hiveID)
        newValues.put(COL_HIVE_NAME, hive.hiveName)
        newValues.put(COL_TYPE, hive.hiveType)
        newValues.put(COL_QUEEN_BEE, hive.queenbee)
        newValues.put(COL_PERSONALITY, hive.queenPersonality)
        newValues.put(COL_FRAME, hive.frameCount)
        newValues.put(COL_TYPE, hive.hiveType)
        newValues.put(COL_AC_FRAME, hive.actualFrameCount)
        newValues.put(COL_HONEYBEES, hive.honeybees)
        newValues.put(COL_NFC, hive.nfcID)
        db.update(TABLE3_NAME, newValues, "$COL_HIVE_ID='${hive.hiveID}'", null)
        db.close()
    }


    fun deleteHive(hiveID : String) {
        val db = this.readableDatabase
        db.delete(TABLE3_NAME, "$COL_HIVE_ID='${hiveID}'", null)
        db.close()
    }

    fun checkPassword(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query =
            "Select * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\" AND $COL_PASSWORD LIKE \"$password\""
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            if ((result.getString(result.getColumnIndex(COL_USERNAME)) == username) &&
                (result.getString(result.getColumnIndex(COL_PASSWORD)) == password)
            ) {

                db.close()
                return true
            }
        }
        db.close()
        return false
    }


    fun getAllApiaries(userID: String ): MutableList<Apiary> {
        val list: MutableList<Apiary> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE2_NAME WHERE $COL_USER_ID LIKE \"$userID\" ORDER BY $COL_AP_ID  "
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val apiary = Apiary()
                apiary.apiaryID = result.getString(result.getColumnIndex(COL_AP_ID))
                apiary.apiaryName = result.getString(result.getColumnIndex(COL_NAME))
                apiary.localization = result.getString(result.getColumnIndex(COL_LOCALIZATION))
                list.add(apiary)
            } while (result.moveToNext())
        }
        db.close()
        return list
    }

    fun getAllHives(apiaryID: String ): MutableList<Hive> {
        val list: MutableList<Hive> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE3_NAME WHERE $COL_APIARY_ID LIKE \"$apiaryID\" ORDER BY $COL_HIVE_ID  "
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val hive = Hive()
                hive.hiveID = result.getString(result.getColumnIndex(COL_HIVE_ID))
                hive.hiveName = result.getString(result.getColumnIndex(COL_HIVE_NAME))
                hive.hiveType = result.getString(result.getColumnIndex(COL_TYPE))

                hive.queenbee = result.getString(result.getColumnIndex(COL_QUEEN_BEE))
                hive.queenPersonality = result.getString(result.getColumnIndex(COL_PERSONALITY))
                hive.frameCount = result.getString(result.getColumnIndex(COL_FRAME))
                hive.actualFrameCount = result.getString(result.getColumnIndex(COL_AC_FRAME))
                hive.honeybees = result.getString(result.getColumnIndex(COL_HONEYBEES))
                list.add(hive)
            } while (result.moveToNext())
        }
        db.close()
        return list
    }


    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.userID)
        values.put(COL_USERNAME, user.userName)
        values.put(COL_PASSWORD, user.password)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun addApiary(apiary: Apiary) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_USER_ID, apiary.userID2)
        values.put(COL_AP_ID, apiary.apiaryID)
        values.put(COL_NAME, apiary.apiaryName)
        values.put(COL_LOCALIZATION, apiary.localization)

        db.insert(TABLE2_NAME, null, values)

        db.close()
    }

    fun addHive(hive: Hive) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_APIARY_ID, hive.apiaryID)

        values.put(COL_HIVE_ID, hive.hiveID)
        values.put(COL_HIVE_NAME, hive.hiveName)
        values.put(COL_TYPE, hive.hiveType)

        values.put(COL_QUEEN_BEE, hive.queenbee)
        values.put(COL_PERSONALITY, hive.queenPersonality)
        values.put(COL_FRAME, hive.frameCount)

        values.put(COL_AC_FRAME, hive.actualFrameCount)
        values.put(COL_HONEYBEES, hive.honeybees)
        values.put(COL_NFC, hive.nfcID)

        db.insert(TABLE3_NAME, null, values)

        db.close()
    }

    //    fun updateApiary(username: String, score: Int) {
//
//        val db = this.readableDatabase
//        val newValues = ContentValues()
//        newValues.put(COL_USERNAME, username)
//        newValues.put(COL_USER_SCORE, score.toString())
//
//        db.update(TABLE_NAME, newValues, "$COL_USERNAME='$username'", null)
//
//        db.close()
//        getAllUsers()
//    }
}
