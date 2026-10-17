package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FlagEmblem(
  language: AppLanguage,
  modifier: Modifier = Modifier,
  size: Dp = 46.dp
) {
  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size)) {
      val radius = size.toPx() / 2f
      val center = Offset(radius, radius)

      // Outer metallic bezel ring
      val bezelBrush = Brush.linearGradient(
        colors = listOf(
          Color(0xFF869E92),
          Color(0xFF385549),
          Color(0xFF1E352C),
          Color(0xFF6B8377)
        ),
        start = Offset(0f, 0f),
        end = Offset(size.toPx(), size.toPx())
      )

      drawCircle(
        brush = bezelBrush,
        radius = radius,
        center = center
      )

      // Inner metallic rim shadow
      drawCircle(
        color = Color(0xFF142921),
        radius = radius - 2.5f,
        center = center
      )

      val innerRadius = radius - 3.5f

      // Flag content clipped to circular inner disc
      val flagClipPath = Path().apply {
        addOval(
          androidx.compose.ui.geometry.Rect(
            center.x - innerRadius,
            center.y - innerRadius,
            center.x + innerRadius,
            center.y + innerRadius
          )
        )
      }

      clipPath(flagClipPath) {
        when (language) {
          AppLanguage.BANGLA -> drawBangladeshFlag(center, innerRadius)
          AppLanguage.ENGLISH -> drawUkFlag(center, innerRadius)
          AppLanguage.URDU -> drawPakistanFlag(center, innerRadius)
          AppLanguage.ARABIC -> drawSaudiFlag(center, innerRadius)
        }
      }

      // Glossy lens highlight overlay
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color.White.copy(alpha = 0.28f),
            Color.White.copy(alpha = 0.05f),
            Color.Transparent
          ),
          center = Offset(radius * 0.7f, radius * 0.5f),
          radius = radius * 0.8f
        ),
        radius = innerRadius,
        center = center
      )

      // Very subtle inner stroke
      drawCircle(
        color = Color.White.copy(alpha = 0.2f),
        radius = innerRadius,
        center = center,
        style = Stroke(width = 1f)
      )
    }
  }
}

private fun DrawScope.drawBangladeshFlag(center: Offset, radius: Float) {
  // Bottle green background
  drawCircle(
    color = Color(0xFF006A4E),
    radius = radius,
    center = center
  )

  // Crimson red sun disc
  drawCircle(
    color = Color(0xFFF42A41),
    radius = radius * 0.44f,
    center = Offset(center.x - radius * 0.06f, center.y)
  )
}

private fun DrawScope.drawUkFlag(center: Offset, radius: Float) {
  // Deep royal navy
  drawCircle(
    color = Color(0xFF012169),
    radius = radius,
    center = center
  )

  val cx = center.x
  val cy = center.y

  // Diagonal white cross (St. Andrew)
  drawLine(
    color = Color.White,
    start = Offset(cx - radius, cy - radius),
    end = Offset(cx + radius, cy + radius),
    strokeWidth = radius * 0.38f
  )
  drawLine(
    color = Color.White,
    start = Offset(cx - radius, cy + radius),
    end = Offset(cx + radius, cy - radius),
    strokeWidth = radius * 0.38f
  )

  // Diagonal red cross (St. Patrick)
  drawLine(
    color = Color(0xFFC8102E),
    start = Offset(cx - radius, cy - radius),
    end = Offset(cx + radius, cy + radius),
    strokeWidth = radius * 0.16f
  )
  drawLine(
    color = Color(0xFFC8102E),
    start = Offset(cx - radius, cy + radius),
    end = Offset(cx + radius, cy - radius),
    strokeWidth = radius * 0.16f
  )

  // Vertical & Horizontal white cross (St. George white border)
  drawLine(
    color = Color.White,
    start = Offset(cx - radius, cy),
    end = Offset(cx + radius, cy),
    strokeWidth = radius * 0.46f
  )
  drawLine(
    color = Color.White,
    start = Offset(cx, cy - radius),
    end = Offset(cx, cy + radius),
    strokeWidth = radius * 0.46f
  )

  // Vertical & Horizontal red cross (St. George)
  drawLine(
    color = Color(0xFFC8102E),
    start = Offset(cx - radius, cy),
    end = Offset(cx + radius, cy),
    strokeWidth = radius * 0.26f
  )
  drawLine(
    color = Color(0xFFC8102E),
    start = Offset(cx, cy - radius),
    end = Offset(cx, cy + radius),
    strokeWidth = radius * 0.26f
  )
}

private fun DrawScope.drawPakistanFlag(center: Offset, radius: Float) {
  // Pakistan dark green
  drawCircle(
    color = Color(0xFF01411C),
    radius = radius,
    center = center
  )

  // Left white vertical stripe (representing minorities)
  drawRect(
    color = Color.White,
    topLeft = Offset(center.x - radius, center.y - radius),
    size = Size(radius * 0.58f, radius * 2f)
  )

  val moonCenter = Offset(center.x + radius * 0.16f, center.y - radius * 0.02f)
  val moonRadius = radius * 0.46f

  // White outer crescent
  drawCircle(
    color = Color.White,
    radius = moonRadius,
    center = moonCenter
  )

  // Cutout with green circle offset to form crescent
  drawCircle(
    color = Color(0xFF01411C),
    radius = moonRadius * 0.84f,
    center = Offset(moonCenter.x + radius * 0.18f, moonCenter.y - radius * 0.12f)
  )

  // 5-point white star
  val starCenter = Offset(center.x + radius * 0.38f, center.y - radius * 0.22f)
  draw5PointStar(starCenter, outerRadius = radius * 0.17f, innerRadius = radius * 0.07f)
}

private fun DrawScope.drawSaudiFlag(center: Offset, radius: Float) {
  // Saudi Forest green
  drawCircle(
    color = Color(0xFF006C35),
    radius = radius,
    center = center
  )

  // White sword silhouette
  val swordY = center.y + radius * 0.25f
  drawLine(
    color = Color.White,
    start = Offset(center.x - radius * 0.55f, swordY),
    end = Offset(center.x + radius * 0.55f, swordY),
    strokeWidth = radius * 0.08f
  )
  // Handle crossbar
  drawLine(
    color = Color.White,
    start = Offset(center.x + radius * 0.35f, swordY - radius * 0.12f),
    end = Offset(center.x + radius * 0.35f, swordY + radius * 0.12f),
    strokeWidth = radius * 0.07f
  )

  // White crescent & star above sword
  val moonCenter = Offset(center.x, center.y - radius * 0.2f)
  drawCircle(
    color = Color.White,
    radius = radius * 0.32f,
    center = moonCenter
  )
  drawCircle(
    color = Color(0xFF006C35),
    radius = radius * 0.25f,
    center = Offset(moonCenter.x + radius * 0.1f, moonCenter.y - radius * 0.05f)
  )
}

private fun DrawScope.draw5PointStar(center: Offset, outerRadius: Float, innerRadius: Float) {
  val path = Path()
  val step = (PI / 5.0).toFloat()
  var angle = - (PI / 2.0).toFloat()

  for (i in 0 until 10) {
    val r = if (i % 2 == 0) outerRadius else innerRadius
    val x = center.x + r * cos(angle)
    val y = center.y + r * sin(angle)
    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    angle += step
  }
  path.close()
  drawPath(path, Color.White)
}
