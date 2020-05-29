package com.meazza.instagram.common.decoration

import android.graphics.Bitmap
import coil.bitmappool.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import kotlin.math.max

class SquareCropTransformation : Transformation {

    override fun key(): String = SquareCropTransformation::class.java.name

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val largerSize = max(input.width, input.height)
        return Util.centerCrop(pool, input, largerSize, largerSize)
    }
}