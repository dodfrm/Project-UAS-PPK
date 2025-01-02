package com.example.uasppk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uasppk.ui.main.AuthFragment

class Auth : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment.newInstance())
                .commitNow()
        }
    }
}