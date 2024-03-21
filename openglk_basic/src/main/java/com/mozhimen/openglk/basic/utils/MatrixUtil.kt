package com.mozhimen.openglk.basic.utils

import android.opengl.Matrix
import com.mozhimen.basick.elemk.android.opengl.annors.AMatrixType
import com.mozhimen.basick.lintk.annors.ADescription

/**
 * @ClassName MatrixUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/21
 * @Version 1.0
 */
object MatrixUtil {
    @JvmStatic
    fun get_ofOriginal(): FloatArray =
        floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )

    @JvmStatic
    @ADescription("get_of")
    fun apply(matrix: FloatArray, @AMatrixType type: Int, widthImage: Int, heightImage: Int, widthView: Int, heightView: Int) {
        if (heightImage > 0 && widthImage > 0 && widthView > 0 && heightView > 0) {
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (type == AMatrixType.MATRIX_FIT_XY) {
                Matrix.orthoM(projection, 0, -1f, 1f, -1f, 1f, 1f, 3f)
                Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
                Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
            }
            val sWhView = widthView.toFloat() / heightView
            val sWhImg = widthImage.toFloat() / heightImage
            if (sWhImg > sWhView) {
                when (type) {
                    AMatrixType.MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    AMatrixType.MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 1f, 1 - 2 * sWhImg / sWhView, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_END -> Matrix.orthoM(projection, 0, -1f, 1f, -1f, 2 * sWhImg / sWhView - 1, 1f, 3f)
                }
            } else {
                when (type) {
                    AMatrixType.MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    AMatrixType.MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 2 * sWhView / sWhImg - 1, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_END -> Matrix.orthoM(projection, 0, 1 - 2 * sWhView / sWhImg, 1f, -1f, 1f, 1f, 3f)
                }
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }

    @JvmStatic
    @ADescription("get_ofShow")
    fun apply(matrix: FloatArray, widthImage: Int, heightImage: Int, widthView: Int, heightView: Int) {
        if (heightImage > 0 && widthImage > 0 && widthView > 0 && heightView > 0) {
            val sWhView = widthView.toFloat() / heightView
            val sWhImg = widthImage.toFloat() / heightImage
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (sWhImg > sWhView) {
                Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
            } else {
                Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }

    @JvmStatic
    @ADescription("getCenterInside")
    fun applyCenterInside(matrix: FloatArray, imgWidth: Int, imgHeight: Int, viewWidth: Int, viewHeight: Int) {
        if (imgHeight > 0 && imgWidth > 0 && viewWidth > 0 && viewHeight > 0) {
            val sWhView = viewWidth.toFloat() / viewHeight
            val sWhImg = imgWidth.toFloat() / imgHeight
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (sWhImg > sWhView) {
                Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
            } else {
                Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }
}