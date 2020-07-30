package com.infinity_coder.remembermenu.ui.common

import android.graphics.Bitmap
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest


class RotateTransformation(private val orientation: Int): BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val exifOrientationDegrees = getExifOrientationDegrees(orientation)
        return TransformationUtils.rotateImageExif(pool, toTransform, exifOrientationDegrees)
    }

    private fun getExifOrientationDegrees(orientation: Int): Int {
        return when (orientation) {
            90 -> ExifInterface.ORIENTATION_ROTATE_90
            else -> ExifInterface.ORIENTATION_NORMAL
        }
    }
}