package com.example.project_aksis.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_aksis.databinding.FragmentListBinding
import com.example.project_aksis.model.Result
import com.example.project_aksis.util.Constants.EMPTY_DB
import com.example.project_aksis.util.Constants.ORDER_DEF
import com.example.project_aksis.util.Constants.ORDER_DESC
import com.example.project_aksis.util.EndlessScroll
import com.example.project_aksis.util.Status
import com.example.project_aksis.view.adapter.ListAdapterGrid
import com.example.project_aksis.view.adapter.ListAdapterList
import com.example.project_aksis.viewmodel.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment @Inject constructor(
    private val listAdapterGrid: ListAdapterGrid,
    private val listViewModel: ListViewModel,
    private val listAdapterList: ListAdapterList

) : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding : FragmentListBinding get ()= _binding!!
    private var dbFlag : Boolean = false
    private var orderFlag : Boolean = false
    private var isGridView = true
    private var isDef : Boolean = true
    private var isDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeLayout()
        favClick()
        sortChange()
        orderFlag = sortChange()


        arguments?.let {
            dbFlag = ListFragmentArgs.fromBundle(it).dbFlag
            orderFlag = ListFragmentArgs.fromBundle(it).order

            if(dbFlag) {
                clearDb()
                takeHeroesFromDb()

            } else {
                if (orderFlag) {
                    clearApi()
                    takeHeroesFromApi()
                    getRecyclerPosition(orderFlag)
                } else {
                    clearApi()
                    takeHeroesFromApi()
                    getRecyclerPosition(orderFlag)

                }

            }
        }
    }

    private fun takeHeroesFromApi() {
        listAdapterGrid.dbFalg = false
        apiLayoutUI()

        listViewModel.heroes.observe(viewLifecycleOwner, Observer {response->
            when(response.status) {
                Status.SUCCESS-> {
                    response.data?.let {
                        val list = listAdapterGrid.result.toMutableList()
                        list.addAll(
                            it.data?.results ?: listOf() )
                        listAdapterGrid.result = list
                        listAdapterList.result = list

                        checkDbFav(list)
                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    hideLoading()

                }

                Status.LOADING-> {
                    showLoading()

                }
            }
        })
    }

    private fun takeHeroesFromDb() {
        listViewModel.takeHeroesFromDb()
        listAdapterGrid.dbFalg = true
        dbLayoutUI()
        listViewModel.heroesDb.observe(viewLifecycleOwner, Observer {heroDetailDb->
            when(heroDetailDb.status) {
                Status.SUCCESS-> {
                    heroDetailDb.data?.let {
                        listAdapterGrid.result = it
                        listAdapterList.result = it

                    }
                    hideLoading()
                }

                Status.ERROR-> {
                    hideLoading()
                   // clearDb()
                    if(heroDetailDb.message == EMPTY_DB) {
                        showErrorMassage(EMPTY_DB)
                        clearApi()
                        clearDb()
                        findNavController().popBackStack()
                    }


                }

                Status.LOADING -> {
                    showLoading()
                }
            }
        })
    }

    private fun checkDbFav(heroes: List<Result>) {
        heroes.forEach { hero ->
            listViewModel.checkFav(hero.id).observe(viewLifecycleOwner) { isFav ->
                listAdapterGrid.updateFavStatus(hero.id, isFav)
                listAdapterList.updateFavStatus(hero.id, isFav)
            }
        }
    }




    private fun favClick() {
        binding.homeFav.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentSelf(true)
            findNavController().navigate(action)
        }
    }


    private fun getRecyclerPosition(order : Boolean) {
        if (binding.gridRecyclerView.visibility == View.VISIBLE) {
            gridRecyclerPosition(order)

        } else { listRecyclerPosition(order) }


        if(!isDataLoaded) {
            isDataLoaded = true
            listViewModel.takeHeroes(0, ORDER_DEF)}
    }


    private fun gridRecyclerPosition(order : Boolean) {
        binding.gridRecyclerView.addOnScrollListener(object : EndlessScroll() {
            override fun onLoadMore(page: Int) {
                showLoading()
                if (order) {
                    listViewModel.takeHeroes(page, ORDER_DESC)
                } else {
                    listViewModel.takeHeroes(page, ORDER_DEF)
                }
            }
        })
    }

    private fun listRecyclerPosition(order : Boolean) {
        binding.listRecyclerView.addOnScrollListener(object : EndlessScroll() {
            override fun onLoadMore(page: Int) {
                showLoading()
                if (order) {
                    listViewModel.takeHeroes(page, ORDER_DESC)
                } else {
                    listViewModel.takeHeroes(page, ORDER_DEF)
                }
            }
        })
    }





    private fun sortChange() : Boolean {
        binding.azButton.setOnClickListener {
            if(isDef) {
                clearApi()
                clearDb()
                listViewModel.takeHeroes(0, ORDER_DESC)

            } else{
                clearApi()
                clearDb()
                listViewModel.takeHeroes(0, ORDER_DEF)
            }
        }
        isDef = !isDef
        return  !isDef
    }

    private fun changeLayout() {
        val listLayoutManager = LinearLayoutManager(requireContext())
        binding.listRecyclerView.adapter = listAdapterList
        binding.listRecyclerView.layoutManager = listLayoutManager

        val gridLayoutManager = GridLayoutManager(requireContext(), 2,
            GridLayoutManager.VERTICAL, false)
        binding.gridRecyclerView.adapter = listAdapterGrid
        binding.gridRecyclerView.layoutManager = gridLayoutManager

        binding.changeRowButton.setOnClickListener {
            if(isGridView) {
                binding.listRecyclerView.visibility = View.VISIBLE
                binding.gridRecyclerView.visibility = View.GONE
                binding.gridRecyclerView.isNestedScrollingEnabled = false

            } else {
                binding.gridRecyclerView.visibility = View.VISIBLE
                binding.listRecyclerView.visibility = View.GONE
                binding.gridRecyclerView.isNestedScrollingEnabled = false
            }
            isGridView = !isGridView

        }
    }


    private fun dbLayoutUI() {
        binding.listBackButton.visibility = View.VISIBLE
        binding.homeSubtitle.visibility = View.GONE
        binding.homeTitle.visibility = View.GONE
        binding.homeFav.visibility = View.GONE
        binding.azButton.visibility = View.GONE


        binding.listBackButton.setOnClickListener {
            clearDb()
            clearApi()
            listViewModel.takeHeroes(0, ORDER_DEF)
            findNavController().popBackStack()
        }
    }


    private fun apiLayoutUI() {
        binding.listBackButton.visibility = View.GONE
        binding.homeSubtitle.visibility = View.VISIBLE
        binding.homeTitle.visibility = View.VISIBLE
        binding.homeFav.visibility = View.VISIBLE
    }


    private fun showLoading() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        lifecycleScope.launch {
            delay(300)
            binding.progressCircular.visibility = View.GONE
            binding.recyclerContainer.visibility = View.VISIBLE

        }
    }

    private fun showErrorMassage(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun clearDb() {
        listViewModel.clearDbHeroes()
        binding.recyclerContainer.visibility = View.GONE
    }

    private fun clearApi(){
        listViewModel.clearApiHeroes()
        binding.recyclerContainer.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}