package com.almax.giphy.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code()) {
            400 -> {
                Log.d(ErrorInterceptorTAG, "intercept: ${response.code()} :: ${response.message()}")
            }

            401 -> {
                Log.d(ErrorInterceptorTAG, "intercept: ${response.code()} :: ${response.message()}")
                //TODO: clear app data action
            }

            403 -> {
                Log.d(ErrorInterceptorTAG, "intercept: ${response.code()} :: ${response.message()}")
            }

            404 -> {
                Log.d(ErrorInterceptorTAG, "intercept: ${response.code()} :: ${response.message()}")
            }
        }
        return response
    }
}

const val ErrorInterceptorTAG = "ErrorInterceptor"