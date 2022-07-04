package com.example.material.viewmodel


import com.example.material.model.PictureOfTheDayServerResponseData

sealed class AppState{

    data class Success(val serverResponseData: PictureOfTheDayServerResponseData) : AppState()
    data class Error(val error: Int) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
