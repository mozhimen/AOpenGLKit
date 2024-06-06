package com.mozhimen.openglk.basic.impls

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import com.mozhimen.basick.elemk.commons.IAA_Listener

/**
 * @ClassName TouchGLSurfaceView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/5/7 23:52
 * @Version 1.0
 */
class TouchGLSurfaceView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {

//    init {
//        setEGLContextClientVersion(3)
//    }

    //////////////////////////////////////////////////

    private var _touchListener: IAA_Listener<Float>? = null
    private var _sceneWidth = 720// 宽
    val sceneWidth: Int = _sceneWidth
    private var _sceneHeight = 1280// 高
    val sceneHeight: Int = _sceneHeight
    private var _previousY = 0f //上次的触控位置Y坐标
    private var _previousX = 0f //上次的触控位置X坐标

    //////////////////////////////////////////////////

    fun setTouchListener(listener: IAA_Listener<Float>) {
        _touchListener = listener
    }

    fun setSceneWidthAndHeight(sceneWidth: Int, sceneHeight: Int) {
        _sceneWidth = sceneWidth
        _sceneHeight = sceneHeight
    }

    //////////////////////////////////////////////////

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setSceneWidthAndHeight(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y: Float = event.y
        val x: Float = event.x
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                //计算触控笔Y位移
                val dy = y - _previousY
                //计算触控笔X位移
                val dx = x - _previousX
                //
                _touchListener?.invoke(dx, dy)
            }
        }
        _previousY = y //记录触控笔位置
        _previousX = x //记录触控笔位置
        return true
    }
}