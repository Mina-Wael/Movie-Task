package com.example.movietask.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movietask.databinding.HomeRecyclerRowBinding
import com.example.movietask.domain.pojo.ResultPojo
import com.example.movietask.utils.Common
import com.example.movietask.utils.Constants

class MainRecyclerAdapter(
   private val diffutil: MovieDiffUtil = MovieDiffUtil,
    private val onItemClick: (ResultPojo) -> Unit
) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

   private var differ = AsyncListDiffer(this, diffutil)

    inner class MainViewHolder(var binding: HomeRecyclerRowBinding, var context: Context) :
        ViewHolder(binding.root) {

        fun bindData(movie: ResultPojo) {
            binding.rvText.text = movie.title
            Glide.with(context).load(Constants.IMAGE_BASE_URL + movie.poster_path)
                .placeholder(Common.getProgressLoading(context))
                .into(binding.rvImage)
        }

        fun onClick(movie: ResultPojo) {
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        var binding: HomeRecyclerRowBinding =
            HomeRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
        holder.onClick(differ.currentList[position])
    }

    fun setList(movieList: List<ResultPojo>) {
        differ.submitList(movieList)
    }


}