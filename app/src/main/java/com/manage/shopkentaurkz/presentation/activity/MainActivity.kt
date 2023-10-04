package com.manage.shopkentaurkz.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manage.shopkentaurkz.R
import com.manage.shopkentaurkz.databinding.ActivityMainBinding
import com.manage.shopkentaurkz.presentation.main_page.MainPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MainPageFragment())
            .commit()
    }
}