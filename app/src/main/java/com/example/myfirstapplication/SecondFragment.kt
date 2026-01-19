package com.example.myfirstapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {
    
    private val tag = "SecondFragment"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogManager.d(tag, "onCreate")
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogManager.d(tag, "onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogManager.d(tag, "onViewCreated")
        
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = "这是第二个Fragment\n\n在这里您可以放置任意内容"
    }
    
    override fun onStart() {
        super.onStart()
        LogManager.d(tag, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        LogManager.d(tag, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        LogManager.d(tag, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        LogManager.d(tag, "onStop")
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        LogManager.d(tag, "onDestroyView")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        LogManager.d(tag, "onDestroy")
    }
}