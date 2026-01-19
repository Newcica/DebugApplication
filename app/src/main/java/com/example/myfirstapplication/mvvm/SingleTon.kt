package com.example.myfirstapplication.mvvm

class SingleTon private constructor(){
    companion object {
        @Volatile
        private var instance: SingleTon? = null
        fun getInstance(): SingleTon {

            if (instance == null) {
                synchronized( this) {
                    if (instance == null) {
                        instance = SingleTon()
                    }
                }
            }
            return instance!!
        }
    }
}