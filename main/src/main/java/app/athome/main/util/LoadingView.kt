package app.athome.main.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import app.athome.main.R

class LoadingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    companion object {
        const val DEFAULT_ANIMATION_DURATION = 2500
        const val DEFAULT_NUM_DOTS = 3
        const val DEFAULT_CIRCLE_COLOR = Color.BLACK
        const val DEFAULT_CIRCLE_RADIUS = 24f
        const val DEFAULT_CIRCLE_SPACING = 10f
        const val DEFAULT_CIRCLE_TRAVEL = 30f
    }

    private val animatorSet: AnimatorSet = AnimatorSet()
    private val dots = arrayListOf<Dot>()
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var circleRadius = DEFAULT_CIRCLE_RADIUS
        set(value) {
            field = value
            requestLayout()
        }

    var circleSpacing = DEFAULT_CIRCLE_SPACING
        set(value) {
            field = value
            requestLayout()
        }

    var circleTravel = DEFAULT_CIRCLE_TRAVEL
        set(value) {
            field = value
            requestLayout()
        }

    var animationDuration = DEFAULT_ANIMATION_DURATION
        set(value) {
            field = value
            dotAnimationDuration = animationDuration / numDots
        }

    var circleColor = DEFAULT_CIRCLE_COLOR
        set(value) {
            field = value
            paint.color = value
        }

    var numDots = DEFAULT_NUM_DOTS
        set(value) {
            field = value
            dotAnimationDuration = animationDuration / numDots
            initDots()
            requestLayout()
        }

    private var dotAnimationDuration: Int
    private val calculatedHeight: Int

    init {
        val attrSet = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        with(attrSet) {
            circleRadius = getDimension(R.styleable.LoadingView_circleRadius, DEFAULT_CIRCLE_RADIUS)
            circleSpacing = getDimension(R.styleable.LoadingView_circleSpacing, DEFAULT_CIRCLE_SPACING)
            circleTravel = getDimension(R.styleable.LoadingView_circleTravel, DEFAULT_CIRCLE_TRAVEL)
            circleColor = getColor(R.styleable.LoadingView_circleColor, DEFAULT_CIRCLE_COLOR)
            numDots = getInt(R.styleable.LoadingView_numDots, DEFAULT_NUM_DOTS)
            animationDuration = getInt(R.styleable.LoadingView_animationDuration, DEFAULT_ANIMATION_DURATION)
            dotAnimationDuration = animationDuration / numDots

            recycle()
        }

        calculatedHeight = (circleRadius * 2 + circleTravel).toInt()
        initDots()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        dots.forEach { it.draw(canvas, paint) }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val calculatedWidth = (dots.last().x + circleRadius).toInt()

        setMeasuredDimension(calculatedWidth, calculatedHeight)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet.cancel()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            animatorSet.start()
        } else {
            animatorSet.cancel()
            dots.forEach {
                it.reset()
            }
        }
    }

    fun show() {
        if (visibility != VISIBLE) {
            visibility = VISIBLE
        }
    }

    fun hide() {
        if (visibility != GONE) {
            visibility = GONE
        }
    }

    private fun initDots() {
        dots.clear()
        val startY = calculatedHeight - circleRadius
        var dotX = circleRadius
        for (i in 0 until numDots) {
            dots.add(Dot(dotX, startY, circleRadius))
            dotX += circleSpacing + (circleRadius * 2)
        }

        initAnimators()
    }

    private fun initAnimators() {
        var starOffset = 0L
        val animators = arrayListOf<ValueAnimator>()
        for (i in 0 until numDots) {
            val dot = dots[i]
            animators.add(ValueAnimator.ofFloat(dot.y, circleRadius, dot.y).apply {
                duration = (dotAnimationDuration * 0.8).toLong()
                startDelay = starOffset
                starOffset += 100
                addUpdateListener {
                    dot.y = it.animatedValue as Float
                    postInvalidate()
                }
            })
        }

        animatorSet.apply {
            playTogether(animators as Collection<Animator>?)
            start()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    start()
                }
            })
        }
    }

    inner class Dot(var x: Float, var y: Float, var radius: Float) {

        private val startX: Float = x
        private val startY: Float = y

        fun draw(canvas: Canvas?, paint: Paint) {
            canvas?.drawCircle(x, y, radius, paint)
        }

        fun reset() {
            x = startX
            y = startY
        }
    }
}
