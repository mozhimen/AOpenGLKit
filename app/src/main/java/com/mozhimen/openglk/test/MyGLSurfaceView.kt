package com.mozhimen.openglk.test

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
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
    init {
        setEGLContextClientVersion(3)
        setRenderer(MyGLRender())
    }
}

class MyGLRender : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(1.0f, 0.0f, 0.0f, 1f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
    }

}