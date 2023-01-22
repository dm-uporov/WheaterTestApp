package com.github.dm.uporov.weathertestapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.github.dm.uporov.weathertestapp.R
import com.github.dm.uporov.weathertestapp.ui.items.ForecastItem
import com.github.dm.uporov.weathertestapp.ui.items.ForecastItemsAdapter
import com.github.dm.uporov.weathertestapp.ui.items.OnForecastItemClickListener
import com.github.dm.uporov.weathertestapp.utils.LeftBorderSnapHelper
import com.github.dm.uporov.weathertestapp.utils.SnapPositionScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), OnForecastItemClickListener {

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

    // TODO
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

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

        textView =  view.findViewById(R.id.message)

        recyclerView = view.findViewById(R.id.forecast_recycler)
        recyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(snapPositionScrollListener)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.updateItems(it.forecastItems)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        snapPositionScrollListener.onSnapPositionChanged = {
            textView.text = "$it"
        }
    }

    override fun onStop() {
        super.onStop()
        snapPositionScrollListener.onSnapPositionChanged = null
    }

    override fun onForecastItemClicked(item: ForecastItem, position: Int, clickedView: View) {
        snapHelper.snapToView(clickedView, recyclerView)
    }
}