package com.example.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// 커스터마이징 뷰
class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mDrawPath: CustomPath? = null
    private var mDrawPaint: Paint? = null
    private var canvas: Canvas? = null
    private var mCanvasPaint: Paint? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mBrushSize: Float = 0.toFloat()
    private var color = Color.BLACK

    init {
        setUpDrawing()
    }

    // 멤버 초기화
    private fun setUpDrawing() {
        mDrawPath = CustomPath(color, mBrushSize)

        mDrawPaint = Paint()
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.SQUARE

        mCanvasPaint = Paint(Paint.DITHER_FLAG)

        mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)  // 비트맵 객체 생성
        canvas = Canvas(mCanvasBitmap!!)    // 캔버스 객체 생성, 캔버스를 비트맵에 붙이기 -> 이후 캔버스에 그리는 그래픽은 모두 mCanvasBitmap에 적용된다.
    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float): Path() {

    }

    // Change Canvas to Canvas? if fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)    // 미리 정의된 비트맵 객체가 화면에 표시됨
        if (!mDrawPath!!.isEmpty) {
            // CustomPath 색상, 굵기 -> Paint 설정
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                // Paint 색상, 굵기 -> Path 설정
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset()
                mDrawPath!!.moveTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {
                mDrawPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP -> {
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()

        return true
    }
}