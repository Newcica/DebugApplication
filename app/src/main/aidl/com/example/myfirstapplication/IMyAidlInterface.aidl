// IMyAidlInterface.aidl
package com.example.myfirstapplication;

// Declare any non-default types here with import statements
import com.example.myfirstapplication.MyData;

interface IMyAidlInterface {

    MyData getData(String requestParam);

    void registerCallback(IMyCallback callback);

    interface IMyCallback {
        void onDataPush(in MyData data);
    }
}