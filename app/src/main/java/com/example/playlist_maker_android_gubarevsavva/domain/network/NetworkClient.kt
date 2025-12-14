package com.example.playlist_maker_android_gubarevsavva.domain.network

import com.example.playlist_maker_android_gubarevsavva.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
