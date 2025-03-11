package com.example.project_aksis.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.project_aksis.R
import com.example.project_aksis.databinding.RowDetailComicsBinding
import com.example.project_aksis.databinding.RowDetailEventsBinding
import com.example.project_aksis.databinding.RowDetailSeriesBinding
import com.example.project_aksis.databinding.RowDetailStoryBinding
import com.example.project_aksis.model.Item
import javax.inject.Inject

class SeriesAdapter @Inject constructor(): RecyclerView.Adapter<SeriesAdapter.SeriesVH>() {
    class SeriesVH(var view : RowDetailSeriesBinding) : ViewHolder(view.root)

    private lateinit var binding : RowDetailSeriesBinding


    private val differ = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, differ)

    var result : List<Item>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesVH {
        val layoutInflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_detail_series, parent, false
        )

        return SeriesVH(binding)
    }

    override fun getItemCount() = result.size


    override fun onBindViewHolder(holder: SeriesVH, position: Int) {
        val series = result[position]

        if (result.first().name.isBlank()) {
            binding.series =  Item("", "empty", "")}
        else {
            binding.series = series
        }
    }
}




class StoryNamesAdapter @Inject constructor(): RecyclerView.Adapter<StoryNamesAdapter.StoryNamesVH>() {

    class StoryNamesVH(itemView : RowDetailStoryBinding) : ViewHolder(itemView.root)

    private lateinit var binding: RowDetailStoryBinding

    private val differ = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, differ)

    var result : List<Item>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryNamesVH {
        val layoutInflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_detail_story, parent,false)
        return StoryNamesVH(binding)
    }

    override fun getItemCount() = result.size


    override fun onBindViewHolder(holder: StoryNamesVH, position: Int) {
        val stories = result[position]

        if (result.first().name.isBlank()) {
            binding.stories =  Item("", "empty", "")}
        else {
            binding.stories = stories
        }
    }
}




class EventsAdapter @Inject constructor(): RecyclerView.Adapter<EventsAdapter.EventsVH>() {

    class EventsVH(var view: RowDetailEventsBinding) : ViewHolder(view.root)

    private lateinit var binding: RowDetailEventsBinding

    private val differ = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, differ)

    var result : List<Item>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_detail_events, parent, false)

        return EventsVH(binding)
    }

    override fun getItemCount() = result.size


    override fun onBindViewHolder(holder: EventsVH, position: Int) {
        val events = result[position]

        if (result.first().name.isBlank()) {
            binding.events =  Item("", "empty", "")}
        else {
            binding.events = events
        }
    }
}



class ComicsAdapter @Inject constructor(): RecyclerView.Adapter<ComicsAdapter.ComicsVH>() {

    class ComicsVH(var view: RowDetailComicsBinding) : ViewHolder(view.root)

    private lateinit var binding: RowDetailComicsBinding

    private val differ = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, differ)

    var result : List<Item>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_detail_comics, parent, false)

        return ComicsVH(binding)
    }

    override fun getItemCount() = result.size


    override fun onBindViewHolder(holder: ComicsVH, position: Int) {
        val comics  = result[position]

        if (result.first().name.isBlank()) {
            binding.comics =  Item("", "empty", "")}
        else{
            binding.comics = comics
        }
    }
}
