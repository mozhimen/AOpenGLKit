package com.mozhimen.openglk.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.openglk.test.widgets.MyGLSurfaceView

class MainActivity : AppCompatActivity() {
    private lateinit var _myGLSurfaceView: MyGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _myGLSurfaceView = MyGLSurfaceView(this)
        setContentView(_myGLSurfaceView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _myGLSurfaceView.release()
    }
}