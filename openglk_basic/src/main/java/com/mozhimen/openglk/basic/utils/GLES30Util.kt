package com.mozhimen.openglk.basic.utils

import android.opengl.GLES30
import com.mozhimen.openglk.basic.annors.AShaderType_GLES30

/**
 * @ClassName GLES30Util
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/21
 * @Version 1.0
 */
object GLES30Util {
    @JvmStatic
    fun loadShader(@AShaderType_GLES30 shaderType: Int, strShader: String): Int {
        val shader = GLES30.glCreateShader(shaderType)
        GLES30.glShaderSource(shader, strShader)
        GLES30.glCompileShader(shader)
        return shader
    }
}