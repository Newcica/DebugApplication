package com.example.myfirstapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class DemoFragment : Fragment() {
    
    private val tag = "DemoFragment"
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var messageInput: EditText
    private lateinit var receivedMessageText: TextView
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(tag, "onAttach")
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(tag, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")
        
        // 初始化ViewModel
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        
        // 初始化视图组件
        messageInput = view.findViewById(R.id.messageInput)
        receivedMessageText = view.findViewById(R.id.receivedMessageText)
        val sendButton = view.findViewById<Button>(R.id.btnSendToActivity)
        
        // 观察来自Activity的消息
        sharedViewModel.activityMessage.observe(viewLifecycleOwner) { message ->
            receivedMessageText.text = "来自Activity的消息: $message"
        }
        
        // 设置发送按钮点击事件
        sendButton.setOnClickListener {
            sendMessageToActivity()
        }
    }
    
    private fun sendMessageToActivity() {
        val message = messageInput.text.toString()
        if (message.isNotEmpty()) {
            sharedViewModel.sendFragmentMessage(message)
            messageInput.setText("")
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tag, "onDestroyView")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
    
    override fun onDetach() {
        super.onDetach()
        Log.d(tag, "onDetach")
    }
}