package com.hamidjonhamidov.whoiskhamidjon.util

import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import java.util.*
import kotlin.collections.ArrayList

object TestUtil {

    val ABOUTME_TEMPLATE_1 = AboutMeModel(
        1,
        "email#1",
        "addss#1",
        "3242342#1",
        "dskflsf#1",
        "dlfsjd#1",
        "ds#1"
    )

    val ABOUTME_TEMPLATE_2 = AboutMeModel(
        2,
        "email#2",
        "addss#2",
        "3242342#2",
        "dskflsf#2",
        "dlfsjd#2",
        "ds#2"
    )

    val aboutMeList = ArrayList<AboutMeModel>(listOf(
        AboutMeModel(ABOUTME_TEMPLATE_1),
        AboutMeModel(ABOUTME_TEMPLATE_2)
    ))

    val SKILL_TEMPLATE_1 = SkillModel(
        1, "name#1", "image#1", "des#1", "0"
    )

    val SKILL_TEMPLATE_2 = SkillModel(
        2, "name#2", "image#2", "des#2", "1"
    )

    val skillList = ArrayList<SkillModel>(listOf(
        SkillModel(SKILL_TEMPLATE_1),
        SkillModel(SKILL_TEMPLATE_2)
    ))

    val BLOGPOST_TEMPLATE_1 = BlogPostModel(1, 0, "title#1", "body#1")
    val BLOGPOST_TEMPLATE_2 = BlogPostModel(2, 1, "title#2", "body#2")

    val blogList = ArrayList<BlogPostModel>(listOf(
        BlogPostModel()
    ))
}




















