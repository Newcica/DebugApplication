package com.example.myfirstapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfirstapplication.ipc.socket.MySocketServerService
import com.example.myfirstapplication.mvp.UserActivity
import com.example.myfirstapplication.mvvm.MvvmTestActivity
import com.example.myfirstapplication.recyclerview.RecyclerViewActivity
import com.example.myfirstapplication.wechat.MomentsActivity
import com.example.myfirstapplication.wechat.ContactsActivity

class MainActivity : AppCompatActivity() {
    val sTAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
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
        
        // 添加Fragment通信演示按钮点击事件
        findViewById<Button>(R.id.btnStartFragmentDemo).setOnClickListener {
            startActivity(Intent(this, FragmentDemoActivity::class.java))
        }
        
        // 添加ViewPager Fragment演示按钮点击事件
        findViewById<Button>(R.id.btnStartViewPagerFragmentDemo).setOnClickListener {
            startActivity(Intent(this, ViewPagerFragmentActivity::class.java))
        }
        
        // 添加Fragment生命周期演示按钮点击事件
        findViewById<Button>(R.id.btnStartLifecycleDemo).setOnClickListener {
            startActivity(Intent(this, LifecycleDemoActivity::class.java))
        }
        
        // 添加朋友圈演示按钮点击事件
        findViewById<Button>(R.id.btnStartMomentsDemo).setOnClickListener {
            startActivity(Intent(this, MomentsActivity::class.java))
        }
        
        // 添加联系人演示按钮点击事件
        findViewById<Button>(R.id.btnStartContactsDemo).setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        findViewById< Button>(R.id.btnStartSocketService).setOnClickListener {
//            startService(Intent(this, MySocketServerService::class.java))
//            startActivity(Intent(this, UserActivity::class.java))
//            startActivity(Intent(this, MvvmTestActivity::class.java))
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
        testLambda()
    }

    fun printLog(str : String) :Unit {
        Log.d(sTAG, "$str")
    }

    fun test() {
        var name : String? = null
        //？表示 如果name是null，那后面的.length不会再执行，所以不会出现NPE异常
        Log.d(sTAG, "length is " + name?.length)
        //!!表示 即使name是null，后面的.length会强制执行，与java类似
        Log.d(sTAG, "length is " + name!!.length)
        //第三种 和java一致，使用前盼空
//        if (name != null) {
//            Log.d(sTAG, "length is " + name.length)
//        }
    }

    //This is a method.
    val method : (String, String)  -> Unit = {str1, str2 ->
        printLog( "str1 is $str1, str2 is $str2")
    }

    val method2 = {
        printLog("method2")
    }

    val method3 : (String) -> Unit = {
        printLog("method3 str is $it")
    }

    val method4 : (String) -> Unit = { str ->
        printLog("method4 str is $str")
    }

    var method5 : (Int,  Int) -> Unit = { num1, num2 ->
        when(num1) {
            1 -> printLog("1")
            in 2 .. 10-> printLog("2 - 10")
            in 11 ..100 -> {
                printLog("11 - 100 <")
            }
            else -> printLog(" else")
        }

        login1("111", "222") {
            var str : String ?= null
            str = if (it) {
                printLog("login success")
                "1"
            } else {
                printLog("login failed")
                "0"
            }
        }
    }

    val method6 : (Int, Int) -> Unit = { num1, _ ->
        printLog("method6 num1 is $num1 num2 is invalid")
    }

    val method07 : (Int) -> Int = { num1 -> num1
    }

    fun testLambda() {
        //This is a method.
        /*val method : (String, String)  -> Unit = {str1, str2 ->
            Log.d(sTAG, "str1 is $str1, str2 is $str2")
        }*/
        method("hello", "world")

        method2()
        method3("hello, method 03")

        login("admin", "admin") { result: Boolean ->
            printLog("login result is $result")
        }

        login("admin",
            "admin",
            { result: Boolean ->
            printLog("login result is $result")
        })
    }

    private fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        if (username == "admin" && password == "admin") {
            callback(true)
        } else {
            callback(false)
        }
    }


    fun login1(userName : String, pwd : String, onResult : (Boolean) -> Unit) {

    }
}