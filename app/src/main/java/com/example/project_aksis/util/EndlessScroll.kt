package com.example.project_aksis.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScroll : RecyclerView.OnScrollListener() {
    private var previousTotalItems = 0
    private var loading = true
    private var currentPage = 0
    private val visibleThreshold = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


        if (loading && totalItemCount > previousTotalItems) {
            loading = false
            previousTotalItems = totalItemCount
        }

        if (!loading && firstVisibleItemPosition > 0 &&
            (totalItemCount - visibleItemCount <= firstVisibleItemPosition + visibleThreshold)) {

            currentPage += 20
            onLoadMore(currentPage)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int)

}