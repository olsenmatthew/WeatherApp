package com.example.ktapp.domain.datasource

import com.example.ktapp.domain.model.Forecast
import com.example.ktapp.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?

}