package com.project.info.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.project.info.R
import com.project.info.model.Details
import com.project.info.viewmodel.InfoViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: InfoViewModel
    private val listAdapter = DetailsAdapter(arrayListOf())

    private val countryDetailsDataObserver = Observer<List<Details>> { list ->
        list?.let {
            rowsList.visibility = View.VISIBLE
            listAdapter.updateDetailsList(it)
        }
    }

    private val loadErrorObserver = Observer<Boolean> {isError ->
        loadError.visibility = if(isError) View.VISIBLE else View.GONE
    }

    private val loadingObserver = Observer<Boolean> {isLoading ->
        loading.visibility = if(isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            loadError.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(InfoViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel.getDetails().observe(it, countryDetailsDataObserver)
            viewModel.loadError.observe(it, loadErrorObserver)
            viewModel.loading.observe(it, loadingObserver)
        }

        rowsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            loadError.visibility = View.GONE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

}
