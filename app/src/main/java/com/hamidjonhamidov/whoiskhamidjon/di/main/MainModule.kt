package com.hamidjonhamidov.whoiskhamidjon.di.main

import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.repository.MainRepository
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.MainDao
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

    @MainScope
    @Provides
    fun provideAboutMeDao(db: AppDatabase) = db.getMainMeDao()

    @MainScope
    @Provides
    fun provideRequestFromFirebase(db: FirebaseFirestore) = RequestFromFirebase(db)

    @MainScope
    @Provides
    fun provideAboutMeRepository(
        requestFromFirebase: RequestFromFirebase,
        mainDao: MainDao,
        sessionManager: SessionManager
    ) =
        MainRepository(requestFromFirebase, mainDao)

}