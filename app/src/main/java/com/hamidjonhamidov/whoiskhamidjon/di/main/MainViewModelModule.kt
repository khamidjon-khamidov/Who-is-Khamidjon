package com.hamidjonhamidov.whoiskhamidjon.di.main

import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.whoiskhamidjon.di.ViewModelKey
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel.AboutMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.viewmodel.ContactMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.viewmodel.SkillsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(AboutMeViewModel::class)
    abstract fun bindAboutMeViewModel(aboutMeViewModel: AboutMeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactMeViewModel::class)
    abstract fun bindContactMeViewModel(contactMeViewModel: ContactMeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SkillsViewModel::class)
    abstract fun bindSkillsViewmodel(skillsViewModel: SkillsViewModel): ViewModel
}