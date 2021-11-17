package com.erkutaras.showcaseview

import android.content.Context
import android.view.View

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
class ShowcaseGroup(
    context: Context,
    var showcaseModels: ArrayList<ShowcaseModel>,
    var onFinish: () -> Unit = {},
    setContentView: (ShowcaseView) -> Unit = {},
) : OnIndexChangedListener {

    private var currentIndex = 0

    //MainView
    private lateinit var layout: ShowcaseView

    //OnIndexChangedListener
    private lateinit var onIndexChangedListener: OnIndexChangedListener

    init {
        if (showcaseModels.isEmpty()) {
            onFinish()
        } else {
            //To initialize OnIndexChangedListener
            initOnIndexChangedListener()

            //To initialize the main layout view
            layout = ShowcaseView(context)

            //To add the view
            setContentView(layout)

            //To handle on layout clicked
            onLayoutClicked()

            //To send a call back of the currentIndex
            updateCurrentIndex()
        }
    }

    /**
     * To handle on layout clicked
     * */
    private fun onLayoutClicked() {
        with(layout) {
            setOnCancelClickListener(View.OnClickListener { onFinish() })
            setOnNextClickListener(View.OnClickListener { showNextLayout() })
            setOnPreviousClickListener(View.OnClickListener { showPreviousLayout() })
            setOnCustomButtonClickListener(View.OnClickListener { showNextLayout() })
        }
    }


    /**
     * To initialize OnIndexChangedListener
     * */
    private fun initOnIndexChangedListener() {
        onIndexChangedListener = this
    }

    /**
     * To update the ui with the next view
     * */
    private fun updateView(isBntNextSelected: Boolean, isBntPreviousSelected: Boolean) {
        val currentShowModel = showcaseModels[currentIndex];
        currentShowModel.isBtnNextSelected = isBntNextSelected
        currentShowModel.isBtnPreviousSelected = isBntPreviousSelected
        layout.updateView(currentShowModel)
    }

    /**
     * To send a call of the index
     * */
    private fun updateCurrentIndex() {
        onIndexChangedListener.onChanged(currentIndex)
    }

    /**
     * To show next layout
     * */
    private fun showNextLayout() {
        currentIndex += 1
        if (isIndexInRange(currentIndex)) {
            updateCurrentIndex()//send the call back
        } else {
            onFinish()
        }
    }

    /**
     * To show previous layout
     * */
    private fun showPreviousLayout() {
        if (isNotFirstItem(currentIndex)) {
            currentIndex -= 1
            updateCurrentIndex()//send the call back
        } else {
            onFinish()
        }
    }

    /**
     * @return true if index is in of range showcaseModels list
     * */
    private fun isIndexInRange(index: Int): Boolean {
        return index < showcaseModels.size
    }

    /**
     * @return true if index is not and greater than zero
     * */
    private fun isNotFirstItem(index: Int): Boolean {
        return index != 0 && index > 0
    }

    /**
     * call back of {@OnIndexChangedListener}
     * */
    override fun onChanged(index: Int) {
        val isBtnPreviousSelected = (index != 0) //At the first item imgPrevious.isSelected = false
        val isBtnNextSelected = (index + 1) < showcaseModels.size//At the last item imgNext.isSelected = false
        updateView(isBtnNextSelected, isBtnPreviousSelected)
    }
}