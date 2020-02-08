package com.hamidjonhamidov.whoiskhamidjon.di.main

import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.repository.AboutMeRepository
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AboutMeDao
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

//    @MainScope
//    @Provides
//    fun provideMainMenuItemsClicked

    @MainScope
    @Provides
    fun provideAboutMeDao(db: AppDatabase) = db.getAboutMeDao()

    @MainScope
    @Provides
    fun provideRequestFromFirebase(db: FirebaseFirestore) = RequestFromFirebase(db)

    @MainScope
    @Provides
    fun provideAboutMeRepository(
        requestFromFirebase: RequestFromFirebase,
        aboutMeDao: AboutMeDao,
        sessionManager: SessionManager
    ) =
        AboutMeRepository(requestFromFirebase, aboutMeDao, sessionManager)

}