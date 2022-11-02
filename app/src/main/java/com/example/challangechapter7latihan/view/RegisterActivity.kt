package com.example.challangechapter7latihan.view

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.challangechapter7latihan.R
import com.example.challangechapter7latihan.databinding.ActivityRegisterBinding
import com.example.challangechapter7latihan.viewmodel.ViewModelRegister
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity() {
    private var imageMultiPart: MultipartBody.Part? = null
    private var imageUri: Uri? = Uri.EMPTY
    private var imageFile: File? = null
    lateinit var viewModelRegister : ViewModelRegister
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelRegister = ViewModelProvider(this).get(ViewModelRegister::class.java)

        binding.addImage.setOnClickListener {
            openGallery()
        }
        binding.btnDaftar.setOnClickListener {
            register()
        }


    }
    fun register(){
        val fullName = binding.registFname.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val email = binding.registEmail.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val password = binding.inputRegistPw.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val phoneNumber = binding.registNohp.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val address = binding.registAlamat.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val city = binding.registCity.text.toString().toRequestBody("multipart/form-data".toMediaType())

        viewModelRegister.addRegister.observe(this,{
            if (it != null){
                Toast.makeText(this, "Register Succeeded", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelRegister.postApiRegister(fullName,email,password,phoneNumber,address,imageMultiPart!!,city)
    }


    fun openGallery(){
        getContent.launch("image/*")
    }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = this!!.contentResolver
                val type = contentResolver.getType(it)
                imageUri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.addImage.setImageURI(it)
                Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }
}