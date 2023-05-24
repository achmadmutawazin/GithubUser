package com.example.githubuser.ui.timeconverter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.R
import java.text.SimpleDateFormat
import java.util.*

class TimeActivity : AppCompatActivity() {

    private lateinit var currentTimeTextView: TextView
    private lateinit var selectedTimeZoneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        currentTimeTextView = findViewById(R.id.current_time_text_view)
        selectedTimeZoneTextView = findViewById(R.id.selected_time_zone_text_view)
        val timeZoneSpinner: Spinner = findViewById(R.id.time_zone_spinner)

        val timeZones = listOf(
            TimeZoneInfo("WIB", "Asia/Jakarta"),
            TimeZoneInfo("WITA", "Asia/Makassar"),
            TimeZoneInfo("WIT", "Asia/Jayapura"),
            TimeZoneInfo("London", "Europe/London")
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeZones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeZoneSpinner.adapter = adapter

        timeZoneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTimeZone = timeZones[position]
                val currentTime = Calendar.getInstance(TimeZone.getTimeZone(selectedTimeZone.timeZone)).time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone(selectedTimeZone.timeZone)
                val formattedCurrentTime = dateFormat.format(currentTime)

                currentTimeTextView.text = "Current Time: $formattedCurrentTime"
                selectedTimeZoneTextView.text = "Selected Time Zone: ${selectedTimeZone.name}"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    data class TimeZoneInfo(val name: String, val timeZone: String) {
        override fun toString(): String {
            return name
        }
    }
}
