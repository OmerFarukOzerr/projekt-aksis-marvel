package com.example.project_aksis.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.project_aksis.R
import com.example.project_aksis.databinding.RowList2Binding
import com.example.project_aksis.model.Result
import com.example.project_aksis.view.ListFragmentDirections
import javax.inject.Inject

class ListAdapterList @Inject constructor() : RecyclerView.Adapter<ListAdapterList.ListListVH>() {
    class ListListVH(var view : RowList2Binding) : ViewHolder(view.root)

    private lateinit var binding: RowList2Binding
    var dbFalg : Boolean = false

    private var favMap: MutableMap<Int, Boolean> = mutableMapOf()

    private val differ = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, differ)

    var result : List<Result>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_list_2, parent, false )

        return ListListVH(binding)
    }

    override fun getItemCount() = result.size

    fun updateFavStatus(heroId: Int, isFav: Boolean) {
        favMap[heroId] = isFav

    }


    override fun onBindViewHolder(holder: ListListVH, position: Int) {
        val heroes = result[position]
        holder.view.heroes = heroes

        val isFav = favMap[heroes.id] ?: false
        holder.view.icListFav.visibility = if (isFav) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(heroes.id, dbFalg)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }
}