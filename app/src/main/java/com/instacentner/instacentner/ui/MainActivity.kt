package com.instacentner.instacentner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.instacentner.instacentner.R
import com.instacentner.instacentner.di.App
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).component.inject(this)

        bottom_navigation.setupWithNavController(
            findNavController(this, R.id.nav_fragment)
        )
    }
}