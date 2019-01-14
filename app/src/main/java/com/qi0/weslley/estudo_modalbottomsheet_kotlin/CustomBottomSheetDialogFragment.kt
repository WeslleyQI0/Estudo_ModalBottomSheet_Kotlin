package com.qi0.weslley.estudo_modalbottomsheet_kotlin

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView

/**
 * Created by Nikola D. on 2/25/2016.
 */
class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mOffsetText: TextView? = null
    private var mStateText: TextView? = null
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            setStateText(newState)
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }

        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            setOffsetText(slideOffset)
        }
    }
    private val mLinearLayoutManager: LinearLayoutManager? = null
    private val mAdapter: ApplicationAdapter? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)


        //
        //        recyclerView.setLayoutManager(mLinearLayoutManager);
        //        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.bottom_sheet_dialog_content_view, null)
        dialog.setContentView(contentView)
        val layoutParams = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        mOffsetText = contentView.findViewById<View>(R.id.offsetText) as TextView
        mStateText = contentView.findViewById<View>(R.id.stateText) as TextView
    }


    private fun setOffsetText(slideOffset: Float) {
        ViewCompat.postOnAnimation(mOffsetText!!) { mOffsetText!!.text = getString(R.string.offset, slideOffset) }
    }

    private fun setStateText(newState: Int) {
        mStateText!!.text = (activity as BottomSheetActivity).getStateAsString(newState)
    }
}
