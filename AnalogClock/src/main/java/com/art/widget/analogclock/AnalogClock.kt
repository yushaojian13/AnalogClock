package com.art.widget.analogclock

import android.text.format.DateUtils
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AnalogClock(
    dialBitmap: ImageBitmap,
    hourHandBitmap: ImageBitmap,
    minuteHandBitmap: ImageBitmap,
    secondHandBitmap: ImageBitmap? = null
) {

    val currentTime = remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = LocalTime.now()
            delay(if (secondHandBitmap != null) DateUtils.SECOND_IN_MILLIS else DateUtils.MINUTE_IN_MILLIS)
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val maxWidth = maxOf(dialBitmap.width, hourHandBitmap.width, secondHandBitmap?.width ?: 0)
        val maxHeight = maxOf(dialBitmap.height, hourHandBitmap.height, secondHandBitmap?.height ?: 0)
        val scale = min(size.width / maxWidth, size.height / maxHeight)

        scale(scale) {
            centerDrawImage(dialBitmap)

            val hourRotation = currentTime.value.hour * 30f + currentTime.value.minute * 0.5f
            val minuteRotation = currentTime.value.minute * 6f + currentTime.value.second * 0.1f
            val secondRotation = currentTime.value.second * 6f

            rotate(hourRotation) {
                centerDrawImage(hourHandBitmap)
            }
            rotate(minuteRotation) {
                centerDrawImage(minuteHandBitmap)
            }
            if (secondHandBitmap != null) {
                rotate(secondRotation) {
                    centerDrawImage(secondHandBitmap)
                }
            }
        }
    }
}

fun DrawScope.centerDrawImage(image: ImageBitmap) {
    translate((size.width - image.width) / 2, (size.height - image.height) / 2) {
        drawImage(image)
    }
}

@Composable
fun AnalogClock(
    dialColor: Color = Color.Black,
    hourHandColor: Color = Color.White,
    minuteHandColor: Color = Color.White,
    secondHandColor: Color = Color.Red,
) {

    val currentTime = remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = LocalTime.now()
            delay(1000)
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = min(size.width, size.height) / 2

        drawCircle(color = dialColor, radius = radius, center = center)

        val hourHandLength = radius * 0.5f
        val minuteHandLength = radius * 0.7f
        val secondHandLength = radius * 0.9f

        val hourRotation = currentTime.value.hour * 30f + currentTime.value.minute * 0.5f
        val minuteRotation = currentTime.value.minute * 6f + currentTime.value.second * 0.1f
        val secondRotation = currentTime.value.second * 6f

        drawLine(
            color = hourHandColor,
            start = center,
            end = calculateHandPosition(center, hourHandLength, hourRotation),
            strokeWidth = 8.dp.toPx()
        )
        drawLine(
            color = minuteHandColor,
            start = center,
            end = calculateHandPosition(center, minuteHandLength, minuteRotation),
            strokeWidth = 4.dp.toPx()
        )
        drawLine(
            color = secondHandColor,
            start = center,
            end = calculateHandPosition(center, secondHandLength, secondRotation),
            strokeWidth = 2.dp.toPx()
        )
    }
}

private fun calculateHandPosition(center: Offset, length: Float, rotation: Float): Offset {
    val angle = Math.toRadians(rotation.toDouble()).toFloat()
    val x = center.x + length * sin(angle)
    val y = center.y - length * cos(angle)
    return Offset(x, y)
}

