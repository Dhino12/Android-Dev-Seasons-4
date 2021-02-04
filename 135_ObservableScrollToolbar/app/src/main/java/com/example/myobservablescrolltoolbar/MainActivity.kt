package com.example.myobservablescrolltoolbar

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.nineoldandroids.view.ViewHelper

class MainActivity : AppCompatActivity(), ObservableScrollViewCallbacks {

    private var mFlexibleSpaceHeight:Int? = 0
    private lateinit var mToolbar:View
    private lateinit var mTitleView:TextView
    private lateinit var mFlexibleSpaceView:View

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean){
        val win = activity.window
        val winParams =  win.attributes
        if(on){
            winParams.flags = winParams.flags or bits
        }else{
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)

            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_VISIBLE
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                window.statusBarColor = Color.TRANSPARENT
            }
        }

        // setSupportActionBar(findViewById(R.id.toolbar))
        val ab = supportActionBar
        if(ab != null) supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mFlexibleSpaceView = findViewById(R.id.flexible_space)
        mTitleView = findViewById<TextView>(R.id.titles)
        mTitleView.text = title
        title = null
        mToolbar = findViewById(R.id.toolbar)

        val scrollView = findViewById<ObservableScrollView>(R.id.scroll)
        scrollView.setScrollViewCallbacks(this)

        mFlexibleSpaceHeight = resources.getDimensionPixelSize(R.dimen.flexible_space_height)
        val flexibleSpaceAndToolbarHeight = mFlexibleSpaceHeight!! + getActionBarSize()

        findViewById<FrameLayout>(R.id.body).setPadding(0, flexibleSpaceAndToolbarHeight, 0, 0)
        mFlexibleSpaceView.layoutParams.height = flexibleSpaceAndToolbarHeight

        ScrollUtils.addOnGlobalLayoutListener(mTitleView){
            updateFlexibleSpaceText(scrollView.currentScrollY)
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        updateFlexibleSpaceText(scrollY)
    }

    override fun onDownMotionEvent() {

    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
        val ab: ActionBar? = supportActionBar
        if (scrollState === ScrollState.UP) {
            if (ab != null) {
                if (ab.isShowing) {
                    ab.hide()
                }
            }
        } else if (scrollState === ScrollState.DOWN) {
            if (ab != null) {
                if (!ab.isShowing) {
                    ab.show()
                }
            }
        }
    }

    private fun updateFlexibleSpaceText(scrollY: Int){
        ViewHelper.setTranslationY(mFlexibleSpaceView, (-scrollY).toFloat())
        val adjustedScrollY = ScrollUtils.getFloat(
            scrollY.toFloat(),
            0f,
            mFlexibleSpaceHeight!!.toFloat()
        ).toInt()
        val maxScale = (mFlexibleSpaceHeight!! - mToolbar.getHeight()) / mToolbar.getHeight()
        val scale = maxScale * (mFlexibleSpaceHeight!!.toFloat() - adjustedScrollY) / mFlexibleSpaceHeight!!

        ViewHelper.setPivotX(mTitleView, 0F)
        ViewHelper.setPivotY(mTitleView, 0F)
        ViewHelper.setScaleX(mTitleView, 1 + scale)
        ViewHelper.setScaleY(mTitleView, 1 + scale)
        val maxTitleTranslationY = mToolbar.height + mFlexibleSpaceHeight!! - (mTitleView.height * (1 + scale)).toInt()
        val titleTranslationY = (maxTitleTranslationY * (mFlexibleSpaceHeight!! - adjustedScrollY) / mFlexibleSpaceHeight!!) as Int
        ViewHelper.setTranslationY(mTitleView, titleTranslationY.toFloat())
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
}