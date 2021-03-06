package com.example.beekeeper.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.beekeeper.MainActivity
import com.example.beekeeper.R
import com.example.beekeeper.db.DBHelper
import com.example.beekeeper.model.User

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.userInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val login = findViewById<Button>(R.id.loginButton)
        val register = findViewById<Button>(R.id.registerButton)

        var user_exists = false
        var valid_password = false

        val builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("alert")
        builder.setMessage("wprowadzono bledne dane!")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, iLInt -> }

        lateinit var username_str : String
        lateinit var password_str : String

        val dbHelper = DBHelper(this)

        login.setOnClickListener {
            username_str = username.text.toString()
            password_str = password.text.toString()
            if ((username_str.length > 1) && (password_str.length > 5)) {

                user_exists = dbHelper.findUserByName(username_str)

                if (user_exists) {
                    valid_password = dbHelper.checkPassword(username_str, password_str)
                    if (valid_password){
                        var sharedPref = this.getSharedPreferences("com.example.beekeeper.shared",0)
                        var islogged = sharedPref.edit()
                        islogged.putBoolean("islogged",true)
                        islogged.putString("username",username_str)
                        islogged.putString("password",password_str)
                        islogged.apply()

                        builder.setTitle("Logowanie")
                        builder.setMessage("Zalogowano uzytkownika $username_str!")
                        val dialog: AlertDialog = builder.create();
                        dialog.show();

                        Thread() {
                            run {

                                Thread.sleep(1000);
                            }
                            runOnUiThread() {
                                val intent = Intent(this, MainActivity::class.java)
                                var userID = dbHelper.findIdByName(username_str)
                                intent.putExtra("username", username_str)
                                intent.putExtra("userID", userID)
                                startActivity(intent)
                                this.onPause()
                            }
                        }.start()
                    } else {
                        builder.setTitle("B????d")
                        builder.setMessage("Niepoprawne has??o!")
                        val dialog: AlertDialog = builder.create();
                        dialog.show();
                        password.text.clear();
                    }
                } else {
                    builder.setTitle("B????d")
                    builder.setMessage("Uzytkownika $username_str nie istnieje! Zarejestruj si??!")
                    val dialog: AlertDialog = builder.create();
                    dialog.show();
                    password.text.clear();
                }
            } else {
                builder.setTitle("B??ad")
                builder.setMessage("Has??o powinno wynosi?? conajmniej 6 znakow!")
                val dialog: AlertDialog = builder.create();
                dialog.show();
                password.text.clear();
            }
        }

        register.setOnClickListener {
            username_str = username.text.toString()
            password_str = password.text.toString()

            if ((username_str.length > 1) && (password_str.length > 5)) {
                user_exists = dbHelper.findUserByName(username_str)
                if (!user_exists) {
                        val user = User(null, username_str, password_str)
                        dbHelper.addUser(user)
                        builder.setTitle("Rejestracja")
                        builder.setMessage("Zarejestrowano uzytkownika $username_str!")
                        val dialog: AlertDialog = builder.create();
                        dialog.show();
                } else {
                    builder.setTitle("B????d")
                    builder.setMessage("Uzytkownik $username_str juz istnieje! Zaloguj si??! ")
                    val dialog: AlertDialog = builder.create();
                    dialog.show();
                    password.text.clear();
                }
            } else {
                builder.setTitle("B??ad")
                builder.setMessage("Has??o powinno wynosi?? conajmniej 6 znakow!")
                val dialog: AlertDialog = builder.create();
                dialog.show();
                password.text.clear();
            }
        }
    }
}