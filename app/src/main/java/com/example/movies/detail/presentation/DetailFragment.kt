package com.example.movies.detail.presentation

import com.example.movies.detail.data.TrailerRemote
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.base.BaseFragment
import com.example.movies.databinding.FragmentDetailMovieBinding
import com.example.movies.movies.presentation.MoviesViewModel
import com.example.movies.movies.presentation.MoviesViewModel.Companion.POSTER_BASE_URL
import com.example.movies.movies.presentation.MoviesViewModel.Companion.YOUTUBE_APP_URI
import com.example.movies.movies.presentation.MoviesViewModel.Companion.YOUTUBE_WEB_URI
import com.example.movies.utils.extensions.viewBinding
import javax.inject.Inject

class DetailFragment : BaseFragment<MoviesViewModel>(R.layout.fragment_detail_movie), TrailerAdapter.TrailerAdapterListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mTrailerAdapter: TrailerAdapter

    private val binding by viewBinding(FragmentDetailMovieBinding::bind)

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override val viewModel by lazy {
        ViewModelProvider(requireActivity(), mViewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrailerAdapter.setListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()

        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("RestrictedApi")
    fun initUI() {
        with(binding.recyclerTrailer){
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = mTrailerAdapter
        }
        renderMovieDetails()
    }

    private fun renderMovieDetails() {
        viewModel.getSelectedMovie()?.apply {
            binding.tvTitle.text = title
            binding.tvPlot.text = overview
            binding.tvRating.text = vote_average.toString()
            binding.tvReleaseDate.text = String.format(getString(R.string.released_in), release_date)
            binding.tvVotesCount.text =
                String.format(getString(R.string.votes_count), vote_count.toString())
            binding.ratingBar.rating = (vote_average / 2).toFloat()
            Glide.with(requireActivity())
                .load("$POSTER_BASE_URL${poster_path}")
                .into(binding.imgPoster)
            binding.imgLike.setOnClickListener { viewModel.updateLikeStatus(this) }
            viewModel.getLikeState(id)
            viewModel.fetchMovieTrailers(id)
        }
    }

    override fun renderViewState(data: Any) {
        when (data) {
            is DetailViewState.MessageRes -> showMessage(getString(data.resId))
            is DetailViewState.LikeState -> renderLikeState(data.isLiked)
            is DetailViewState.TrailersFetchedSuccess -> renderTrailers(data.trailers)
            is DetailViewState.TrailersFetchedError -> renderFetchingTrailerError()
        }
    }

    private fun renderFetchingTrailerError() {
        binding.trailersLoading.visibility = View.GONE
        showMessage(getString(R.string.fetch_trailers_error))
    }

    private fun renderTrailers(trailers: List<TrailerRemote>) {
        binding.trailersLoading.visibility = View.GONE
        mTrailerAdapter.addItems(trailers)
    }

    private fun renderLikeState(isLiked: Boolean) {
        if (isLiked) R.string.movie_liked else R.string.movie_disliked
        binding.imgLike.setImageResource(if (isLiked) R.drawable.like else R.drawable.dislike)
    }

    override fun onTrailerClicked(trailerRemote: TrailerRemote) {
        try {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_APP_URI${trailerRemote.key}"))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_WEB_URI${trailerRemote.key}"))
            startActivity(intent)
        }
    }
}