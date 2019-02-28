package com.example.ktapp.ui.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.example.ktapp.R
import com.example.ktapp.ui.adapters.ForecastListAdapter
import com.example.ktapp.domain.commands.RequestForecastCommand
import com.example.ktapp.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.*


class MainActivity : CoroutineScopeActivity(), ToolbarManager {

    private val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP)

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecast_list.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecast_list)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = launch {
        var result = RequestForecastCommand(zipCode).execute()
        val adapter = ForecastListAdapter(result) {
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to result.city)
        }
        forecast_list.adapter = adapter
        toolbarTitle = "${result.city}"
    }
}
