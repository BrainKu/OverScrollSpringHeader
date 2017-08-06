package com.github.brainku.overscrollheader

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.SpringBackBehavior
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var mAppBarLayout : AppBarLayout
    lateinit var mCoordinatorLayout: CoordinatorLayout
    var mSpringBehavior = SpringBackBehavior()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAppBarLayout = findViewById(R.id.appbar_layout) as AppBarLayout
        mCoordinatorLayout = findViewById(R.id.coordinator) as CoordinatorLayout
        mAppBarLayout.post { setAppBarPreScroll(mAppBarLayout) }
        mSpringBehavior.setInitOffset(getDimenSize(100))
        (mAppBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior = mSpringBehavior
        initViewPager()
    }

    fun initViewPager() {
        val tabLayout = findViewById(R.id.tab_layout) as TabLayout;
        val viewPager = findViewById(R.id.viewpager) as ViewPager;
        viewPager.adapter = SimpleViewPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun setAppBarPreScroll(appbar: AppBarLayout) {
       val behavior = (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior
       behavior?.onNestedPreScroll(mCoordinatorLayout, mAppBarLayout, null, 0, getDimenSize(100), intArrayOf(0, 0))
    }

    fun getDimenSize(dp: Int) = (resources.displayMetrics.density * dp.toFloat()).toInt()
}

class SimpleViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return Fragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int) =
        when(position) {
            0 -> "Title 0"
            1 -> "Title 1"
            else -> "Hello World"
        }
}
