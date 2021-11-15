package com.example.movies.detail.presentation

import com.example.movies.detail.data.TrailerRemote
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ItemTrailerViewBinding

class TrailerAdapter(var mTrailerRemoteList: MutableList<TrailerRemote>) :
    RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private lateinit var mListener: TrailerAdapterListener

    override fun getItemCount(): Int {
        return if (mTrailerRemoteList.size > 0) mTrailerRemoteList.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding =
            ItemTrailerViewBinding.inflate(layoutInflater, parent, false)
        return TrailerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = mTrailerRemoteList[position]
        holder.bind(trailer)
        holder.getBtn().setOnClickListener { mListener.onTrailerClicked(trailer) }
    }

    fun addItems(mList: List<TrailerRemote>) {
        mTrailerRemoteList.addAll(mList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mTrailerRemoteList.clear()
    }

    fun setListener(listener: TrailerAdapterListener) {
        mListener = listener
    }

    class TrailerViewHolder(
        private val itemBinding: ItemTrailerViewBinding,
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(trailer: TrailerRemote) {
            itemBinding.trailerTv.text = trailer.name
        }

        fun getBtn() = itemBinding.playBtn
    }

    interface TrailerAdapterListener {
        fun onTrailerClicked(trailerRemote: TrailerRemote)
    }

}
