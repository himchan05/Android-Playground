package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofit.Model.User
import com.example.retrofit.Service.JsonPlaceholderApi
import com.example.retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit builder

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        // object to call methods
        val jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)

        val myCall: Call<List<User>> = jsonPlaceholderApi.getUsers()

        myCall.enqueue(object: Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val Users:List<User> = response.body()!!
                val stringBuilder = StringBuilder()
                for(user in Users) {
                    stringBuilder.append(user.name)
                    stringBuilder.append("\n")
                    stringBuilder.append(user.id)
                    stringBuilder.append("\n")
                    stringBuilder.append(user.email)
                    stringBuilder.append("\n")
                    stringBuilder.append(user.username)
                    stringBuilder.append("\n")
                    stringBuilder.append("\n")
                }
                binding.txtUser.text = stringBuilder
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Err", t.message.toString())
            }
        })
    }
}