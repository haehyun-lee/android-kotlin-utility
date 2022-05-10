package com.example.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
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

    private val mPaths = ArrayList<CustomPath>()
    private val mUndoPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    fun onClickUndo(){
        if (mPaths.size > 0) {
            // 최근에 그려진 순으로 선을 지움
            mUndoPaths.add(mPaths.removeAt(mPaths.size - 1))
            invalidate()
        }
    }

    fun onClickRedo(){
        if (mUndoPaths.size > 0) {
            mPaths.add(mUndoPaths.removeAt(mUndoPaths.size - 1))
            invalidate()
        }
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

//        mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)  // 비트맵 객체 생성
        canvas = Canvas(mCanvasBitmap!!)    // 캔버스 객체 생성, 캔버스를 비트맵에 붙이기 -> 이후 캔버스에 그리는 그래픽은 모두 mCanvasBitmap에 적용된다.
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)    // 미리 정의된 비트맵 객체가 화면에 표시됨, 캔버스 배경 그리기

        // Path 정보를 읽어서 해당하는 디자인으로 그리기 (사용자가 그렸던 Path 정보를 저장해뒀다가 화면에 다시 뿌리는 방식)
        for (path in mPaths) {
            mDrawPaint!!.strokeWidth = path.brushThickness
            mDrawPaint!!.color = path.color

            canvas.drawPath(path, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {
            // CustomPath 색상, 굵기 -> Paint 설정
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color

            // Canvas에 Path 그리기
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

                // Path 초기화 & 클릭 좌표(x, y)를 기준점으로 설정
                mDrawPath!!.reset()
                mDrawPath!!.moveTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {
                // Undo 후에 새로운 선 그리면 Undo 내역 초기화
                if (mUndoPaths.size > 0)
                    mUndoPaths.clear()
                
                // 기준점에서 이동 좌표(x, y)까지 라인 그리기
                mDrawPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                // 새로운 Path 준비
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()

        return true
    }

    fun setSizeForBrush(newSize: Float) {
        // 화면 해상도 차이를 고려해 브러시 크기 변환?
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            newSize, resources.displayMetrics
        )
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    fun setColor(newColor: String) {
        // get the color from String
        color = Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }

    /*
    Path 정보에 색상, 굵기 정보를 추가로 저장하기 위한 CustomPath
    마우스 클릭 ~ 이동 ~ 떼기 까지의 선 하나의 색, 굵기가 각자 다르기 때문
     */
    internal inner class CustomPath(var color: Int, var brushThickness: Float): Path() { }

}