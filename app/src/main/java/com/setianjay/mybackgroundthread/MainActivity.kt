package com.setianjay.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnExecutor: Button
    private lateinit var resultExecutor: TextView

    private lateinit var btnCoroutine: Button
    private lateinit var resultCoroutine: TextView

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
    }

    private fun initView() {
        btnExecutor = findViewById(R.id.btn_start1)
        resultExecutor = findViewById(R.id.tv_result1)

        btnCoroutine = findViewById(R.id.btn_start2)
        resultCoroutine = findViewById(R.id.tv_result2)
    }

    private fun initListener() {
        btnExecutor.setOnClickListener(this)
        btnCoroutine.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start1 -> {
                runWithExecutor()
            }
            R.id.btn_start2 -> {
                runWithCoroutine()
            }
        }
    }

    private fun runWithExecutor() {
        val executors = Executors.newSingleThreadExecutor()
        try {
            executors.execute {
                for (i in 0..10) {
                    Thread.sleep(500)
                    val percentage = i * 10

                    handler.post {
                        if (percentage == 100) {
                            resultExecutor.text = getString(R.string.task_completed)
                        } else {
                            resultExecutor.text = getString(R.string.percentage, percentage)
                        }
                    }
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun runWithCoroutine() {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            for (i in 0..10) {
                delay(500)
                val percentage = i * 10

                withContext(Dispatchers.Main){
                    if(percentage == 100){
                        resultCoroutine.text = getString(R.string.task_completed)
                    }else{
                        resultCoroutine.text = getString(R.string.percentage, percentage)
                    }
                }
            }
        }
    }
}