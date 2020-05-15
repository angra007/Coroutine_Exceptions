package com.ankitangra.www.coroutineexceptionhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Exception
import java.util.concurrent.CancellationException


/*

 */

class MainActivity : AppCompatActivity() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main()
    }

    val handler = CoroutineExceptionHandler{  _, exp ->
        printlns("Exception thrown in one of the children $exp")
    }


    fun main () {
        val parentJob = CoroutineScope(IO).launch(handler) {

            supervisorScope {
                val jobA = launch {
                    val resultA = getResult(1)
                    printlns(resultA.toString())
                }
                jobA.invokeOnCompletion { throwable->
                    if (throwable != null) {
                        printlns("Error getting resultA $throwable")
                    }
                }

                val jobB = launch {
                    val resultA = getResult(2)
                    printlns(resultA.toString())
                }
                jobB.invokeOnCompletion { throwable->
                    if (throwable != null) {
                        printlns("Error getting resultB $throwable")
                    }
                }

                val jobC = launch {
                    val resultA = getResult(3)
                    printlns(resultA.toString())
                }
                jobC.invokeOnCompletion { throwable->
                    if (throwable != null) {
                        printlns("Error getting resultC $throwable")
                    }
                }
            }

        }.invokeOnCompletion {throwable->
            if (throwable != null) {
                printlns("Error in parent job $throwable")
            }
        }
    }

    suspend fun getResult ( number : Int) : Int {
        delay(number*500L)
        if (number == 2) {
            throw Exception("Error getting result for number : $number")
            //cancel(CancellationException("Error getting result for number : $number"))
            //throw CancellationException("Error getting result for number : $number")
        }
        return number
    }

    private fun printlns (message : String) {
        Log.d(TAG, message)
    }
}
