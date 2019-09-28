package com.example.rupeekassignment

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rupeekassignment.adapter.ImageAdapter
import com.example.rupeekassignment.extensions.*
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import android.R.id.edit
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "StoredData"
    private var PRIVATE_MODE = 0
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = ImageAdapter()
        }

        sharedPref = this@MainActivity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val json = sharedPref.getString("Data", null)

        if(json == null) {
            apiCall()
        } else {
            handleResponse(json)
        }
    }

    //checks if the response is valid or not
    fun didAPIsucceed(response: Response?): Boolean {
        if (response == null) {
            logError(Error("No response"))
            return false
        }

        response.apply {
            if (code() != 200) {
                notifyUser("Internet issue. Connection lost")
                return false
            }

            if (!isSuccessful) {
                notifyUser("Unsuccessful connection! Please try again later.")
                return false
            }
        }

        return true
    }

    private fun apiCall() {
        val request =
            apiRequest(apiURL())

        HttpClient.client.newCall(request).enqueue(object : Callback {

            override fun onResponse(response: Response?) {
                val json = response?.body()?.string()

                with(sharedPref.edit()) {
                    putString("Data", json)
                    commit()
                }

                if (didAPIsucceed(response) && json != null) {
                    handleResponse(json)
                } else {
                    logError(Error("API Failure"))
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                notifyUser("Failed to do api call!! Please try again later. ")
            }
        })
    }

    private fun notifyUser(message: String) {
        runOnUiThread {
            toast(this@MainActivity, message)
        }
    }

    private fun handleResponse(json : String) {
        val data = toData(json)

        if(data != null) {
            val images = getImageList(data)
            runOnUiThread {
                (recyclerview.adapter as ImageAdapter).apply {
                    addImages(images)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
