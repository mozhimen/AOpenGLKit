package com.mozhimen.openglk.basic.mos

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
    private val vertexShader = """
        attribute vec4 vPosition;
        void main() {
            gl_Position = vPosition;
        }
    """.trimIndent()

    private val fragmentShader = """
        precision mediump float;
        uniform vec4 vColor;
        void main() {
            gl_FragColor = vColor;
        }
    """.trimIndent()

    private val triangleCoords = floatArrayOf(
        0f, 0.5f, 0f,//顶部
        -0.5f, -0.5f, 0f,//左下角
        0.5f, -0.5f, 0f//右下角
    )

    private val color = floatArrayOf(0.5f, 0.5f, 0.5f, 1f)

    private var _vertexBuffer: FloatBuffer

    init {
        val allocateBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        allocateBuffer.order(ByteOrder.nativeOrder())
        _vertexBuffer = allocateBuffer.asFloatBuffer()
        _vertexBuffer.put(triangleCoords)
        _vertexBuffer.position(0)
    }
}