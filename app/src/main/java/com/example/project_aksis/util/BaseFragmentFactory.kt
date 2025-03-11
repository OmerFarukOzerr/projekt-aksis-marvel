package com.example.project_aksis.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.project_aksis.view.DetailFragment
import com.example.project_aksis.view.ListFragment
import com.example.project_aksis.view.adapter.ComicsAdapter
import com.example.project_aksis.view.adapter.EventsAdapter
import com.example.project_aksis.view.adapter.ListAdapterGrid
import com.example.project_aksis.view.adapter.ListAdapterList
import com.example.project_aksis.view.adapter.SeriesAdapter
import com.example.project_aksis.view.adapter.StoryNamesAdapter
import com.example.project_aksis.viewmodel.DetailViewModel
import com.example.project_aksis.viewmodel.ListViewModel
import javax.inject.Inject

class BaseFragmentFactory @Inject constructor(
    private val listViewModel: ListViewModel,
    private val listAdapterGrid : ListAdapterGrid,
    private val detailViewModel: DetailViewModel,
    private val comicsAdapter: ComicsAdapter,
    private val eventsAdapter: EventsAdapter,
    private val seriesAdapter: SeriesAdapter,
    private val storyAdapter : StoryNamesAdapter,
    private val ListAdapterList : ListAdapterList

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {

            ListFragment::class.java.name -> ListFragment(listAdapterGrid, listViewModel, ListAdapterList)
            DetailFragment::class.java.name -> DetailFragment(detailViewModel, comicsAdapter, eventsAdapter,
                seriesAdapter, storyAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }

}