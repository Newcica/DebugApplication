package com.example.myfirstapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class SingleTaskActivity : AppCompatActivity() {
    private lateinit var taskIdText: TextView
    private lateinit var hashCodeText: TextView
    private lateinit var instanceIdText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.singleTask)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        title = "SingleTask Mode"
        
        // 显示任务ID和实例ID
        taskIdText = findViewById(R.id.taskIdText)
        hashCodeText = findViewById(R.id.hashCodeText)
        instanceIdText = findViewById(R.id.instanceIdText)
        
        taskIdText.text = "Task ID: ${this.taskId}"
        hashCodeText.text = "Hash Code: ${this.hashCode()}"
        instanceIdText.text = "Instance ID: ${getInstanceId()}"
        
        // 设置按钮点击事件
        findViewById<Button>(R.id.btnStartStandard).setOnClickListener {
            startActivity(Intent(this, StandardActivity::class.java))
        }
        
        findViewById<Button>(R.id.btnStartSingleTop).setOnClickListener {
            startActivity(Intent(this, SingleTopActivity::class.java))
        }
        
        findViewById<Button>(R.id.btnStartSingleTask).setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
        
        findViewById<Button>(R.id.btnStartSingleInstance).setOnClickListener {
            startActivity(Intent(this, SingleInstanceActivity::class.java))
        }
        
        Log.d("LaunchMode", "SingleTaskActivity created, taskId: ${this.taskId}, hashCode: ${this.hashCode()}")
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("LaunchMode", "SingleTaskActivity onNewIntent called")
    }
    
    private fun getInstanceId(): String {
        return Integer.toHexString(System.identityHashCode(this))
    }
}