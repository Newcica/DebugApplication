package com.example.myfirstapplication.debug

class SingleTon private constructor(){
    companion object {
        @Volatile
        private var instance : SingleTon? = null
        public fun  getInstance() : SingleTon {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SingleTon()
                    }
                }
            }
            return  instance!!
        }
    }
}