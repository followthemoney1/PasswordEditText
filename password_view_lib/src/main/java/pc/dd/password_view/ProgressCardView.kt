package pc.dd.password_view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.support.v7.widget.CardView
import android.util.AttributeSet
import java.lang.Float.NaN
import java.util.jar.Attributes
import kotlin.properties.Delegates

class ProgressCardView(context: Context, attrs: AttributeSet) : CardView(context, attrs) {
    var cardView:CardView? = null

    var paint: Paint = Paint()
    var progressWeight: Int = 0
    var progress: Int by Delegates.observable(0) { prop, old, new ->
        invalidate()
    }

    var xLastPosition: Float = NaN
    var xStart: Float = NaN
    var xEnd: Float = NaN
    var yPosition: Float = NaN

    var locOnDraw = false

    init {
        cardView = CardView(context)
        setAttr(context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText))

        paint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 10f
            isAntiAlias = true
        }
    }

    private fun setAttr(attributes: TypedArray){
        val cardBackgroundColor = attributes.getColor(R.styleable.PasswordEditText_cardBackgroundColor, Color.WHITE )
        val cardElevation = attributes.getDimension(R.styleable.PasswordEditText_cardElevation,0.3f)
        val cardMaxElevation = attributes.getDimension(R.styleable.PasswordEditText_cardMaxElevation, 0.6f)
        val cardUseCompatPadding = attributes.getBoolean(R.styleable.PasswordEditText_cardUseCompatPadding, true)
        val cardPreventCornerOverlap = attributes.getBoolean(R.styleable.PasswordEditText_cardPreventCornerOverlap, false)

        cardView?.setCardBackgroundColor(cardBackgroundColor)
        cardView?.elevation = cardElevation
        cardView?.maxCardElevation = cardMaxElevation
        cardView?.useCompatPadding = cardUseCompatPadding
        cardView?.preventCornerOverlap = cardPreventCornerOverlap

        attributes.recycle()
    }

    fun setProgressColorFromResources(color: Int) {
        paint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xStart = x - 8
        progressWeight = width.div(4)
        yPosition = height - 4.toFloat()

        if (xLastPosition.isNaN()) xLastPosition = xStart
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!locOnDraw) {
            xEnd = xStart + (progress * progressWeight)
            animateValue()
        } else canvas?.drawLine(xStart, yPosition, xEnd, yPosition, paint)
    }

    private fun animateValue() {
        locOnDraw = true

        val animator = ValueAnimator.ofFloat(xLastPosition, xEnd)

        animator.duration = 500

        animator.addUpdateListener { anim ->
            xEnd = anim.animatedValue as Float
            invalidate()
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler().postDelayed({
                    xLastPosition = xEnd
                    locOnDraw = false
                }, 100)
            }
        })

        animator.start()
    }
}
