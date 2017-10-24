package com.avito.android.krop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.support.annotation.IntDef
import android.view.View

class OverlayView(context: Context, @OverlayShape private val shape: Int) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var viewport = RectF()

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)

        if (viewport.isEmpty) {
            with(viewport) {
                left = 0f
                top = 0f
                right = width.toFloat()
                bottom = height.toFloat()
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (shape) {
            SHAPE_OVAL -> canvas.drawOval(viewport, paint)
            else -> canvas.drawRect(viewport, paint)
        }
    }

}

@IntDef(SHAPE_OVAL.toLong(), SHAPE_RECT.toLong())
@Retention(AnnotationRetention.SOURCE)
annotation class OverlayShape

const val SHAPE_OVAL = 0
const val SHAPE_RECT = 1