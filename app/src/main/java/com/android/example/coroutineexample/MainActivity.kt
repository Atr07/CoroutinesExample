package com.android.example.coroutineexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            // IO, Main, Default
            CoroutineScope(IO).launch() {
                fakeApiResult()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = text1.text.toString() + "\n$input"
        text1.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiResult(){
        val result1 = getResult1FromApi1()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResult1FromApi2()
        println("debug: $result2")
        setTextOnMainThread(result2)
    }

    private suspend fun getResult1FromApi1(): String {
        logThread("getResultFromApi1")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult1FromApi2(): String {
        logThread("getResultFromApi2")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
