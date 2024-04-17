package com.mozhimen.openglk.test.impls

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFactory
import com.mozhimen.openglk.test.R
import com.mozhimen.openglk.test.mos.Texture
import com.mozhimen.openglk.test.mos.TextureMVPMatrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @ClassName TextureRender
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/16 1:06
 * @Version 1.0
 */
class OrthogoTextureRender(private val _context: Context) : GLSurfaceView.Renderer {

    private lateinit var _triangle: TextureMVPMatrix
    private var _bitmap: Bitmap
    private var _bitmapWidth = 0
    private var _bitmapHeight = 0
    private var _surfaceWidth = 0
    private var _surfaceHeight = 0

    private var _projM = FloatArray(16)
    private var _viewM = FloatArray(16)
    private var _MVPM = FloatArray(16)

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
        _triangle = TextureMVPMatrix(_bitmap)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        _surfaceWidth = width
        _surfaceHeight = height

        //calculateViewport()
        calculateViewport()
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        Matrix.multiplyMM(_MVPM, 0, _projM, 0, _viewM, 0)
        _triangle.draw(_MVPM)
    }

    fun release() {
        _triangle.release()
    }

    fun calculateViewport() {
        val imageRatio = _bitmapWidth / _bitmapHeight.toFloat()
        val surfaceRatio = _surfaceWidth / _surfaceHeight.toFloat()

        if (imageRatio > surfaceRatio) {
            val tb = imageRatio / surfaceRatio
            Matrix.orthoM(_projM, 0, -1f, 1f, -tb, tb, -1f, 1f)
        } else if (imageRatio < surfaceRatio) {
            val lr = imageRatio / surfaceRatio
            Matrix.orthoM(_projM, 0, -lr, lr, -1f, 1f, -1f, 1f)
        } else {
            Matrix.orthoM(_projM, 0, -1f, 1f, -1f, 1f, -1f, 1f)
        }

        Matrix.setLookAtM(_viewM, 0, 0f, 0f, -1f, 0f, 0f, 0f, 0f, 1f, 0f)
    }
}
