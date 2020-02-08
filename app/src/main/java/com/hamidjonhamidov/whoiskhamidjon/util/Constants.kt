package com.hamidjonhamidov.whoiskhamidjon.util

import android.util.Log
import com.hamidjonhamidov.whoiskhamidjon.R
import org.json.JSONException
import org.json.JSONObject

object Constants {

    // these are menu item ids for sliding navigation
    val menuItems = listOf(
        R.id.menu_item_about_me,
        R.id.menu_item_skills,
        R.id.menu_item_personal_projects,
        R.id.menu_item_source_code,
        R.id.menu_item_about_app,
        R.id.menu_item_personal_posts,
        R.id.menu_item_contact,
        R.id.menu_item_exit
    )

    // these are for AboutMe constants
    val ABOUT_ME_VIEW_STATE_BUNDLE_KEY = "ABOUT_ME_VIEW_STATE_BUNDLE_KEY"


    // network response to requests
    const val UNABLE_TO_CONNECT_TO_INTERNET = "Sorry, NETWORK UNAVAILABLE!"
    const val SLOW_NETWORK = "Sorry, Network is so slow!"
    const val ERROR_UNKNOWN = "Sorry, Unknown error!"
    const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
    const val PAGINATION_DONE_ERROR = "Invalid page."


    // network time constants
    const val TESTING_CACHE_DELAY = 0L
    const val TESTING_NETWORK_DELAY = 0L
    const val NETWORK_TIMEOUT = 6000L


    fun isNetworkError(msg: String) =
        when {
            msg.contains(SLOW_NETWORK) -> true
            else -> false
        }

    private val TAG = "AppDebug"
    fun parseDetailJsonResponse(rawJson: String?): String{
        try{
            if(!rawJson.isNullOrBlank()){
                if(rawJson.equals(ERROR_CHECK_NETWORK_CONNECTION)){
                    return PAGINATION_DONE_ERROR
                }
                return JSONObject(rawJson).get("detail") as String
            }
        } catch (e: JSONException){
            Log.d(TAG, "Constants: parseDetailJsonResponse: ${e.message}")
        }
        return ""
    }

    fun isPaginationDone(errorResponse: String?): Boolean{
        // if error response = '{"detail":"Invalid page."}' then pagination is done
        return PAGINATION_DONE_ERROR.equals(parseDetailJsonResponse(errorResponse))
    }
}





















