package com.jazastudio.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_introduction.*
import splitties.activities.start
import timber.log.Timber

class IntroductionActivity : AppCompatActivity() {

    private var countViewPager: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        if (!Prefs.instance.isFirst) {
            Timber.d("first")
            start<MainActivity>()
        }
        setContentView(R.layout.activity_introduction)
        viewpager_introduction?.adapter = MyPagerAdapter(supportFragmentManager)
        dot_introduction_indicator?.setViewPager(viewpager_introduction)

        viewpager_introduction?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (countViewPager == viewpager_introduction.adapter?.count) {
                    btn_done.visibility = View.VISIBLE
                } else {
                    btn_done.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {
                countViewPager = position + 1
                if (countViewPager == viewpager_introduction.adapter?.count) {
                    btn_done.visibility = View.VISIBLE
                } else {
                    btn_done.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        btn_done?.setOnClickListener {
            Prefs.instance.isFirst = false
            start<MainActivity>()
        }

        btn_next?.setOnClickListener {
            if (viewpager_introduction.currentItem < viewpager_introduction.adapter!!.count) {
                viewpager_introduction.currentItem = viewpager_introduction.currentItem + 1
            }
            if (viewpager_introduction.currentItem == viewpager_introduction.adapter?.count) {
                btn_done.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        finish()
    }
}