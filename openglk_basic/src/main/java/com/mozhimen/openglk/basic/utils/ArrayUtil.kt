package com.mozhimen.openglk.basic.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @ClassName ArrayUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/10 16:01
 * @Version 1.0
 */
object ArrayUtil {
    /**
     * 将int[]数组转换为OpenGLES所需的IntBuffer
     */
    @JvmStatic
    fun intArray2intBuffer(intArray: IntArray): IntBuffer {
        val intBuffer: IntBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4字节
        val byteBuffer = ByteBuffer.allocateDirect(intArray.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder()) //数组排列用nativeOrder
        intBuffer = byteBuffer.asIntBuffer()
        intBuffer.put(intArray)
        intBuffer.position(0)
        return intBuffer
    }

    /**
     * 将float[]数组转换为OpenGLES所需的FloatBuffer
     */
    @JvmStatic
    fun floatArray2floatBuffer(floatArray: FloatArray): FloatBuffer {
        val floatBuffer: FloatBuffer
        //初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4字节
        val byteBuffer = ByteBuffer.allocateDirect(floatArray.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        floatBuffer = byteBuffer.asFloatBuffer()
        floatBuffer.put(floatArray)
        floatBuffer.position(0)
        return floatBuffer
    }
}