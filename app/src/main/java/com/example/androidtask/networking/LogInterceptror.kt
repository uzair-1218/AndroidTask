package com.example.androidtask.networking

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.nio.charset.StandardCharsets

class LogInterceptror : Interceptor {
    @SuppressLint("TimberArgCount", "LongLogTag")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        Log.d("Requesting URL to the Server ====>>>", request.url.toString())
        val response: Response = chain.proceed(request)
        val responseBody = response.body
        val source = responseBody!!.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer()
        Log.d("Complete Response form the Server ====>>>", buffer.clone().readString(UTF8))
        return response
    }

    companion object {
        private val UTF8 = StandardCharsets.UTF_8
    }
}