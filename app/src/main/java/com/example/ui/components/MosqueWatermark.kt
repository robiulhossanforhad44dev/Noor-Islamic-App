package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun MosqueWatermark(
  modifier: Modifier = Modifier,
  tint: Color = Color(0xFF6ED8B4).copy(alpha = 0.22f)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    val strokeWidth = 1.6f
    val stroke = Stroke(width = strokeWidth)

    // Base line
    drawLine(
      color = tint,
      start = Offset(0f, h),
      end = Offset(w, h),
      strokeWidth = strokeWidth
    )

    // Main central dome (positioned towards right)
    val domeCenterX = w * 0.72f
    val domeBaseY = h * 0.76f
    val domeRadius = w * 0.22f

    // Dome arc
    val domePath = Path().apply {
      moveTo(domeCenterX - domeRadius, domeBaseY)
      cubicTo(
        domeCenterX - domeRadius * 0.9f, domeBaseY - domeRadius * 0.7f,
        domeCenterX - domeRadius * 0.2f, domeBaseY - domeRadius * 1.35f,
        domeCenterX, domeBaseY - domeRadius * 1.45f
      )
      cubicTo(
        domeCenterX + domeRadius * 0.2f, domeBaseY - domeRadius * 1.45f,
        domeCenterX + domeRadius * 0.9f, domeBaseY - domeRadius * 0.7f,
        domeCenterX + domeRadius, domeBaseY
      )
    }
    drawPath(domePath, color = tint, style = stroke)

    // Dome finial (spire & crescent)
    val finialTopY = domeBaseY - domeRadius * 1.75f
    drawLine(
      color = tint,
      start = Offset(domeCenterX, domeBaseY - domeRadius * 1.45f),
      end = Offset(domeCenterX, finialTopY),
      strokeWidth = strokeWidth
    )
    drawCircle(
      color = tint,
      radius = 2.5f,
      center = Offset(domeCenterX, finialTopY + 4f),
      style = stroke
    )
    // Small crescent on finial
    val crescentPath = Path().apply {
      addArc(
        Rect(domeCenterX - 5f, finialTopY - 8f, domeCenterX + 5f, finialTopY + 2f),
        startAngleDegrees = 40f,
        sweepAngleDegrees = 260f
      )
    }
    drawPath(crescentPath, color = tint, style = stroke)

    // Horizontal bands beneath main dome
    drawLine(
      color = tint,
      start = Offset(domeCenterX - domeRadius * 1.05f, domeBaseY),
      end = Offset(domeCenterX + domeRadius * 1.05f, domeBaseY),
      strokeWidth = strokeWidth
    )
    drawLine(
      color = tint,
      start = Offset(domeCenterX - domeRadius * 1.05f, domeBaseY + 12f),
      end = Offset(domeCenterX + domeRadius * 1.05f, domeBaseY + 12f),
      strokeWidth = strokeWidth
    )

    // Arched windows in main dome base
    for (i in -2..2) {
      val winX = domeCenterX + i * (domeRadius * 0.38f)
      val winY = domeBaseY + 24f
      val winW = domeRadius * 0.22f
      val winH = 22f

      val winPath = Path().apply {
        moveTo(winX - winW / 2, winY + winH)
        lineTo(winX - winW / 2, winY + winW / 2)
        cubicTo(
          winX - winW / 2, winY,
          winX + winW / 2, winY,
          winX + winW / 2, winY + winW / 2
        )
        lineTo(winX + winW / 2, winY + winH)
      }
      drawPath(winPath, color = tint, style = stroke)
    }

    // Minaret 1 (Far left minaret)
    drawMinaret(
      centerX = w * 0.24f,
      baseY = h,
      height = h * 0.88f,
      width = w * 0.08f,
      tint = tint,
      stroke = stroke
    )

    // Minaret 2 (Far right minaret)
    drawMinaret(
      centerX = w * 0.96f,
      baseY = h,
      height = h * 0.92f,
      width = w * 0.08f,
      tint = tint,
      stroke = stroke
    )

    // Secondary smaller dome on the left
    val smallDomeCenterX = w * 0.44f
    val smallDomeBaseY = h * 0.80f
    val smallDomeRadius = w * 0.14f

    val smallDomePath = Path().apply {
      moveTo(smallDomeCenterX - smallDomeRadius, smallDomeBaseY)
      cubicTo(
        smallDomeCenterX - smallDomeRadius * 0.9f, smallDomeBaseY - smallDomeRadius * 0.7f,
        smallDomeCenterX - smallDomeRadius * 0.2f, smallDomeBaseY - smallDomeRadius * 1.35f,
        smallDomeCenterX, smallDomeBaseY - smallDomeRadius * 1.45f
      )
      cubicTo(
        smallDomeCenterX + smallDomeRadius * 0.2f, smallDomeBaseY - smallDomeRadius * 1.45f,
        smallDomeCenterX + smallDomeRadius * 0.9f, smallDomeBaseY - smallDomeRadius * 0.7f,
        smallDomeCenterX + smallDomeRadius, smallDomeBaseY
      )
    }
    drawPath(smallDomePath, color = tint, style = stroke)

    drawLine(
      color = tint,
      start = Offset(smallDomeCenterX, smallDomeBaseY - smallDomeRadius * 1.45f),
      end = Offset(smallDomeCenterX, smallDomeBaseY - smallDomeRadius * 1.7f),
      strokeWidth = strokeWidth
    )
  }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawMinaret(
  centerX: Float,
  baseY: Float,
  height: Float,
  width: Float,
  tint: Color,
  stroke: Stroke
) {
  val halfW = width / 2
  val topY = baseY - height

  // Tower body vertical lines
  drawLine(
    color = tint,
    start = Offset(centerX - halfW, baseY),
    end = Offset(centerX - halfW * 0.75f, topY + height * 0.25f),
    strokeWidth = stroke.width
  )
  drawLine(
    color = tint,
    start = Offset(centerX + halfW, baseY),
    end = Offset(centerX + halfW * 0.75f, topY + height * 0.25f),
    strokeWidth = stroke.width
  )

  // Balcony 1
  val balcony1Y = topY + height * 0.25f
  drawLine(
    color = tint,
    start = Offset(centerX - halfW * 1.25f, balcony1Y),
    end = Offset(centerX + halfW * 1.25f, balcony1Y),
    strokeWidth = stroke.width
  )
  drawLine(
    color = tint,
    start = Offset(centerX - halfW * 1.25f, balcony1Y + 7f),
    end = Offset(centerX + halfW * 1.25f, balcony1Y + 7f),
    strokeWidth = stroke.width
  )

  // Upper shaft
  val balcony2Y = topY + height * 0.12f
  drawLine(
    color = tint,
    start = Offset(centerX - halfW * 0.65f, balcony1Y),
    end = Offset(centerX - halfW * 0.55f, balcony2Y),
    strokeWidth = stroke.width
  )
  drawLine(
    color = tint,
    start = Offset(centerX + halfW * 0.65f, balcony1Y),
    end = Offset(centerX + halfW * 0.55f, balcony2Y),
    strokeWidth = stroke.width
  )

  // Balcony 2
  drawLine(
    color = tint,
    start = Offset(centerX - halfW * 0.9f, balcony2Y),
    end = Offset(centerX + halfW * 0.9f, balcony2Y),
    strokeWidth = stroke.width
  )

  // Minaret cupola / conical roof & crescent
  val spirePath = Path().apply {
    moveTo(centerX - halfW * 0.5f, balcony2Y)
    lineTo(centerX, topY)
    lineTo(centerX + halfW * 0.5f, balcony2Y)
  }
  drawPath(spirePath, color = tint, style = stroke)

  // Spire finial
  drawLine(
    color = tint,
    start = Offset(centerX, topY),
    end = Offset(centerX, topY - 12f),
    strokeWidth = stroke.width
  )
}
