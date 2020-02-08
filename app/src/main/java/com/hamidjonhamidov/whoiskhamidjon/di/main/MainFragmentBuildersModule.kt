package com.hamidjonhamidov.whoiskhamidjon.di.main

import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_app.AboutAppFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.AboutMeFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.ContactFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.ContactTypeFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.persojal_projects.PersonalProjectsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.SkillsDetailsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.SkillsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeAboutMeFragment(): AboutMeFragment

    @ContributesAndroidInjector()
    abstract fun contributeAboutAppFragment(): AboutAppFragment

    @ContributesAndroidInjector()
    abstract fun contributeContactFragment(): ContactFragment

    @ContributesAndroidInjector()
    abstract fun contributeContactTypeFragment(): ContactTypeFragment

    @ContributesAndroidInjector()
    abstract fun contributePersonalProjectsFragment(): PersonalProjectsFragment

    @ContributesAndroidInjector()
    abstract fun contributeSkillsDetailsFragment(): SkillsDetailsFragment

    @ContributesAndroidInjector()
    abstract fun contributeSkillsListFragmentFragment(): SkillsListFragment
}