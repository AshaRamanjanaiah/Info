package com.project.info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.info.R
import com.project.info.model.Details
import com.project.info.viewmodel.InfoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: InfoViewModel
    private val listAdapter = DetailsAdapter(arrayListOf())

    private val countryDetailsDataObserver = Observer<List<Details>> {list ->
        list?.let {
            rowsList.visibility = View.VISIBLE
            listAdapter.updateDetailsList(it)
        }
    }

    private val titleDataObserver = Observer<String> {title ->
        supportActionBar?.setTitle(title)
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
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
        viewModel.getDetails().observe(this, countryDetailsDataObserver)
        viewModel.getAppTitle().observe(this, titleDataObserver)
        viewModel.loadError.observe(this, loadErrorObserver)
        viewModel.loading.observe(this, loadingObserver)

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
