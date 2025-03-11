package com.example.project_aksis.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_aksis.R
import com.example.project_aksis.databinding.FragmentDetailBinding
import com.example.project_aksis.databinding.FragmentListBinding
import com.example.project_aksis.model.Result
import com.example.project_aksis.util.Status
import com.example.project_aksis.view.adapter.ComicsAdapter
import com.example.project_aksis.view.adapter.EventsAdapter
import com.example.project_aksis.view.adapter.SeriesAdapter
import com.example.project_aksis.view.adapter.StoryNamesAdapter
import com.example.project_aksis.viewmodel.DetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragment @Inject constructor(
    private val viewModel: DetailViewModel,
    private val comicsAdapter: ComicsAdapter,
    private val eventsAdapter: EventsAdapter,
    private val seriesAdapter: SeriesAdapter,
    private val storyAdapter : StoryNamesAdapter

) : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding : FragmentDetailBinding get ()= _binding!!
    private lateinit var heroId : String
    private var dbFlag : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            heroId = DetailFragmentArgs.fromBundle(it).charId.toString()
            dbFlag = DetailFragmentArgs.fromBundle(it).dbFlag

            if(dbFlag) {
                takeHeroDetailFromDb()

            } else {
                takeHeroDetailFromApi()
            }
        }

        layoutManager()

        binding.detailBackImage.setOnClickListener { findNavController().popBackStack() }
    }

    private fun takeHeroDetailFromApi() {
        viewModel.takeHeroById(heroId.toInt())
        viewModel.heroes.observe(viewLifecycleOwner, Observer {response->
            when(response.status) {
                Status.SUCCESS-> {
                    response.data?.data?.results.let {hero->
                        if (!hero.isNullOrEmpty()) {
                            binding.heroDetails = hero.first()

                            comicsAdapter.result = hero.first().comics.items
                            eventsAdapter.result = hero.first().events.items
                            seriesAdapter.result = hero.first().series.items
                            storyAdapter.result = hero.first().stories.items

                            binding.detailFav.setOnClickListener {
                                viewModel.insertHeroDetail(hero.first())
                            }
                            updateFav(hero.first())
                        }
                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    println(Status.ERROR.name)
                    hideLoading()
                    //noConnection( response.message == NO_NETWORK_CONNECTION )
                    //networkError( response.message == OTHER_ERROR )
                }

                Status.LOADING-> {
                    showLoading()

                }
            }
        })
    }

    private fun takeHeroDetailFromDb() {
        viewModel.getHeroDetailFromDb(heroId.toInt())
        viewModel.heroDetailDb.observe(viewLifecycleOwner, Observer {heroDetailDb->
            when(heroDetailDb.status) {
                Status.SUCCESS-> {
                    heroDetailDb.data?.let {
                        binding.heroDetails = it

                        binding.heroDetails = heroDetailDb.data

                        comicsAdapter.result = heroDetailDb.data.comics.items
                        eventsAdapter.result = heroDetailDb.data.events.items
                        seriesAdapter.result = heroDetailDb.data.series.items
                        storyAdapter.result = heroDetailDb.data.stories.items

                        updateFav(it)
                    }
                    hideLoading()
                }

                Status.ERROR-> {

                }

                Status.LOADING -> {
                    showLoading()
                }
            }
        })
    }


    private fun updateFav(heroDetail : Result) {
        viewModel.checkFav(heroId.toInt()).observe(viewLifecycleOwner, Observer {favStatus->
            binding.apply {
                detailFav.setOnClickListener {
                    if (favStatus) {
                        if(dbFlag) {
                            findNavController().popBackStack()
                        }
                        viewModel.deleteHeroDetail(heroId.toInt())

                    } else {
                        viewModel.insertHeroDetail(heroDetail)
                    }
                }
                if (favStatus) {
                    detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorSecondary))

                } else {
                    detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorOnSecondary))
                }
            }
        })
    }

    private fun layoutManager() {

        val seriesLayoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.HORIZONTAL, false)
        binding.seriesRec.adapter = seriesAdapter
        binding.seriesRec.layoutManager = seriesLayoutManager

        val comicsLayoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.HORIZONTAL, false)
        binding.comicsRec.adapter = comicsAdapter
        binding.comicsRec.layoutManager = comicsLayoutManager

        val eventsLayoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.HORIZONTAL, false)
        binding.eventsRec.adapter = eventsAdapter
        binding.eventsRec.layoutManager = eventsLayoutManager

        val storyLayoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.HORIZONTAL, false)
        binding.storyRec.adapter = storyAdapter
        binding.storyRec.layoutManager = storyLayoutManager
    }



    private fun showLoading() {
        binding.listDetailProgressBar.visibility = View.VISIBLE
        binding.listDetailLay.visibility = View.GONE
    }

    private fun hideLoading() {
        lifecycleScope.launch {
            delay(300)
            binding.listDetailLay.visibility = View.VISIBLE
            binding.listDetailProgressBar.visibility = View.GONE
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}