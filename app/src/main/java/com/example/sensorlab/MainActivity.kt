package com.example.sensorlab

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


const val TAG = "SENSOR"

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sm: SensorManager
    private var sGrav: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sGrav = sm.getDefaultSensor(Sensor.TYPE_GRAVITY)

        val allSensor = sm.getSensorList(Sensor.TYPE_ALL)

        allSensor.forEach {
            Log.d(TAG, it.name)
        }


        //registerListener with onClick
        btn.setOnClickListener{
            if (sGrav != null) {
                sm.registerListener(this, sGrav, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                Toast.makeText(this, "no magnetic sensor", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val value = event?.values?.get(0).toString()
        if (event?.sensor == sGrav) {
            val result = getString(R.string.gravity) + value
            txtGrav.text = result
        }

    }

    override fun onResume() {
        super.onResume()

        sGrav?.also {
            sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }

}


