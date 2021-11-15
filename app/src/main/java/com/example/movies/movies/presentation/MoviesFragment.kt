package com.example.movies.movies.presentation

import com.example.movies.movies.data.Movie
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.base.BaseFragment
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.utils.commons.GridSpacingItemDecoration
import com.example.movies.utils.extensions.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MoviesFragment : BaseFragment<MoviesViewModel>( R.layout.fragment_movies), MovieAdapter.OnItemClick,
        (CombinedLoadStates) -> Unit, SearchView.OnQueryTextListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mGridLayoutManager: Provider<GridLayoutManager>

    @Inject
    lateinit var mGridSpacingItemDecoration: GridSpacingItemDecoration

    @Inject
    lateinit var mMovieAdapter: MovieAdapter

    private val binding by viewBinding(FragmentMoviesBinding::bind)

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override val viewModel by lazy {
        ViewModelProvider(requireActivity(), mViewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
//        inflater.inflate(R.menu.search_menu, menu);
//        val mSearchMenuItem: MenuItem = menu.findItem(R.id.action_search)
//        val searchView: SearchView = mSearchMenuItem.actionView as SearchView
//        searchView.setOnQueryTextListener(this)
//        searchView.queryHint = resources.getString(R.string.search_placeholder)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        mGridLayoutManager.get()?.let {
            it.reverseLayout = false
            it.isItemPrefetchEnabled = false
            binding.moviesRecycler.layoutManager = it
        }
        binding.moviesRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(mGridSpacingItemDecoration)
            itemAnimator = DefaultItemAnimator()
            mMovieAdapter.setListener(this@MoviesFragment)
            adapter = mMovieAdapter.withLoadStateFooter(
                footer = MovieStateAdapter { mMovieAdapter.retry() }
            )
        }
        listenForAdapterStates()
    }

    private fun listenForAdapterStates() {
        viewModel.movies.observe(viewLifecycleOwner,
            { paging -> lifecycleScope.launch { mMovieAdapter.submitData(paging) } })
        binding.btnRetry.setOnClickListener { mMovieAdapter.retry() }
        mMovieAdapter.addLoadStateListener(this)
    }

    override fun onMovieClicked(movieEntity: Movie) {
        viewModel.setSelectedMovie(movieEntity)
        activity?.let {
            findNavController().navigate(R.id.details, Bundle())
        }
    }

    override fun invoke(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading) {
            binding.btnRetry.visibility = View.GONE
            binding.loadingView.visibility = View.VISIBLE
        } else {
            binding.loadingView.visibility = View.GONE
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> {
                    binding.btnRetry.visibility = View.VISIBLE
                    loadState.refresh as LoadState.Error
                }
                else -> null
            }
            errorState?.error?.localizedMessage?.let { showMessage(it) }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { viewModel.getSearchLiveData().postValue(it) }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
}