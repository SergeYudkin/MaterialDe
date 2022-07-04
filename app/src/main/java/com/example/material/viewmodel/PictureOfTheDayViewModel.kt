package com.example.material.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.material.BuildConfig
import com.example.material.R
import com.example.material.model.PictureOfTheDayRetrofitImpl
import com.example.material.model.PictureOfTheDayServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveDataForViewToObserve() = liveDataForViewToObserve

    fun sendServerRequest() {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(418)
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(418)
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey,date).enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayServerResponseData>{
        override fun onResponse(
            call: Call<PictureOfTheDayServerResponseData>,
            response: Response<PictureOfTheDayServerResponseData>
        ) {
            if(response.isSuccessful){
                response.body()?.let {
                    liveDataForViewToObserve.postValue(AppState.Success(it))
                }
            }else{
                liveDataForViewToObserve.postValue(AppState.Error(R.string.error_code))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.postValue(AppState.Error(R.string.error_code))
        }

    }
}