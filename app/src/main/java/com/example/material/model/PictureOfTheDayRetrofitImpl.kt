package com.example.material.model


import com.example.material.view.baseUrl
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


val podRetrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .build()


class PictureOfTheDayRetrofitImpl {

    fun getRetrofitImpl(): PictureOfTheDayAPI{

        return podRetrofit.create(PictureOfTheDayAPI::class.java)
    }

}
