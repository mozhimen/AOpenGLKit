package com.mozhimen.openglk.basic.mos

import android.opengl.GLES30
import android.opengl.Matrix
import com.mozhimen.openglk.basic.utils.GLES30Util
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * @ClassName Triangle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
class Triangle {
    //顶点着色器
    private val _strShaderVertex = """
        uniform mat4 mTMatrix;
        attribute vec4 vPosition;
        void main() {
            gl_Position = mTMatrix * vPosition;
        }
    """.trimIndent()

    //片元着色器
    private val _strShaderFragment = """
        precision mediump float;
        uniform vec4 vColor;
        void main() {
            gl_FragColor = vColor;
        }
    """.trimIndent()

    //顶点
    private val _vertexTriangle = floatArrayOf(
        0f, 0.5f, 0f,//顶部
        -0.5f, -0.5f, 0f,//左下角
        0.5f, -0.5f, 0f//右下角
    )

    private var _vboIds = IntArray(1)

    private val _color = floatArrayOf(0.5f, 0.5f, 0.5f, 1f)
    private var _matrixTranslate = FloatArray(16)
    private var _vertexBuffer: FloatBuffer
    private var _program: Int = 0

    init {
        val allocateBuffer = ByteBuffer.allocateDirect(_vertexTriangle.size * 4)
        allocateBuffer.order(ByteOrder.nativeOrder())
        _vertexBuffer = allocateBuffer.asFloatBuffer()
        _vertexBuffer.put(_vertexTriangle)
        _vertexBuffer.position(0)

        Matrix.setIdentityM(_matrixTranslate, 0)
//        Matrix.translateM(_matrixTranslate, 0, 0.5f, 0f, 0f)
        Matrix.scaleM(_matrixTranslate, 0, 0.5f, 0.5f, 1f)

        //创建shader,并为其指定源码
        val shaderVertex = GLES30Util.loadShader(GLES30.GL_VERTEX_SHADER, _strShaderVertex)
        val shaderFragment = GLES30Util.loadShader(GLES30.GL_FRAGMENT_SHADER, _strShaderFragment)

        _program = GLES30.glCreateProgram()
        GLES30.glAttachShader(_program, shaderVertex)
        GLES30.glAttachShader(_program, shaderFragment)
        GLES30.glLinkProgram(_program)

        //生成vbo
        GLES30.glGenBuffers(1, _vboIds, 0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, _vboIds[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, allocateBuffer.capacity(), allocateBuffer, GLES30.GL_STATIC_DRAW)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
    }

    private var _vPosition = 0
    private var _vColor = 0
    private var _mTMatrix = 0

    fun draw() {
        //使用program
        GLES30.glUseProgram(_program)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, _vboIds[0])

        //将数据传递给shader
        _vPosition = GLES30.glGetAttribLocation(_program, "vPosition")
        GLES30.glEnableVertexAttribArray(_vPosition)
        GLES30.glVertexAttribPointer(_vPosition, 3, GLES30.GL_FLOAT, false, 0, 0)

        _mTMatrix = GLES30.glGetUniformLocation(_program, "mTMatrix")
        GLES30.glUniformMatrix4fv(_mTMatrix, 1, false, _matrixTranslate, 0)

        _vColor = GLES30.glGetUniformLocation(_program, "vColor")
        GLES30.glUniform4fv(_vColor, 1, _color, 0)

        //drawArray, 绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, _vertexTriangle.size / 3)

        GLES30.glDisableVertexAttribArray(_vPosition)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
    }
}