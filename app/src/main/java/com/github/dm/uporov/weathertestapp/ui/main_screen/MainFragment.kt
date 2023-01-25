package com.github.dm.uporov.weathertestapp.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.main_screen.items.ForecastItemsAdapter
import com.github.dm.uporov.weathertestapp.ui.main_screen.model.MainUiState
import com.github.dm.uporov.weathertestapp.utils.LeftBorderSnapHelper
import com.github.dm.uporov.weathertestapp.utils.SnapPositionScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var adapter: ForecastItemsAdapter
    @Inject
    lateinit var snapHelper: LeftBorderSnapHelper
    @Inject
    lateinit var snapPositionScrollListener: SnapPositionScrollListener

    private lateinit var viewModel: MainViewModel

    private lateinit var mainView: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainView = MainViewImpl(
            view = view,
            snapPositionScrollListener = snapPositionScrollListener,
            adapter = adapter,
            snapHelper = snapHelper,
            onRetryClickListener = viewModel::onRetryClicked
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::renderNewState)
            }
        }
    }

    private fun renderNewState(state: MainUiState) {
        when {
            state.errorMessage != null -> mainView.showError(state.errorMessage)
            state.isLoading -> mainView.showLoading()
            else -> mainView.showContent(state.forecastShortItems, state.detailedItem)
        }
    }

    override fun onStart() {
        super.onStart()
        snapPositionScrollListener.onSnapPositionChangedListener = {
            viewModel.onForecastItemClicked(it)
        }
    }

    override fun onStop() {
        super.onStop()
        snapPositionScrollListener.onSnapPositionChangedListener = null
    }
}