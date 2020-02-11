package com.hamidjonhamidov.whoiskhamidjon.requests.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import javax.inject.Inject

class RequestFromFirebase
@Inject
constructor(
    private val dbFirebase: FirebaseFirestore
) {

    private val TAG = "AppDebug"

    fun requestAboutMeInfo():
            LiveData<GenericNetworkResponse<AboutMeModel>> {
        val result = MutableLiveData<GenericNetworkResponse<AboutMeModel>>()


        dbFirebase.collection(FirebaseConstants.COLLECTION_ABOUT_ME)
            .get()
            .addOnSuccessListener {
                for (doc in it.documents) {
                    val aboutMe = doc.toObject(AboutMeModel::class.java)

                    result.postValue(
                        GenericNetworkResponse
                            .create(data = aboutMe, strResponse = "Successfully Refreshed from Firebase!")
                    )
                }
            }
            .addOnCanceledListener {
                result.postValue(
                    GenericNetworkResponse
                        .create(data = null, strResponse = "Couldn't Refresh from Firebase")
                )
            }

        return result
    }

}