package com.example.rupeekassignment.extensions

import com.example.rupeekassignment.model.Data
import com.example.rupeekassignment.model.Datum
import com.google.gson.GsonBuilder
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request

object HttpClient {
    val client : OkHttpClient
        get() = OkHttpClient()
}

fun apiURL() : HttpUrl {

    return HttpUrl.parse("http://www.mocky.io/v2/5c2443f530000054007a5f3e")
        .newBuilder()
        .build()
}

fun apiRequest(url : HttpUrl) : Request {
    return Request.Builder()
        .url(url)
        .build()
}

fun toData(json : String) : Data? {
    val gson = GsonBuilder().create()
    val data = gson.fromJson(json, Data::class.java)

    if(data == null)
        logError(Error("No response received"))

    return data
}

fun getImageList(data : Data) : ArrayList<Datum> {

    val list = ArrayList<Datum>()
    data.data?.forEach {
        val place = it.place
        val url = it.url
        list.add(Datum(place, url))
    }
    return list

}
