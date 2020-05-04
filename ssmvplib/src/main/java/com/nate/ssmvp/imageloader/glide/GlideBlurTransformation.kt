package com.nate.ssmvp.imageloader.glide

import android.graphics.Bitmap
import androidx.annotation.IntRange
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.nate.ssmvp.utils.FastBlurUtils
import java.security.MessageDigest

/**
 * Glide 图片模糊变换
 * Created by Nate on 2020/5/4
 */
class GlideBlurTransformation(
  @IntRange(from = 0)
  var radius: Int) : BitmapTransformation() {
  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    messageDigest.update(ID_BYTES)
  }

  override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
    return FastBlurUtils.doBlur(toTransform, radius, true)
  }

  override fun equals(other: Any?): Boolean {
    return other is GlideBlurTransformation
  }

  override fun hashCode(): Int {
    return ID.hashCode()
  }

  companion object {
    private val ID = GlideBlurTransformation::class.java.name
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)
  }
}