package com.hamidjonhamidov.whoiskhamidjon.models.about_me

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class AboutMeModelTest {

    val templateAboutMe = AboutMeModel(
        0,
        "email",
        "addss",
        "3242342",
        "dskflsf",
        "dlfsjd",
        "ds"
    )

    val template1 = AboutMeModel(
        1,
        "email#1",
        "addss#1",
        "3242342#1",
        "dskflsf#1",
        "dlfsjd#1",
        "ds#1"
    )

    val template2 = AboutMeModel(
        2,
        "email#2",
        "addss#2",
        "3242342#2",
        "dskflsf#2",
        "dlfsjd#2",
        "ds#2"
    )

    /*
        Compare two equal AboutMeModels
     */
    @Test
    internal fun isAboutMeEqual_identicalProperties_returnTrue()  {

        // Arrange
        val model1 = AboutMeModel(templateAboutMe)
        val model2 = AboutMeModel(templateAboutMe)

        // Act

        // Assert
        assertEquals(model1, model2)
        println("models are equal")
    }

    /*
        Compare two AboutMeModels with 2 different pks
     */
    @Test
    internal fun isAboutMeModelsEqual_differentPks_returnFalse() {
        // Arrange
        val model1 = AboutMeModel(templateAboutMe)
        val model2 = AboutMeModel(templateAboutMe)
        model2.pk = 4

        // Act

        // Assert
        assertNotEquals(model1, model2)
        println("The models are not equal")
    }

    /*
        Compare two AboutMeModels with 2 different phone number
     */
    @Test
    internal fun isAboutMeModelsEqual_differentPhoneNumber_returnFalse() {

        // Arrange
        val model1 = AboutMeModel(templateAboutMe)
        val model2 = AboutMeModel(templateAboutMe)
        model2.phone_number = "23434234"

        // Act

        // Assert
        assertNotEquals(model1, model2)
        println("The models are not equal")

    }
}


















