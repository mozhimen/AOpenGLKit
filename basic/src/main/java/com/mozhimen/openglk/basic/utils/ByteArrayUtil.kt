package com.mozhimen.openglk.basic.utils

/**
 * @ClassName ByteArrayUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/10 16:06
 * @Version 1.0
 */
object ByteArrayUtil {
    /**
     * 大端、小端方式，两字节数据转short值(有符号)
     */
    @JvmStatic
    fun bytes2short(bytes: ByteArray?, isBigEndian: Boolean): Short {
        if (bytes == null || bytes.isEmpty() || bytes.size > 2) {
            return 0
        }
        return if (isBigEndian) {
            ((bytes[1].toInt() shl 8) or (bytes[0].toInt() and 0xff)).toShort()
        } else {
            ((bytes[1].toInt() and 0xff) or (bytes[0].toInt() shl 8)).toShort()
        }
    }
}