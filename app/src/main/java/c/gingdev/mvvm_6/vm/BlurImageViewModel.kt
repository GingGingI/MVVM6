package c.gingdev.mvvm_6.vm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class BlurImageViewModel(private val context: Context): ViewModel() {
    val baseImg = ObservableField<Drawable?>()
    val bluredImg = ObservableField<Drawable>()

    fun blur() {
        if (baseImg.get() == null)
            return

        val bitmap = getBitmapFromDrawable(baseImg.get()!!)

        val rs = RenderScript.create(context)
        val input
                = Allocation.createFromBitmap(rs, bitmap)
        val output = Allocation.createTyped(rs, input.type)

        ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)).apply {
            setRadius(15.0f)
            setInput(input)
            forEach(output)
            output.copyTo(bitmap)
        }.also {
            bluredImg.set(BitmapDrawable(context.resources, bitmap))
        }
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable)
            if (drawable.bitmap != null)
                return drawable.bitmap

        var bitmap: Bitmap? = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0)
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        else
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        drawable.apply {
            bounds.set(0, 0, canvas.width, canvas.height)
            draw(canvas)
        }
        return bitmap!!
    }
}