package com.art.analogclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.art.widget.analogclock.AnalogClock

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.padding(16.dp)) {
                ImageAnalogClock()
//                ColorAnalogClock()
            }
        }
    }
}

@Composable
fun ImageAnalogClock() {
    val dial = ImageBitmap.imageResource(R.drawable.widget_analog_clock_dial)
    val hour = ImageBitmap.imageResource(R.drawable.widget_analog_clock_hour)
    val minute = ImageBitmap.imageResource(R.drawable.widget_analog_clock_minute)
    AnalogClock(dial, hour, minute)
}

@Composable
fun ColorAnalogClock() {
    AnalogClock()
}