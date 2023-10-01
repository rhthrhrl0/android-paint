package woowacourse.paint.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import woowacourse.paint.R
import woowacourse.paint.customView.container.ContentContainer
import woowacourse.paint.customView.content.Content
import woowacourse.paint.customView.content.ContentType
import woowacourse.paint.util.getEnum

class PaintBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val contents = ContentContainer()
    val drawnPaths: List<Content>
        get() = contents.getDrawnContents()

    var currentColor: Int
        set(@ColorInt value) {
            contents.paintInfo.currentColor = value
        }
        get() = contents.paintInfo.currentColor

    var minStrokeWidth: Float
        set(value) {
            contents.paintInfo.minStrokeWidth = value
        }
        get() = contents.paintInfo.minStrokeWidth

    var maxStrokeWidth: Float
        set(value) {
            contents.paintInfo.maxStrokeWidth = value
        }
        get() = contents.paintInfo.maxStrokeWidth

    var currentStrokeWidth: Float
        set(value) {
            contents.paintInfo.currentStrokeWidth = value
        }
        get() = contents.paintInfo.currentStrokeWidth

    var contentType: ContentType
        set(value) {
            contents.contentType = value
        }
        get() = contents.contentType

    init {
        if (attrs != null) initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PaintBoard)
        this.minStrokeWidth = typedArray.getFloat(
            R.styleable.PaintBoard_minStrokeWidth,
            contents.paintInfo.minStrokeWidth,
        )
        this.maxStrokeWidth = typedArray.getFloat(
            R.styleable.PaintBoard_maxStrokeWidth,
            contents.paintInfo.maxStrokeWidth,
        )
        this.currentColor = typedArray.getColor(
            R.styleable.PaintBoard_currentColor,
            contents.paintInfo.currentColor,
        )
        this.currentStrokeWidth = typedArray.getFloat(
            R.styleable.PaintBoard_currentStrokeWidth,
            contents.paintInfo.currentStrokeWidth,
        )
        this.contentType = typedArray.getEnum(R.styleable.PaintBoard_contentType, this.contentType)
        typedArray.recycle()
    }

    fun changeDrawnPaths(paths: List<Content>) {
        contents.changeDrawnContents(paths)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        contents.drawOnCanvas(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        contents.updateContent(event)
        invalidate()
        return true
    }
}
