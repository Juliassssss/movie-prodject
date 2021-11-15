package com.example.movies.movies.presentation

import com.example.movies.movies.data.Movie
import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ItemCardMovieViewBinding

class MovieAdapter : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieModelComparator) {

    private lateinit var listener: OnItemClick

    fun setListener(mOnItemClick: OnItemClick) {
        listener = mOnItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding =
            ItemCardMovieViewBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MovieViewHolder
        val movie = getItem(position)
        movie?.poster_path?.let {
            Glide.with(viewHolder.itemView.context)
                .load(Uri.parse("http://image.tmdb.org/t/p/w185$it"))
                .into(viewHolder.ivPoster)
            viewHolder.ivPoster.setOnClickListener { listener.onMovieClicked(movie) }
        }
        movie?.let { viewHolder.bind(it) }
    }

    class MovieViewHolder(private val itemBinding: ItemCardMovieViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var ivPoster: ImageView = itemBinding.imgView as ImageView
        fun bind(movie: Movie) {
            with(itemBinding) {
                nameMovie.text = movie.title
                opMovie.text = movie.original_language
            }
        }
    }

    interface OnItemClick {
        fun onMovieClicked(movieEntity: Movie)
    }

    companion object {
        private val MovieModelComparator = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}