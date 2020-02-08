package com.hamidjonhamidov.whoiskhamidjon

import org.junit.Test

import org.junit.Assert.*
import java.lang.StringBuilder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun someTest() {
        val list = ArrayList<String>()
        list.add("hello")
        list.add("hisfd")
        list.add("lj;k")
        list.add("vcnv")
        list.add("sad;lf")
        list.add(";lkjdf")

//        println(list.toString())
        val list2 = convertToArrayList("[]")

        println(list2)
//        for (l in list2) {
//            println(l)
//        }
    }


    fun convertToArrayList(someStr: String): ArrayList<String> {
//        if (someStr.length == 2) return ArrayList()

        var str = someStr
        str = str.removeRange(0, 1)
        str = str.removeRange(str.length - 1, str.length)



        return str.trim().splitToSequence(',', ' ')
            .filter { it.isNotEmpty() }
            .toCollection(ArrayList())
    }

    @Test
    fun test2() {
        var str = "dfdflkj"
        str = str.removeRange(0, 1)
        str = str.removeRange(str.length - 1, str.length)


        println(str)
    }
}























