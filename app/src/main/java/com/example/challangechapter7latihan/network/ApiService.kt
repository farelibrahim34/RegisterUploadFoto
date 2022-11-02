package com.example.challangechapter7latihan.network

import com.example.challangechapter7latihan.model.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("auth/register")
    @Multipart
    fun addRegist(
        @Part("full_name") fullName: RequestBody,
        @Part("email") email : RequestBody,
        @Part("password")  password: RequestBody,
        @Part("phone_number") phoneNumber : RequestBody,
        @Part("address") address : RequestBody,
        @Part fileImage : MultipartBody.Part,
        @Part("city") city : RequestBody

    ): Call<ResponseRegister>



}