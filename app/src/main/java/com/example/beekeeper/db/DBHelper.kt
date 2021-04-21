package com.example.beekeeper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_USERNAME TEXT UNIQUE, $COL_PASSWORD TEXT )")
        db!!.execSQL(CREATE_TABLE_QUERY)
        val CREATE_TABLE2_QUERY =
            ("CREATE TABLE $TABLE2_NAME ($COL_AP_ID INTEGER PRIMARY KEY AUTOINCREMENT,COL_USER_ID INTEGER, $COL_NAME TEXT UNIQUE, $COL_LOCALIZATION TEXT )")
        db!!.execSQL(CREATE_TABLE2_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE2_NAME")
        onCreate(db!!)
    }


    fun findUserByName(username: String): Boolean {
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\""
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {

            if (result.getString(result.getColumnIndex(COL_USERNAME)) == username) {
                db.close()
                return true
            }
        }
        db.close()
        return false
    }


//    fun updateScore(username: String, score: Int) {
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

//
//    fun getActualScore(username: String): Int {
//        val query = "SELECT * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\" "
//
//        val db = this.writableDatabase
//
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//            val output = result.getInt(result.getColumnIndex(COL_USER_SCORE))
//            return output
//        }
//        db.close()
//        return 0;
//    }


//    fun getAllUsers(): MutableList<User> {
//        val list: MutableList<User> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $TABLE_NAME ORDER BY $COL_USER_SCORE DESC LIMIT 10"
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//            do {
//                val user = User()
//                user.userID = result.getString(result.getColumnIndex(COL_ID)).toInt()
//                user.userName = result.getString(result.getColumnIndex(COL_USERNAME))
//                user.password = result.getString(result.getColumnIndex(COL_PASSWORD))
//                user.userScore = result.getString(result.getColumnIndex(COL_USER_SCORE))
//                list.add(user)
//            } while (result.moveToNext())
//        }
//        db.close()
//        return list
//    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.userID)
        values.put(COL_USERNAME, user.userName)
        values.put(COL_PASSWORD, user.password)

        var result = db.insert(TABLE_NAME, null, values)

        db.close()
    }


}