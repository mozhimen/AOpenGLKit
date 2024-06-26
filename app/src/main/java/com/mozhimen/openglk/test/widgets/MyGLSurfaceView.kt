package com.mozhimen.openglk.test.widgets

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.mozhimen.openglk.test.impls.TextureRender
import com.mozhimen.openglk.test.mos.Rectangle
import com.mozhimen.openglk.test.mos.Texture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @ClassName MyGLSurfaceView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private lateinit var myGLRender: TextureRender

    init {
        setEGLContextClientVersion(3)
        myGLRender = TextureRender(context)
        setRenderer(myGLRender)
    }

    fun release() {
        myGLRender.release()
    }
}

class MyGLRender(private val _context: Context) : GLSurfaceView.Renderer {
    private lateinit var _triangle: Rectangle
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(1.0f, 0.0f, 0.0f, 1f)
        _triangle = Rectangle()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        _triangle.draw()
    }

    fun release() {
//        _triangle.release()
    }
}