package com.project.info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.info.R
import com.project.info.viewmodel.InfoViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = MainFragment()
        fragmentTransaction.add(R.id.list, fragment)
        fragmentTransaction.commit()

        val viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)

        viewModel.getAppTitle().observe(this, Observer {
            it?.let {
                supportActionBar?.setTitle(it)
            }
        })

    }

}
