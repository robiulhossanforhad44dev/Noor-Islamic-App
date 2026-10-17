package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * An authentic Islamic pointed arch (Mihrab) shape.
 */
class IslamicArchShape(private val cornerRadius: Float = 10f) : Shape {
  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    val w = size.width
    val h = size.height
    val path = Path().apply {
      moveTo(0f, h - cornerRadius)
      quadraticTo(0f, h, cornerRadius, h)
      lineTo(w - cornerRadius, h)
      quadraticTo(w, h, w, h - cornerRadius)
      val archStartY = h * 0.40f
      lineTo(w, archStartY)
      cubicTo(
        w, archStartY * 0.35f,
        w * 0.68f, 0f,
        w * 0.5f, 0f
      )
      cubicTo(
        w * 0.32f, 0f,
        0f, archStartY * 0.35f,
        0f, archStartY
      )
      lineTo(0f, h - cornerRadius)
      close()
    }
    return Outline.Generic(path)
  }
}

/**
 * Decorative double Islamic Arch Frame border drawn inside the card.
 */
@Composable
fun IslamicArchFrame(
  modifier: Modifier = Modifier,
  borderColor: Color = Color(0xFFC5A059),
  content: @Composable () -> Unit
) {
  Box(modifier = modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Inner decorative border path
      val inset = 3.5f
      val innerW = w - inset * 2
      val innerH = h - inset * 2
      val archStartY = inset + innerH * 0.40f

      val path = Path().apply {
        moveTo(inset, inset + innerH - 6f)
        quadraticTo(inset, inset + innerH, inset + 6f, inset + innerH)
        lineTo(inset + innerW - 6f, inset + innerH)
        quadraticTo(inset + innerW, inset + innerH, inset + innerW, inset + innerH - 6f)
        lineTo(inset + innerW, archStartY)
        cubicTo(
          inset + innerW, archStartY * 0.38f,
          inset + innerW * 0.68f, inset,
          inset + innerW * 0.5f, inset
        )
        cubicTo(
          inset + innerW * 0.32f, inset,
          inset, archStartY * 0.38f,
          inset, archStartY
        )
        lineTo(inset, inset + innerH - 6f)
        close()
      }

      drawPath(
        path = path,
        color = borderColor.copy(alpha = 0.55f),
        style = Stroke(width = 1.0f)
      )
    }

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp, bottom = 4.dp, start = 4.dp, end = 4.dp),
      contentAlignment = Alignment.Center
    ) {
      content()
    }
  }
}

/**
 * 1. নামাজের সময়: Clock with Mosque Dome
 */
@Composable
fun PrayerTimeArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Clock Face circle on bottom left / center
    val clockRadius = w * 0.36f
    val clockCenter = Offset(w * 0.45f, h * 0.58f)
    drawCircle(color = tint, radius = clockRadius, center = clockCenter, style = stroke)

    // Clock hour & minute ticks
    for (i in 0 until 12) {
      val angle = (i * 30) * (PI / 180f).toFloat()
      val outer = Offset(clockCenter.x + cos(angle) * (clockRadius - 1.5f), clockCenter.y + sin(angle) * (clockRadius - 1.5f))
      val inner = Offset(clockCenter.x + cos(angle) * (clockRadius - 3.8f), clockCenter.y + sin(angle) * (clockRadius - 3.8f))
      drawLine(color = tint, start = inner, end = outer, strokeWidth = 1.0f)
    }

    // Clock hands
    drawLine(color = tint, start = clockCenter, end = Offset(clockCenter.x, clockCenter.y - clockRadius * 0.55f), strokeWidth = 1.5f, cap = StrokeCap.Round)
    drawLine(color = tint, start = clockCenter, end = Offset(clockCenter.x + clockRadius * 0.52f, clockCenter.y + clockRadius * 0.15f), strokeWidth = 1.3f, cap = StrokeCap.Round)
    drawCircle(color = tint, radius = 1.5f, center = clockCenter)

    // Mosque dome silhouette rising behind the clock on the right
    val domePath = Path().apply {
      moveTo(w * 0.62f, h * 0.68f)
      lineTo(w * 0.88f, h * 0.68f)
      cubicTo(w * 0.88f, h * 0.42f, w * 0.78f, h * 0.30f, w * 0.75f, h * 0.22f)
      cubicTo(w * 0.72f, h * 0.30f, w * 0.62f, h * 0.42f, w * 0.62f, h * 0.68f)
    }
    drawPath(domePath, color = tint, style = stroke)
    // Spire
    drawLine(color = tint, start = Offset(w * 0.75f, h * 0.22f), end = Offset(w * 0.75f, h * 0.14f), strokeWidth = 1.2f)
    drawCircle(color = tint, radius = 1.2f, center = Offset(w * 0.75f, h * 0.13f))
  }
}

/**
 * 2. কুরআন মাজিদ: Open Quran on Rehal Bookstand
 */
@Composable
fun QuranRehalArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Open Book Pages (Top)
    val bookCenter = Offset(w * 0.5f, h * 0.40f)

    // Left Page
    val leftPage = Path().apply {
      moveTo(bookCenter.x, bookCenter.y + 2f)
      cubicTo(w * 0.35f, h * 0.34f, w * 0.20f, h * 0.36f, w * 0.15f, h * 0.26f)
      lineTo(w * 0.15f, h * 0.52f)
      cubicTo(w * 0.20f, h * 0.60f, w * 0.35f, h * 0.58f, bookCenter.x, bookCenter.y + 14f)
      close()
    }
    drawPath(leftPage, color = tint, style = stroke)

    // Right Page
    val rightPage = Path().apply {
      moveTo(bookCenter.x, bookCenter.y + 2f)
      cubicTo(w * 0.65f, h * 0.34f, w * 0.80f, h * 0.36f, w * 0.85f, h * 0.26f)
      lineTo(w * 0.85f, h * 0.52f)
      cubicTo(w * 0.80f, h * 0.60f, w * 0.65f, h * 0.58f, bookCenter.x, bookCenter.y + 14f)
      close()
    }
    drawPath(rightPage, color = tint, style = stroke)

    // Text line hints on pages
    drawLine(color = tint, start = Offset(w * 0.24f, h * 0.38f), end = Offset(w * 0.44f, h * 0.44f), strokeWidth = 0.9f)
    drawLine(color = tint, start = Offset(w * 0.24f, h * 0.46f), end = Offset(w * 0.44f, h * 0.51f), strokeWidth = 0.9f)
    drawLine(color = tint, start = Offset(w * 0.56f, h * 0.44f), end = Offset(w * 0.76f, h * 0.38f), strokeWidth = 0.9f)
    drawLine(color = tint, start = Offset(w * 0.56f, h * 0.51f), end = Offset(w * 0.76f, h * 0.46f), strokeWidth = 0.9f)

    // X-shaped Wooden Rehal Stand below
    val standY = h * 0.58f
    drawLine(color = tint, start = Offset(w * 0.26f, standY), end = Offset(w * 0.74f, h * 0.88f), strokeWidth = 1.5f, cap = StrokeCap.Round)
    drawLine(color = tint, start = Offset(w * 0.74f, standY), end = Offset(w * 0.26f, h * 0.88f), strokeWidth = 1.5f, cap = StrokeCap.Round)

    // Stand base decorative arch
    val archStand = Path().apply {
      moveTo(w * 0.32f, h * 0.88f)
      cubicTo(w * 0.42f, h * 0.80f, w * 0.58f, h * 0.80f, w * 0.68f, h * 0.88f)
    }
    drawPath(archStand, color = tint, style = stroke)
  }
}

/**
 * 3. তাসবিহ: Prayer Beads Loop with Pendant
 */
@Composable
fun TasbihArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // Elliptical loop of beads
    val rx = w * 0.32f
    val ry = h * 0.30f
    val center = Offset(w * 0.50f, h * 0.44f)

    val beadCount = 20
    for (i in 0 until beadCount) {
      val angle = (i * (360f / beadCount)) * (PI / 180f).toFloat()
      val bx = center.x + cos(angle) * rx
      val by = center.y + sin(angle) * ry
      drawCircle(color = tint, radius = 1.8f, center = Offset(bx, by), style = Stroke(width = 1.0f))
    }

    // Imam / Top tassel bead
    val topX = center.x
    val topY = center.y + ry
    drawCircle(color = tint, radius = 2.4f, center = Offset(topX, topY))

    // Hanging pendant / counter box & tassel
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round)
    drawLine(color = tint, start = Offset(topX, topY + 2f), end = Offset(topX, topY + 9f), strokeWidth = 1.2f)

    // Little counter box
    drawRect(
      color = tint,
      topLeft = Offset(topX - 4f, topY + 9f),
      size = Size(8f, 7f),
      style = stroke
    )
    drawLine(color = tint, start = Offset(topX - 2f, topY + 12.5f), end = Offset(topX + 2f, topY + 12.5f), strokeWidth = 0.8f)

    // Tassel strands hanging down
    drawLine(color = tint, start = Offset(topX - 2f, topY + 16f), end = Offset(topX - 4f, topY + 22f), strokeWidth = 1.0f)
    drawLine(color = tint, start = Offset(topX, topY + 16f), end = Offset(topX, topY + 23f), strokeWidth = 1.0f)
    drawLine(color = tint, start = Offset(topX + 2f, topY + 16f), end = Offset(topX + 4f, topY + 22f), strokeWidth = 1.0f)
  }
}

/**
 * 4. শাজরা শরীফ: Ornate Book Cover with Minaret
 */
@Composable
fun ShajraSharifArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Vertical Book Cover
    val bookW = w * 0.48f
    val bookH = h * 0.68f
    val bookLeft = w * 0.16f
    val bookTop = h * 0.20f

    // Outer rectangle with book spine
    drawRect(color = tint, topLeft = Offset(bookLeft, bookTop), size = Size(bookW, bookH), style = stroke)
    // Spine double line
    drawLine(color = tint, start = Offset(bookLeft + 3f, bookTop), end = Offset(bookLeft + 3f, bookTop + bookH), strokeWidth = 1.0f)

    // Inner ornate border
    drawRect(
      color = tint,
      topLeft = Offset(bookLeft + 6f, bookTop + 4f),
      size = Size(bookW - 9f, bookH - 8f),
      style = Stroke(width = 0.8f)
    )

    // Central Islamic Medallion (diamond)
    val medCenter = Offset(bookLeft + bookW * 0.55f, bookTop + bookH * 0.5f)
    val medRadius = 4.5f
    val medPath = Path().apply {
      moveTo(medCenter.x, medCenter.y - medRadius)
      lineTo(medCenter.x + medRadius, medCenter.y)
      lineTo(medCenter.x, medCenter.y + medRadius)
      lineTo(medCenter.x - medRadius, medCenter.y)
      close()
    }
    drawPath(medPath, color = tint, style = Stroke(width = 1.0f))

    // Minaret silhouette on the right
    val minX = w * 0.78f
    val minTop = h * 0.22f
    val minBottom = h * 0.88f
    // Shaft
    drawLine(color = tint, start = Offset(minX, minTop + 8f), end = Offset(minX, minBottom), strokeWidth = 1.8f)
    // Balcony
    drawLine(color = tint, start = Offset(minX - 3.5f, minTop + 14f), end = Offset(minX + 3.5f, minTop + 14f), strokeWidth = 1.4f)
    // Dome/Finial
    val minDome = Path().apply {
      moveTo(minX - 2.5f, minTop + 8f)
      cubicTo(minX - 2f, minTop + 2f, minX + 2f, minTop + 2f, minX + 2.5f, minTop + 8f)
      close()
    }
    drawPath(minDome, color = tint, style = stroke)
    drawLine(color = tint, start = Offset(minX, minTop + 2f), end = Offset(minX, minTop - 2f), strokeWidth = 1.0f)
  }
}

/**
 * 5. দরূদ শরীফ: Prophet's Mosque Green Dome with Twin Olive Branches
 */
@Composable
fun DaroodSharifArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Green Dome in center
    val centerX = w * 0.50f
    val domeBaseY = h * 0.65f
    val domeW = w * 0.44f

    // Stepped base
    drawLine(color = tint, start = Offset(centerX - domeW * 0.65f, domeBaseY + 4f), end = Offset(centerX + domeW * 0.65f, domeBaseY + 4f), strokeWidth = 1.2f)
    drawRect(color = tint, topLeft = Offset(centerX - domeW * 0.5f, domeBaseY), size = Size(domeW, 4f), style = stroke)

    // Dome arc
    val domePath = Path().apply {
      moveTo(centerX - domeW * 0.5f, domeBaseY)
      cubicTo(
        centerX - domeW * 0.45f, domeBaseY - domeW * 0.55f,
        centerX - domeW * 0.15f, domeBaseY - domeW * 0.85f,
        centerX, domeBaseY - domeW * 0.95f
      )
      cubicTo(
        centerX + domeW * 0.15f, domeBaseY - domeW * 0.95f,
        centerX + domeW * 0.45f, domeBaseY - domeW * 0.55f,
        centerX + domeW * 0.5f, domeBaseY
      )
      close()
    }
    drawPath(domePath, color = tint, style = stroke)

    // Spire & Crescent
    val finialTop = domeBaseY - domeW * 1.20f
    drawLine(color = tint, start = Offset(centerX, domeBaseY - domeW * 0.95f), end = Offset(centerX, finialTop), strokeWidth = 1.2f)
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX, finialTop - 1f))

    // Twin Olive/Laurel Branches curving on both sides
    val leftBranch = Path().apply {
      moveTo(centerX - 3f, domeBaseY + 8f)
      cubicTo(centerX - w * 0.35f, domeBaseY + 8f, centerX - w * 0.45f, domeBaseY - 6f, centerX - w * 0.42f, domeBaseY - 18f)
    }
    drawPath(leftBranch, color = tint, style = Stroke(width = 1.0f, cap = StrokeCap.Round))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX - w * 0.28f, domeBaseY + 6f))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX - w * 0.40f, domeBaseY))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX - w * 0.42f, domeBaseY - 14f))

    val rightBranch = Path().apply {
      moveTo(centerX + 3f, domeBaseY + 8f)
      cubicTo(centerX + w * 0.35f, domeBaseY + 8f, centerX + w * 0.45f, domeBaseY - 6f, centerX + w * 0.42f, domeBaseY - 18f)
    }
    drawPath(rightBranch, color = tint, style = Stroke(width = 1.0f, cap = StrokeCap.Round))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX + w * 0.28f, domeBaseY + 6f))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX + w * 0.40f, domeBaseY))
    drawCircle(color = tint, radius = 1.2f, center = Offset(centerX + w * 0.42f, domeBaseY - 14f))
  }
}

/**
 * Top-left Header Rawza / Shrine emblem
 */
@Composable
fun ShajraHeaderEmblem(
  modifier: Modifier = Modifier.size(38.dp),
  tint: Color = Color.White
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Base rectangular tiered shrine foundation
    drawRect(color = tint, topLeft = Offset(w * 0.14f, h * 0.72f), size = Size(w * 0.72f, h * 0.16f), style = stroke)
    drawRect(color = tint, topLeft = Offset(w * 0.20f, h * 0.58f), size = Size(w * 0.60f, h * 0.14f), style = stroke)

    // Vertical colonnade / grill lines
    for (i in 1..4) {
      val lx = w * 0.20f + i * (w * 0.60f / 5f)
      drawLine(color = tint, start = Offset(lx, h * 0.58f), end = Offset(lx, h * 0.72f), strokeWidth = 0.9f)
    }

    // Dome on top
    val domeCenterX = w * 0.50f
    val domeBaseY = h * 0.58f
    val domeR = w * 0.22f
    val domePath = Path().apply {
      moveTo(domeCenterX - domeR, domeBaseY)
      cubicTo(
        domeCenterX - domeR * 0.9f, domeBaseY - domeR * 0.6f,
        domeCenterX - domeR * 0.25f, domeBaseY - domeR * 1.35f,
        domeCenterX, domeBaseY - domeR * 1.45f
      )
      cubicTo(
        domeCenterX + domeR * 0.25f, domeBaseY - domeR * 1.45f,
        domeCenterX + domeR * 0.9f, domeBaseY - domeR * 0.6f,
        domeCenterX + domeR, domeBaseY
      )
    }
    drawPath(domePath, color = tint, style = stroke)

    // Spire
    drawLine(color = tint, start = Offset(domeCenterX, domeBaseY - domeR * 1.45f), end = Offset(domeCenterX, h * 0.14f), strokeWidth = 1.2f)
    drawCircle(color = tint, radius = 1.5f, center = Offset(domeCenterX, h * 0.12f))
  }
}

/**
 * 6. তাফসীরুল কুরআন: Ornate Quran Volume with central medallion
 */
@Composable
fun TafseerQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    val bookW = w * 0.54f
    val bookH = h * 0.72f
    val left = w * 0.23f
    val top = h * 0.16f

    // Book outer cover
    drawRect(color = tint, topLeft = Offset(left, top), size = Size(bookW, bookH), style = stroke)
    // Double inner frame
    drawRect(color = tint, topLeft = Offset(left + 3f, top + 3f), size = Size(bookW - 6f, bookH - 6f), style = Stroke(width = 0.8f))
    drawRect(color = tint, topLeft = Offset(left + 6f, top + 6f), size = Size(bookW - 12f, bookH - 12f), style = Stroke(width = 0.6f))

    // Central circular medallion
    val center = Offset(left + bookW * 0.5f, top + bookH * 0.5f)
    drawCircle(color = tint, radius = 6f, center = center, style = Stroke(width = 1.0f))
    drawCircle(color = tint, radius = 2f, center = center)

    // Corner decorative flourishes
    drawLine(color = tint, start = Offset(left + 6f, top + 10f), end = Offset(left + 10f, top + 6f), strokeWidth = 0.8f)
    drawLine(color = tint, start = Offset(left + bookW - 10f, top + 6f), end = Offset(left + bookW - 6f, top + 10f), strokeWidth = 0.8f)
    drawLine(color = tint, start = Offset(left + 6f, top + bookH - 10f), end = Offset(left + 10f, top + bookH - 6f), strokeWidth = 0.8f)
    drawLine(color = tint, start = Offset(left + bookW - 10f, top + bookH - 6f), end = Offset(left + bookW - 6f, top + bookH - 10f), strokeWidth = 0.8f)
  }
}

/**
 * 7. হাফেজী কুরআন: Embossed Hafezi Quran Book
 */
@Composable
fun HafeziQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    val bookW = w * 0.56f
    val bookH = h * 0.74f
    val left = w * 0.22f
    val top = h * 0.14f

    drawRect(color = tint, topLeft = Offset(left, top), size = Size(bookW, bookH), style = stroke)

    // Intricate horizontal text cartouche in center
    val cartoucheTop = top + bookH * 0.38f
    val cartoucheH = bookH * 0.24f
    drawRect(color = tint, topLeft = Offset(left + 4f, cartoucheTop), size = Size(bookW - 8f, cartoucheH), style = Stroke(width = 1.0f))
    // Arabic calligraphy simulation lines inside
    drawLine(color = tint, start = Offset(left + 7f, cartoucheTop + cartoucheH * 0.5f), end = Offset(left + bookW - 7f, cartoucheTop + cartoucheH * 0.5f), strokeWidth = 1.2f)

    // Top & Bottom floral motifs
    drawCircle(color = tint, radius = 2.5f, center = Offset(left + bookW * 0.5f, top + 10f), style = Stroke(width = 0.8f))
    drawCircle(color = tint, radius = 2.5f, center = Offset(left + bookW * 0.5f, top + bookH - 10f), style = Stroke(width = 0.8f))
  }
}

/**
 * 8. কলিকাতা কুরআন: Calcutta Quran Volume
 */
@Composable
fun CalcuttaQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    val bookW = w * 0.52f
    val bookH = h * 0.72f
    val left = w * 0.24f
    val top = h * 0.16f

    drawRect(color = tint, topLeft = Offset(left, top), size = Size(bookW, bookH), style = stroke)

    // Classic Calcutta border (double rectangle with ruled lines)
    drawRect(color = tint, topLeft = Offset(left + 4f, top + 4f), size = Size(bookW - 8f, bookH - 8f), style = Stroke(width = 0.8f))

    // Multiple horizontal line rules simulating the classic Calcutta script pages
    for (i in 1..5) {
      val ly = top + 8f + i * ((bookH - 16f) / 6f)
      drawLine(color = tint, start = Offset(left + 7f, ly), end = Offset(left + bookW - 7f, ly), strokeWidth = 0.8f)
    }
  }
}

/**
 * 9. কালার কুরআন মাজিদ: Slanted Colored Quran Book
 */
@Composable
fun ColorQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // 3D perspective slanted book
    val bookPath = Path().apply {
      moveTo(w * 0.30f, h * 0.16f)
      lineTo(w * 0.76f, h * 0.22f)
      lineTo(w * 0.68f, h * 0.86f)
      lineTo(w * 0.22f, h * 0.80f)
      close()
    }
    drawPath(bookPath, color = tint, style = stroke)

    // Inner medallion on slant
    val center = Offset(w * 0.49f, h * 0.51f)
    drawCircle(color = tint, radius = 5.5f, center = center, style = Stroke(width = 1.0f))
    drawCircle(color = tint, radius = 1.8f, center = center)

    // Diagonal color band simulation
    drawLine(color = tint, start = Offset(w * 0.26f, h * 0.48f), end = Offset(w * 0.72f, h * 0.54f), strokeWidth = 0.8f)
  }
}

/**
 * 10. হিফজুল কুরআন: Circular Seal Badge with Checkmark over Quran on Rehal
 */
@Composable
fun HifzulQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Outer Circular Memorization Seal
    val center = Offset(w * 0.50f, h * 0.50f)
    val radius = w * 0.40f
    drawCircle(color = tint, radius = radius, center = center, style = stroke)
    drawCircle(color = tint, radius = radius - 3.5f, center = center, style = Stroke(width = 0.8f))

    // Open Book on Rehal in center
    val bookPath = Path().apply {
      moveTo(center.x, h * 0.40f)
      cubicTo(w * 0.38f, h * 0.36f, w * 0.28f, h * 0.38f, w * 0.24f, h * 0.32f)
      lineTo(w * 0.24f, h * 0.54f)
      cubicTo(w * 0.28f, h * 0.60f, w * 0.38f, h * 0.58f, center.x, h * 0.62f)
      cubicTo(w * 0.62f, h * 0.58f, w * 0.72f, h * 0.60f, w * 0.76f, h * 0.54f)
      lineTo(w * 0.76f, h * 0.32f)
      cubicTo(w * 0.72f, h * 0.38f, w * 0.62f, h * 0.36f, center.x, h * 0.40f)
      close()
    }
    drawPath(bookPath, color = tint, style = stroke)

    // Checkmark Badge at top
    val checkPath = Path().apply {
      moveTo(center.x - 4f, h * 0.24f)
      lineTo(center.x - 1f, h * 0.28f)
      lineTo(center.x + 5f, h * 0.20f)
    }
    drawPath(checkPath, color = tint, style = Stroke(width = 1.4f, cap = StrokeCap.Round))
  }
}

/**
 * 11. বিষয়ভিত্তিক কুরআন: Open Quran with 8-Pointed Star Above
 */
@Composable
fun BishoybhittikQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // 8-Pointed Islamic Star (Khatam) above the book
    val starCenter = Offset(w * 0.50f, h * 0.22f)
    val starR = 5f
    drawRect(
      color = tint,
      topLeft = Offset(starCenter.x - starR, starCenter.y - starR),
      size = Size(starR * 2, starR * 2),
      style = Stroke(width = 0.9f)
    )
    // Overlapping rotated diamond
    val dPath = Path().apply {
      moveTo(starCenter.x, starCenter.y - starR * 1.35f)
      lineTo(starCenter.x + starR * 1.35f, starCenter.y)
      lineTo(starCenter.x, starCenter.y + starR * 1.35f)
      lineTo(starCenter.x - starR * 1.35f, starCenter.y)
      close()
    }
    drawPath(dPath, color = tint, style = Stroke(width = 0.9f))

    // Open Book on Rehal below
    val bookCenter = Offset(w * 0.50f, h * 0.46f)
    val leftPage = Path().apply {
      moveTo(bookCenter.x, bookCenter.y)
      cubicTo(w * 0.36f, h * 0.42f, w * 0.24f, h * 0.44f, w * 0.20f, h * 0.36f)
      lineTo(w * 0.20f, h * 0.60f)
      cubicTo(w * 0.24f, h * 0.68f, w * 0.36f, h * 0.66f, bookCenter.x, bookCenter.y + 14f)
      close()
    }
    drawPath(leftPage, color = tint, style = stroke)

    val rightPage = Path().apply {
      moveTo(bookCenter.x, bookCenter.y)
      cubicTo(w * 0.64f, h * 0.42f, w * 0.76f, h * 0.44f, w * 0.80f, h * 0.36f)
      lineTo(w * 0.80f, h * 0.60f)
      cubicTo(w * 0.76f, h * 0.68f, w * 0.64f, h * 0.66f, bookCenter.x, bookCenter.y + 14f)
      close()
    }
    drawPath(rightPage, color = tint, style = stroke)

    // Rehal cross
    drawLine(color = tint, start = Offset(w * 0.30f, h * 0.68f), end = Offset(w * 0.70f, h * 0.88f), strokeWidth = 1.3f)
    drawLine(color = tint, start = Offset(w * 0.70f, h * 0.68f), end = Offset(w * 0.30f, h * 0.88f), strokeWidth = 1.3f)
  }
}

/**
 * 12. কুরআন তেলাওয়াত: Open Quran with Audio Waves on Both Sides
 */
@Composable
fun QuranTelawatArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Open Book in Center
    val center = Offset(w * 0.50f, h * 0.52f)
    val bookW = w * 0.46f

    val leftPage = Path().apply {
      moveTo(center.x, center.y - 8f)
      cubicTo(center.x - bookW * 0.5f, center.y - 12f, center.x - bookW, center.y - 10f, center.x - bookW, center.y - 16f)
      lineTo(center.x - bookW, center.y + 8f)
      cubicTo(center.x - bookW, center.y + 14f, center.x - bookW * 0.5f, center.y + 12f, center.x, center.y + 6f)
      close()
    }
    drawPath(leftPage, color = tint, style = stroke)

    val rightPage = Path().apply {
      moveTo(center.x, center.y - 8f)
      cubicTo(center.x + bookW * 0.5f, center.y - 12f, center.x + bookW, center.y - 10f, center.x + bookW, center.y - 16f)
      lineTo(center.x + bookW, center.y + 8f)
      cubicTo(center.x + bookW, center.y + 14f, center.x + bookW * 0.5f, center.y + 12f, center.x, center.y + 6f)
      close()
    }
    drawPath(rightPage, color = tint, style = stroke)

    // Rehal stand
    drawLine(color = tint, start = Offset(w * 0.36f, h * 0.68f), end = Offset(w * 0.64f, h * 0.86f), strokeWidth = 1.3f)
    drawLine(color = tint, start = Offset(w * 0.64f, h * 0.68f), end = Offset(w * 0.36f, h * 0.86f), strokeWidth = 1.3f)

    // Audio sound waves on left
    val waveStroke = Stroke(width = 1.1f, cap = StrokeCap.Round)
    val leftWave1 = Path().apply {
      moveTo(w * 0.16f, h * 0.38f)
      cubicTo(w * 0.12f, h * 0.46f, w * 0.12f, h * 0.54f, w * 0.16f, h * 0.62f)
    }
    drawPath(leftWave1, color = tint, style = waveStroke)
    val leftWave2 = Path().apply {
      moveTo(w * 0.10f, h * 0.34f)
      cubicTo(w * 0.05f, h * 0.46f, w * 0.05f, h * 0.54f, w * 0.10f, h * 0.66f)
    }
    drawPath(leftWave2, color = tint, style = waveStroke)

    // Audio sound waves on right
    val rightWave1 = Path().apply {
      moveTo(w * 0.84f, h * 0.38f)
      cubicTo(w * 0.88f, h * 0.46f, w * 0.88f, h * 0.54f, w * 0.84f, h * 0.62f)
    }
    drawPath(rightWave1, color = tint, style = waveStroke)
    val rightWave2 = Path().apply {
      moveTo(w * 0.90f, h * 0.34f)
      cubicTo(w * 0.95f, h * 0.46f, w * 0.95f, h * 0.54f, w * 0.90f, h * 0.66f)
    }
    drawPath(rightWave2, color = tint, style = waveStroke)
  }
}

/**
 * 13. তাজভীদুল কুরআন: Upright Tajweed Quran volume with ornate crest
 */
@Composable
fun TajweedQuranArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    val bookW = w * 0.52f
    val bookH = h * 0.72f
    val left = w * 0.24f
    val top = h * 0.16f

    drawRect(color = tint, topLeft = Offset(left, top), size = Size(bookW, bookH), style = stroke)

    // Inner arch frame inside book cover
    val archPath = Path().apply {
      moveTo(left + 4f, top + bookH - 6f)
      lineTo(left + 4f, top + 14f)
      cubicTo(left + 4f, top + 6f, left + bookW * 0.5f, top + 4f, left + bookW * 0.5f, top + 4f)
      cubicTo(left + bookW * 0.5f, top + 4f, left + bookW - 4f, top + 6f, left + bookW - 4f, top + 14f)
      lineTo(left + bookW - 4f, top + bookH - 6f)
      close()
    }
    drawPath(archPath, color = tint, style = Stroke(width = 0.8f))

    // Calligraphy lines inside arch
    drawLine(color = tint, start = Offset(left + 8f, top + 18f), end = Offset(left + bookW - 8f, top + 18f), strokeWidth = 1.1f)
    drawCircle(color = tint, radius = 3f, center = Offset(left + bookW * 0.5f, top + bookH * 0.55f), style = Stroke(width = 0.8f))
  }
}

/**
 * 14. নূরানী কায়দা: Slanted Primer with Arabic Alphabet and Pointer
 */
@Composable
fun NooraniQaidaArt(
  modifier: Modifier = Modifier.size(36.dp),
  tint: Color = Color(0xFFD6B575)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val stroke = Stroke(width = 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round)

    // Slanted Primer Book
    val bookPath = Path().apply {
      moveTo(w * 0.20f, h * 0.26f)
      lineTo(w * 0.76f, h * 0.20f)
      lineTo(w * 0.84f, h * 0.76f)
      lineTo(w * 0.28f, h * 0.84f)
      close()
    }
    drawPath(bookPath, color = tint, style = stroke)

    // Two page columns
    drawLine(color = tint, start = Offset(w * 0.48f, h * 0.23f), end = Offset(w * 0.56f, h * 0.80f), strokeWidth = 0.9f)

    // Arabic letters (ا ب ت) simulation
    // Alif (vertical stroke)
    drawLine(color = tint, start = Offset(w * 0.36f, h * 0.36f), end = Offset(w * 0.36f, h * 0.48f), strokeWidth = 1.3f)
    // Ba (curved with dot below)
    val baPath = Path().apply {
      moveTo(w * 0.42f, h * 0.52f)
      lineTo(w * 0.42f, h * 0.58f)
      lineTo(w * 0.32f, h * 0.58f)
      lineTo(w * 0.32f, h * 0.52f)
    }
    drawPath(baPath, color = tint, style = Stroke(width = 1.1f))
    drawCircle(color = tint, radius = 0.9f, center = Offset(w * 0.37f, h * 0.62f))

    // Ta (curved with two dots above)
    val taPath = Path().apply {
      moveTo(w * 0.72f, h * 0.46f)
      lineTo(w * 0.72f, h * 0.52f)
      lineTo(w * 0.62f, h * 0.52f)
      lineTo(w * 0.62f, h * 0.46f)
    }
    drawPath(taPath, color = tint, style = Stroke(width = 1.1f))
    drawCircle(color = tint, radius = 0.8f, center = Offset(w * 0.65f, h * 0.42f))
    drawCircle(color = tint, radius = 0.8f, center = Offset(w * 0.69f, h * 0.42f))

    // Pointer rod / Qalam pointing across the book
    drawLine(color = tint, start = Offset(w * 0.86f, h * 0.88f), end = Offset(w * 0.58f, h * 0.56f), strokeWidth = 1.5f, cap = StrokeCap.Round)
  }
}

