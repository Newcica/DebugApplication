package com.example.myfirstapplication

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class MyData(
    val id: Int,             // 整型
    val name: String,        // 字符串
    val isSuccess: Boolean,  // 布尔型
    val price: Float,        // 浮点型
    val tags: List<String>   // 集合类型
) : Parcelable {
    //question1 返序列化构造是做什么的
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readFloat(),
        parcel.createStringArrayList()!!
    )


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeByte(if (isSuccess) 1 else 0)
        dest.writeFloat(price)
        dest.writeStringList(tags)
    }

    //这里的companion关键字是什么作用
    companion object CREATOR : Parcelable.Creator<MyData> {
        override fun createFromParcel(parcel: Parcel): MyData {
            return MyData(parcel)
        }
        override fun newArray(size: Int): Array<MyData?> {
            return arrayOfNulls(size)
        }
    }
}