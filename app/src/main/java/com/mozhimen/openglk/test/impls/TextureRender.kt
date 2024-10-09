package com.mozhimen.openglk.test.impls

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.mozhimen.kotlin.utilk.android.graphics.UtilKBitmapFactory
import com.mozhimen.openglk.test.R
import com.mozhimen.openglk.test.mos.Texture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @ClassName TextureRender
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/16 1:06
 * @Version 1.0
 */
class TextureRender(private val _context: Context) : GLSurfaceView.Renderer {

    private lateinit var _triangle: Texture
    private var _bitmap: Bitmap
    private var _bitmapWidth = 0
    private var _bitmapHeight = 0
    private var _surfaceWidth = 0
    private var _surfaceHeight = 0
    private var _startX = 0
    private var _startY = 0

    private var _viewWidth = 0
    private var _viewHeight = 0

    init {
        _bitmap = getImage()
        _bitmapWidth = _bitmap.width
        _bitmapHeight = _bitmap.height
    }

    private fun getImage(): Bitmap {
        val opts = BitmapFactory.Options().apply {
            inScaled = false
        }
        return UtilKBitmapFactory.decodeResource(_context.resources, R.drawable.xmlk_img, opts)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(1.0f, 0.0f, 0.0f, 1f)
        _triangle = Texture(_bitmap)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        _surfaceWidth = width
        _surfaceHeight = height

        //calculateViewport()
        calculateViewport()
        GLES30.glViewport(_startX, _startY, _viewWidth, _viewHeight)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        _triangle.draw()
    }

    fun release() {
        _triangle.release()
    }

    fun calculateViewport() {
        val imageRatio = _bitmapWidth / _bitmapHeight.toFloat()
        val surfaceRatio = _surfaceWidth / _surfaceHeight.toFloat()

        if (imageRatio > surfaceRatio) {
            _viewWidth = _surfaceWidth
            _viewHeight = (_surfaceWidth / imageRatio).toInt()
        } else if (imageRatio < surfaceRatio) {
            _viewHeight = _surfaceHeight
            _viewWidth = (_surfaceHeight * imageRatio).toInt()
        } else {
            _viewWidth = _surfaceWidth
            _viewHeight = _surfaceHeight
        }

        _startX = (_surfaceWidth - _viewWidth) / 2
        _startY = (_surfaceHeight - _viewHeight) / 2
    }
}
