package com.hamidjonhamidov.whoiskhamidjon.util

import retrofit2.Response

@Suppress("unused") // T is used in extending classes
sealed class GenericNetworkResponse<T> {
    companion object{
        private val TAG = "AppDebug"

        fun <T> create(error: Throwable):
                NetworkErrorResponse<T> =
            NetworkErrorResponse(errorMessage = error.message ?: "Unknown error")


        fun <T> create(response: Response<T>, strAdMessage: String? = null):
                GenericNetworkResponse<T>{
            if(response.isSuccessful){
                val body = response.body()
                return if(body==null || response.code() == 204) {
                    NetworkEmptyResponse()
                }
                else if(response.code() == 401){
                    NetworkErrorResponse("401 unathorized. You are not Khamidjon")
                } else {
                    NetworkSuccessResponse(body = body, successResponseMessage = strAdMessage)
                }
            } else {
                val msg = response.errorBody()?.toString()
                val errorMsg = if(msg.isNullOrEmpty()){
                    response.message()
                } else msg

                return NetworkErrorResponse(
                    errorMessage = errorMsg ?: "Unknown error!"
                )
            }
        }

        fun <T> create(data: T? = null, strResponse: String? = null):
        GenericNetworkResponse<T>{
            if(data!=null){
                return NetworkSuccessResponse(body = data, successResponseMessage = strResponse)
            }
            else if (strResponse!=null){
                return NetworkErrorResponse<T>(
                    errorMessage = strResponse
                )
            } else {
                return NetworkEmptyResponse()
            }
        }
    }
}

class NetworkEmptyResponse<T> : GenericNetworkResponse<T> ()

data class NetworkSuccessResponse<T>(val body: T, val successResponseMessage: String? = null): GenericNetworkResponse<T>()

data class NetworkErrorResponse<T> (val errorMessage: String): GenericNetworkResponse<T>()