package com.nate.ssmvp.imageloader.glide

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader.TileMode
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.security.MessageDigest

/**
 * 会居中的圆角变换
 * Created by Nate on 2020/5/4
 */
class GlideRoundTransform @JvmOverloads constructor(dp: Int = 10) : CenterCrop() {
  override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
    //glide4.0+
    val transform = super.transform(pool, toTransform, outWidth, outHeight)
    return roundCrop(pool, transform)
  }

  init {
    radius = SizeUtils.dp2px(dp.toFloat()).toFloat()
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

  companion object {
    private var radius = 10f
    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
      if (source == null) return null
      var result: Bitmap? = pool[source.width, source.height, ARGB_8888]
      if (result == null) {
        result = Bitmap.createBitmap(source.width, source.height, ARGB_8888)
      }
      val canvas = Canvas(result!!)
      val paint = Paint()
      paint.shader = BitmapShader(source, TileMode.CLAMP, TileMode.CLAMP)
      paint.isAntiAlias = true
      val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
      canvas.drawRoundRect(rectF, radius, radius, paint)
      return result
    }
  }

}