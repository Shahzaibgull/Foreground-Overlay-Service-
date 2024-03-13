package com.example.myapplicationlockmode

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.myapplicationlockmode.databinding.ActivityOverlayMainBinding


class OverlayMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOverlayMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOverlayMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val canDraw: Boolean
        val intent :Intent?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            canDraw = Settings.canDrawOverlays(this)
            if(!canDraw && intent!=null)
            {
                startActivity(intent)
            }
        }

        binding.buttonFragmentOne.setOnClickListener {

            binding.buttonFragmentSecond.background=resources.getDrawable(R.drawable.common_background2)
            binding.buttonFragmentOne.background=resources.getDrawable(R.drawable.click_background)
            val service=Intent(this, OverlayService::class.java)
            startService(service)

        }
        binding.buttonFragmentSecond.setOnClickListener {

            binding.buttonFragmentOne.background=resources.getDrawable(R.drawable.common_background)
            binding.buttonFragmentSecond.background=resources.getDrawable(R.drawable.click_background)
            val service=Intent(this, OverlayService::class.java)
            stopService(service)
        }
    }
}