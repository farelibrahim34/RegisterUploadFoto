package com.example.challangechapter7latihan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challangechapter7latihan.model.ResponseRegister
import com.example.challangechapter7latihan.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelRegister : ViewModel() {
    lateinit var addRegister : MutableLiveData<ResponseRegister>

    init {
        addRegister = MutableLiveData()
    }
    fun postLiveDataRegister(): MutableLiveData<ResponseRegister>{
        return addRegister
    }
    fun postApiRegister(fullName : RequestBody,email :RequestBody,password : RequestBody,phoneNumber : RequestBody,address : RequestBody,image : MultipartBody.Part,city : RequestBody){
        RetrofitClient.instance.addRegist(fullName,email,password,phoneNumber,address,image,city)
            .enqueue(object : Callback<ResponseRegister>{
                override fun onResponse(
                    call: Call<ResponseRegister>,
                    response: Response<ResponseRegister>
                ) {
                    if (response.isSuccessful){
                        addRegister.postValue(response.body())
                    }else{
                        addRegister.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    addRegister.postValue(null)
                }

            })
    }

}