package com.example.myobservable

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.nineoldandroids.view.ViewHelper
import com.nineoldandroids.view.ViewPropertyAnimator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ObservableScrollViewCallbacks {
    private val MAX_TEXT_SCALE_DELTA = 0.3f

    private var mImageView: View? = null
    private var mOverlayView: View? = null
    private var mScrollView: ObservableScrollView? = null
    private var mTitleView: TextView? = null
    private var mFab: View? = null
    private var mActionBarSize = 0
    private var mFlexibleSpaceShowFabOffset = 0
    private var mFlexibleSpaceImageHeight = 0
    private var mFabMargin = 0
    private var mFabIsShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFlexibleSpaceImageHeight = resources.getDimensionPixelSize(R.dimen.flexible_space_image_height)
        mFlexibleSpaceShowFabOffset = resources.getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset)
        mActionBarSize = getActionBarSize()

        mImageView = findViewById(R.id.image)
        mOverlayView = findViewById(R.id.overlay)
        scroll.setScrollViewCallbacks(this)
        mTitleView = findViewById<TextView>(R.id.titles)
        titles.text = title
        title = null
        fab.setOnClickListener {
            Toast.makeText(this, "FAB is clicked", Toast.LENGTH_SHORT).show()
        }
        mFabMargin = resources.getDimensionPixelSize(R.dimen.margin_standard)
        ViewHelper.setScaleX(
            fab, 0f)
        ViewHelper.setScaleY(fab, 0f)

        ScrollUtils.addOnGlobalLayoutListener(scroll) {
            scroll?.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize)

            // If you'd like to start from scrollY == 0, don't write like this:
            //mScrollView.scrollTo(0, 0);
            // The initial scrollY is 0, so it won't invoke onScrollChanged().
            // To do this, use the following:
//            onScrollChanged(0, false, false);

            // You can also achieve it with the following codes.
            // This causes scroll change from 1 to 0.
            //mScrollView.scrollTo(0, 1);
            //mScrollView.scrollTo(0, 0);
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        var mFabIsShown = false
        // Translate overlay and image
        val flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize.toFloat()
        val minOverlayTransitionY = mActionBarSize - mOverlayView!!.height
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY.toFloat(), minOverlayTransitionY.toFloat(), 0f))
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2.toFloat(), minOverlayTransitionY.toFloat(), 0f))

        // Change alpha of overlay

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat(scrollY.toFloat() / flexibleRange, 0f, 1f))

        // Scale title text

        // Scale title text
        val scale = 1 + ScrollUtils.getFloat(
            (flexibleRange - scrollY) / flexibleRange,
            0f,
            MAX_TEXT_SCALE_DELTA
        )
        ViewHelper.setPivotX(mTitleView, 0f)
        ViewHelper.setPivotY(mTitleView, 0f)
        ViewHelper.setScaleX(mTitleView, scale)
        ViewHelper.setScaleY(mTitleView, scale)

        // Translate title text

        // Translate title text
        val maxTitleTranslationY = (mFlexibleSpaceImageHeight - mTitleView!!.height * scale).toInt()
        val titleTranslationY = maxTitleTranslationY - scrollY
        ViewHelper.setTranslationY(mTitleView, titleTranslationY.toFloat())

        // Translate FAB

        // Translate FAB
        val maxFabTranslationY = mFlexibleSpaceImageHeight - fab!!.height / 2
        val fabTranslationY = ScrollUtils.getFloat(
            -scrollY + mFlexibleSpaceImageHeight - fab!!.height / 2.toFloat(),
            mActionBarSize - fab!!.height / 2.toFloat(),
            maxFabTranslationY.toFloat()
        )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            val lp = fab!!.layoutParams as FrameLayout.LayoutParams
            lp.leftMargin = mOverlayView!!.width - mFabMargin - fab!!.width
            lp.topMargin = fabTranslationY.toInt()
            fab!!.requestLayout()
        } else {
            ViewHelper.setTranslationX(
                fab,
                mOverlayView!!.width - mFabMargin - fab!!.width.toFloat()
            )
            ViewHelper.setTranslationY(fab, fabTranslationY)
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab()
        } else {
            showFab()
        }
    }

    override fun onDownMotionEvent() {

    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

    }

    protected fun getActionBarSize(): Int {
        val typedValue = TypedValue()
        val textSizeAttr = intArrayOf(R.attr.actionBarSize)
        val indexOfAttrTextSize = 0
        val a = obtainStyledAttributes(typedValue.data, textSizeAttr)
        val actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1)
        a.recycle()
        return actionBarSize
    }

    private fun showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(fab).cancel()
            ViewPropertyAnimator.animate(fab).scaleX(1f).scaleY(1f).setDuration(200).start()
            mFabIsShown = true
        }
    }

    private fun hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(fab).cancel()
            ViewPropertyAnimator.animate(fab).scaleX(0f).scaleY(0f).setDuration(200).start()
            mFabIsShown = false
        }
    }
}