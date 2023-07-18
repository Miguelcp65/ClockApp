package com.example.clockapp

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.clockapp.databinding.ActivityFullscreenBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isChecked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    binding.textBatteryLevel.text = "${level.toString()}%"
                }
            }
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        val dateFormat = SimpleDateFormat("MMMM dd, yyyy")
        val currentDate = dateFormat.format(Date())
        binding.textDate.text = currentDate.toString()

        binding.checkBatteryLevel.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.textBatteryLevel.visibility = View.GONE
            } else {
                isChecked = true
                binding.textBatteryLevel.visibility = View.VISIBLE
            }
            binding.checkBatteryLevel.isChecked = isChecked
        }
        binding.checkDate.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.textDate.visibility = View.GONE
            } else {
                isChecked = true
                binding.textDate.visibility = View.VISIBLE
            }
            binding.checkDate.isChecked = isChecked
        }

        binding.layoutMenu.animate().translationY(500F)

        binding.imageViewClose.setOnClickListener {
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat())
                .setDuration(
                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                )
        }
        binding.imageViewPreferences.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }
    }
}