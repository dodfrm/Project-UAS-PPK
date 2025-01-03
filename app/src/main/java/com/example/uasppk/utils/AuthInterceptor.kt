package com.example.uasppk.utils

import okhttp3.Interceptor
import okhttp3.Response
class AuthInterceptor(private val accessToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (!accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}