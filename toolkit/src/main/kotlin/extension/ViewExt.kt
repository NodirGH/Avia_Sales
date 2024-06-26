@file:JvmName("ViewExt")

package extension

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.view.animation.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.pow


fun TextView.isEmpty(): Boolean = TextUtils.isEmpty(this.text.toString())

fun TextView.isNotEmpty(): Boolean = TextUtils.isEmpty(this.text.toString()).not()
fun View.show() {
    this.visibility = (View.VISIBLE)
}

fun AppBarLayout.scrollScale(toolbar: CollapsingToolbarLayout) {
    this.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { bar, verticalOffset ->
        val range = bar.totalScrollRange
        if (range != 0) {
            val factor = 1f - (-verticalOffset.toFloat() / range)
            if (factor >= 0) {
                toolbar.scaleX = factor
                toolbar.scaleY = factor
                toolbar.pivotY = toolbar.height.toFloat()
                toolbar.pivotX = toolbar.width.toFloat() / 2
                toolbar.alpha = factor.pow(5)
            }
        }
    })
}

fun View.showIf(condition: Boolean) {
    if (condition) this.visibility = (View.VISIBLE)
}

fun View.manageVisibility(condition: Boolean) {
    if (condition) this.visibility = (View.VISIBLE)
    else visibility = View.GONE
}

fun View.hide(): View {
    this.visibility = (View.GONE)
    return this
}


fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.hideKeyboardWithRemoveFocus() {
    hideKeyboard()
    this.clearFocus()
}

fun View.showKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun View.showKeyBoardWithFocus() {
    showKeyBoard()
    requestFocus()
}

fun View.invisible(): View {
    this.visibility = (View.INVISIBLE)
    return this
}

fun View.gone(): View {
    this.visibility = (View.GONE)
    return this
}

fun View.isVisible(): Boolean {
    return this.visibility == (View.VISIBLE)
}

fun View.isNotVisible(): Boolean {
    return this.visibility != (View.VISIBLE)
}

fun View.toggleVisibility() = if (isVisible()) hide() else show()

//fun View.toggleVisibilityAnim(duration: Long = 200) {
//    if (isVisible()) hideAnim(duration) else showAnim(duration)
//}

fun View.withAlpha(value: Float): View {
    alpha = value
    return this
}

fun View.translationX(x: Float): View {
    translationX = x
    return this
}

fun View.translationY(y: Float): View {
    translationY = y
    return this
}

fun View.withDelayShow(delay: Long = 300L, init: View.() -> Unit): View {
    Handler(Looper.getMainLooper()).postDelayed({
        visibility = View.VISIBLE
        init()
    }, delay)
    return this
}

@JvmOverloads
fun View.showAnim(duration: Long = 200) {
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate()
        .setDuration(duration)
        .alpha(1f)
        .setInterpolator(DecelerateInterpolator())
        .setListener(null)
}

//@JvmOverloads
//fun View.hideAnim(duration: Long = 200, endListener: (() -> Unit)? = null) {
//    this.animate()
//        .setDuration(duration)
//        .alpha(0f)
//        .setListener(animOnFinish {
//            this.visibility = View.GONE
//            endListener?.invoke()
//        })
//}

//fun View.invisibleAnim(duration: Long = 200, endListener: (() -> Unit)? = null) {
//    this.animate()
//        .setDuration(duration)
//        .alpha(0f)
//        .setListener(animOnFinish {
//            this.visibility = View.INVISIBLE
//            endListener?.invoke()
//        })
//}

fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener { listener.onClick(this) }
}

fun View.onClick(listener: (View) -> Unit) {
    this.setOnClickListener { listener.invoke(it) }
}

fun View.lock() {
    isEnabled = false
    isClickable = false
}

fun View.unlock() {
    isEnabled = true
    isClickable = true
}

fun ViewGroup.lockAllChildren() {
    views().forEach { it.lock() }
}

fun ViewGroup.unlockAllChildren() {
    views().forEach { it.unlock() }
}

fun View.doOnPreDraw(callback: () -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            callback.invoke()
            return true
        }
    })
}

operator fun ViewGroup.get(pos: Int): View = getChildAt(pos)

fun ViewGroup.views(): List<View> {
    return (0 until childCount).map { getChildAt(it) }
}

inline fun View.snack(message: String, f: Snackbar.() -> Unit, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

inline fun View.snack(
    resId: Int,
    f: Snackbar.() -> Unit,
    @IdRes length: Int = Snackbar.LENGTH_LONG
) {
    val snack = Snackbar.make(this, resId, length)
    snack.f()
    snack.show()
}

fun TextView.stringText() = this.text.toString()
fun TextView.clearText() {
    this.text = ""
}

fun EditText.clearText() {
    text.clear()
}

fun EditText.moveCursorToEnd() {
    if (text.isNotEmpty()) setSelection(text.length)
}

fun View.showKeyBord() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

    this.requestFocus()
}

fun View.showKeyBoardImplicit() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun View.hideKeyBoardImplicit() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun EditText.getString() = this.text.toString()

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            removeOnGlobalLayoutListener(this)
            f()
        }
    })
}

fun ViewGroup.transition(duration: Long = 200, transition: Transition = ChangeBounds()) {
    transition.duration = duration
    TransitionManager.beginDelayedTransition(this, transition)
}

//fun View.animateBackgroundColor(
//    @ColorRes colorFromRes: Int,
//    @ColorRes colorToResId: Int,
//    duration: Long = 260,
//    endListener: (() -> Unit)? = null
//) {
//    val colorToRes = this.context.getColorCompat(colorToResId)
//    val colorFrom = this.context.getColorCompat(colorFromRes)
//
//    animateBackgroundColor(duration, from = colorFrom, to = colorToRes, endListener = endListener)
//}

//fun View.animateBackgroundColor(
//    duration: Long = 500L,
//    from: Int,
//    to: Int,
//    endListener: (() -> Unit)? = null
//) {
//    val anim = ValueAnimator()
//    anim.setIntValues(from, to)
//    anim.setEvaluator(ArgbEvaluator())
//    anim.addUpdateListener { valueAnimator -> this.setBackgroundColor(valueAnimator.animatedValue as Int) }
//
//    anim.duration = duration
//    endListener?.let {
//        anim.addListener(animOnFinish(endListener))
//    }
//    anim.start()
//}

fun View.getCurrentBackgroundColor(): Int? = (this.background as? ColorDrawable)?.color

fun View.animateColorWhatever(
    duration: Long = 500L,
    colorFrom: Int,
    colorTo: Int,
    animation: (ValueAnimator) -> Unit
) {
    val anim = ValueAnimator()
    anim.setIntValues(colorFrom, colorTo)
    anim.setEvaluator(ArgbEvaluator())
    anim.addUpdateListener { valueAnimator -> animation(valueAnimator) }

    anim.duration = duration
    anim.start()
}

fun TextInputLayout.setErrorMess(isValid: Boolean, errorStringId: Int) {
    if (isValid) {
        error = null
        isErrorEnabled = false
    } else {
        isErrorEnabled = true
        error = this.context.getString(errorStringId)
    }
}

//fun View.animateHSVBackgrond(
//    colorFrom: Int,
//    colorTo: Int,
//    duration: Long = 500L,
//    endListener: (() -> Unit)? = null
//) {
//    val from = FloatArray(3)
//    val to = FloatArray(3)
//
//    Color.colorToHSV(colorFrom, from) // from white
//    Color.colorToHSV(colorTo, to) // to red
//
//    val anim = ValueAnimator.ofFloat(0F, 1F) // animate from 0 to 1
//    anim.duration = duration // for 300 ms
//
//    val hsv = FloatArray(3) // transition color
//    anim.addUpdateListener { animation ->
//        // Transition along each axis of HSV (hue, saturation, value)
//        hsv[0] = from[0] + (to[0] - from[0]) * animation.animatedFraction
//        hsv[1] = from[1] + (to[1] - from[1]) * animation.animatedFraction
//        hsv[2] = from[2] + (to[2] - from[2]) * animation.animatedFraction
//
//        this.setBackgroundColor(Color.HSVToColor(hsv))
//    }
//    endListener?.let {
//        anim.addListener(animOnFinish(endListener))
//    }
//    anim.start()
//}


fun EditText.onDoneTap(callback: () -> Unit) = onKeyTap(EditorInfo.IME_ACTION_DONE, callback)

private fun EditText.onKeyTap(imeKeyCode: Int, callback: () -> Unit) {
    imeOptions = imeKeyCode
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener if (actionId == imeKeyCode) {
            callback(); true
        } else false
    }
}

fun TextView.setDrawableStart(@DrawableRes icon: Int) {
    val drawable = AppCompatResources.getDrawable(this.context, icon)
    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}

fun TextView.setDrawableTop(@DrawableRes icon: Int) {
    val drawable = AppCompatResources.getDrawable(this.context, icon)
    setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
}

fun TextView.setDrawableEnd(@DrawableRes icon: Int) {
    val drawable = AppCompatResources.getDrawable(this.context, icon)
    setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}

fun TextView.setDrawableBottom(@DrawableRes icon: Int) {
    val drawable = AppCompatResources.getDrawable(this.context, icon)
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)
}

//fun RatingBar.setStarsColorRes(@ColorRes colorRes: Int) {
//    val stars = progressDrawable
//    val color = context.getColorCompat(colorRes)
//}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun EditText.hideKeyBord() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        this.windowToken, 0
    )
}

// fun View.hideAnimWithScale() {
//  if (this.withAlpha == 1f) {
//    this.animate()
//        .withAlpha(0f)
//        .scaleX(0f)
//        .scaleY(0f)
//        .setDuration(200)
//        .start()
//  }
// }
//
// fun View.showAnimWithScale() {
//  if (this.withAlpha == 0f) {
//    this.animate()
//        .withAlpha(1f)
//        .scaleY(1f)
//        .scaleX(1f)
//        .setDuration(200)
//        .start()
//  }
// }

fun View.hideAnimWithScale(
    duration: Long = 200,
    interpolator: Interpolator = AccelerateDecelerateInterpolator()
) {
    if (this.isVisible()) {
        this.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(duration)
            .setInterpolator(interpolator)
            .withEndAction { this.hide() }
            .start()
    }
}

fun View.showAnimWithScale(
    duration: Long = 200,
    interpolator: Interpolator = AccelerateDecelerateInterpolator()
) {
    if (this.isNotVisible()) {
        doOnPreDraw {
            alpha = 0f
            scaleY = 0f
            scaleX = 0f
            this.show()
            this.animate()
                .alpha(1f)
                .scaleY(1f)
                .scaleX(1f)
                .setInterpolator(interpolator)
                .setDuration(duration)
                .start()
        }
    }
}

fun View.showAnimWithScaleIf(show: Boolean, duration: Long = 200, interpolator: Boolean = false) {
    if (show) showAnimWithScale(
        duration,
        if (interpolator) OvershootInterpolator() else AccelerateDecelerateInterpolator()
    )
    else hideAnimWithScale(
        duration,
        if (interpolator) AnticipateInterpolator() else AccelerateDecelerateInterpolator()
    )
}

@TargetApi(21)
fun View.showAnimWithReveal(
    duration: Long = 200,
    endAction: () -> Unit = {},
    x: Int = -1,
    y: Int = -1
) {
    if (isNotVisible()) {
        this.doOnPreDraw {
            alpha = 1f
            translationY = 0f
            translationX = 0f
            val centerX = if (x >= 0) x else width / 2
            val centerY = if (y >= 0) y else height / 2
            val finalRadius = Math.hypot(centerX.toDouble(), centerY.toDouble())

            val revealAnim = ViewAnimationUtils.createCircularReveal(
                this,
                centerX,
                centerY,
                0f,
                finalRadius.toFloat()
            )
            revealAnim.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(p0: Animator?) {
//                    endAction()
//                }
            })

            show()
            revealAnim.start()
        }
    } else {
        endAction()
    }
}

@TargetApi(21)
fun View.hideAnimWithReveal(
    duration: Long = 200,
    endAction: () -> Unit = {},
    x: Int = -1,
    y: Int = -1
) {
    if (isVisible()) {
        val cx = if (x >= 0) x else width / 2
        val cy = if (y >= 0) y else height / 2
        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble())

        val revealAnim =
            ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius.toFloat(), 0f)
        revealAnim.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator?) {
//                hide()
//                endAction()
//            }
        })

        revealAnim.start()
    } else {
        endAction()
    }
}

fun FloatingActionButton.animateIn(duration: Long = 200) {
    if (this.isNotVisible()) {
        doOnPreDraw {
            visibility = View.VISIBLE
            alpha = 0.5f
            scaleX = 0f
            scaleY = 0f
            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(OvershootInterpolator())
                .setDuration(duration)
                .start()
        }
    }
}

fun FloatingActionButton.animateOut(duration: Long = 200) {
    if (this.isVisible()) {
        animate()
            .alpha(0.5f)
            .scaleX(0f)
            .scaleY(0f)
            .withEndAction { this.invisible() }
            .setInterpolator(AnticipateInterpolator())
            .setDuration(duration)
            .start()
    }
}

fun View.blink(action: (() -> Unit)? = null) {

    this.alpha = 1f
    this.animate()
        .alpha(0f)
        .setDuration(200)
        .withEndAction {
            action?.invoke()
            this.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        }
        .start()
}

fun View.showAnimWithSlideUp(duration: Long = 300, endAction: () -> Unit = {}) {
    if (isNotVisible()) {
        show()
        this.doOnPreDraw {
            translationY = height.toFloat()
            alpha = 0.5f
            animate()
                .alpha(1f)
                .translationY(0f)
                .withEndAction(endAction)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(duration)
                .start()
        }
    } else {
        endAction()
    }
}

fun View.showAnimScaleUp(duration: Long = 300, endAction: () -> Unit = {}) {
    scaleX = 0.95f
    scaleY = 0.95f
    alpha = 0.5f
    animate()
        .alpha(1f)
        .scaleX(1f)
        .scaleY(1f)
        .withEndAction(endAction)
        .setDuration(duration)
        .start()

}

fun View.showAnimScaleUpZeroAlpha(duration: Long = 300, endAction: () -> Unit = {}) {
    visibility = View.VISIBLE
    scaleX = 0.95f
    scaleY = 0.95f
    alpha = 0.0f
    animate()
        .alpha(1f)
        .scaleX(1f)
        .scaleY(1f)
        .withEndAction(endAction)
        .setDuration(duration)
        .setStartDelay(150)
        .start()

}

fun View.showAnimWithSlideFromRight(duration: Long = 300, endAction: () -> Unit = {}) {
    if (isNotVisible()) {
        show()
        this.doOnPreDraw {
            translationX = width.toFloat()
            alpha = 0.5f
            animate()
                .alpha(1f)
                .translationX(0f)
                .withEndAction(endAction)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(duration)
                .start()
        }
    } else {
        endAction()
    }
}

fun View.showAnimWithSlideFromLeft(duration: Long = 300, endAction: () -> Unit = {}) {
    if (isNotVisible()) {
        show()
        this.doOnPreDraw {
            translationX = width.toFloat() * -1
            alpha = 0.5f
            animate()
                .alpha(1f)
                .translationX(0f)
                .withEndAction(endAction)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(duration)
                .start()
        }
    } else {
        endAction()
    }
}

fun View.hideAnimWithSlideDown(duration: Long = 300, endAction: () -> Unit = {}) {
    if (isVisible()) {
        animate()
            .alpha(0.5f)
            .translationY(height.toFloat())
            .withEndAction {
                hide()
                endAction()
            }
            .setInterpolator(AccelerateInterpolator())
            .setDuration(duration)
            .start()
    } else {
        endAction()
    }
}

fun View.invisibleAnimWithSlideDown(duration: Long = 300, endAction: () -> Unit = {}) {
    if (isVisible()) {
        animate()
            .alpha(0.5f)
            .translationY(height.toFloat())
            .withEndAction {
                this.invisible()
                endAction()
            }
            .setInterpolator(AccelerateInterpolator())
            .setDuration(duration)
            .start()
    } else {
        endAction()
    }
}

fun ProgressBar.updateColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.indeterminateDrawable.colorFilter =
            BlendModeColorFilter(ContextCompat.getColor(this.context, color), BlendMode.SRC_IN)
    } else {
        this.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(
                this.context,
                color
            ), PorterDuff.Mode.SRC_IN
        )
    }
}

fun RecyclerView.isLastVisible(): Boolean {
    val layoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
    val pos: Int = layoutManager.findLastCompletelyVisibleItemPosition()
    return pos + 1 >= adapter?.itemCount ?: 0
}

fun RecyclerView.isFirstVisible(): Boolean {
    val layoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
    val pos: Int = layoutManager.findFirstVisibleItemPosition()
    return pos == 0
}

fun TextView.addClickableLink(fullText: String, linkText: SpannableString, callback: () -> Unit) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents() // Prevent CheckBox state from being toggled when link is clicked
            callback.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true // Show links with underlines
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)
    val cs = TextUtils.expandTemplate(fullTextWithTemplate, linkText)

    text = cs
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}



//fun ImageView.loadNetworkImage(
//    url: String
//) {
//    val circularProgressDrawable = CircularProgressDrawable(this.context)
//    circularProgressDrawable.strokeWidth = 5f
//    circularProgressDrawable.centerRadius = 30f
//    circularProgressDrawable.start()
//    if (url.isEmpty()){
//        Glide.with(context)
//            .load(url)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(circularProgressDrawable)
//            .into(this)
//        return
//    }
//    val shimmer =
//        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
//            .setDuration(1200) // how long the shimmering animation takes to do one full sweep
//            .setBaseAlpha(0.9f) //the alpha of the underlying children
//            .setHighlightAlpha(0.8f) // the shimmer alpha amount
//            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
//            .setAutoStart(true)
//            .build()
//    val shimmerDrawable = ShimmerDrawable().apply {
//        setShimmer(shimmer)
//    }
//// This is the placeholder for the imageView
//
//
//    //TODO Turn on the local cache before prod
//    Glide.with(context)
////        .load(GlideUrlCustomCacheKey(url))
//        .load(url)
//        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .placeholder(circularProgressDrawable)
//        .into(this)
//}