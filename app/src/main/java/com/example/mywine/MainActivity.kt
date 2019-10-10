package com.example.mywine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mywine.ui.main.WinesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WinesListFragment.newInstance())
                .commitNow()
        }
    }
}
