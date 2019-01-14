package com.qi0.weslley.estudo_modalbottomsheet_kotlin

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

import kotlinx.android.synthetic.main.bottom_sheet_content_view.*
import kotlinx.android.synthetic.main.content_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottons_and_textviews.*
import java.util.ArrayList

class BottomSheetActivity : AppCompatActivity() {

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        setSupportActionBar(toolbar)

        recyclerView = recyclerViewDrag
        var adapter = ApplicationAdapter(this, listApplications(this))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val parentThatHasBottomSheetBehavior = recyclerViewDrag.parent.parent as FrameLayout
        mBottomSheetBehavior = BottomSheetBehavior.from(parentThatHasBottomSheetBehavior)
        if (mBottomSheetBehavior != null) {
            setStateText((mBottomSheetBehavior ).state)
            (mBottomSheetBehavior).setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    setStateText(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    setOffsetText(slideOffset)
                }
            })
        }

        /*fab.setOnClickListener {
            *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*//*
        }*/

        peek_me.setOnClickListener {
            mBottomSheetBehavior.peekHeight = drag_me.height
            drag_me.requestLayout()
        }

        as_modal.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun setOffsetText(slideOffset: Float) {
        offsetText.text = getString(R.string.offset, slideOffset)
    }

    private fun setStateText(newState: Int) {
        stateText.text = getStateAsString(newState)
    }

    fun getStateAsString(newState: Int): String {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> return getString(R.string.collapsed)
            BottomSheetBehavior.STATE_DRAGGING -> return getString(R.string.dragging)
            BottomSheetBehavior.STATE_EXPANDED -> return getString(R.string.expanded)
            BottomSheetBehavior.STATE_HIDDEN -> return getString(R.string.hidden)
            BottomSheetBehavior.STATE_SETTLING -> return getString(R.string.settling)
        }
        return getString(R.string.undefined)
    }

    private fun listApplications(context: Context): List<ApplicationInfo> {
        val flags = PackageManager.GET_META_DATA
        val installedApps = ArrayList<ApplicationInfo>()
        val pm = context.packageManager
        val applications = pm.getInstalledApplications(flags)
        for (appInfo in applications) {
            if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 1) {
                installedApps.add(appInfo)
            }
        }
        return installedApps
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}



