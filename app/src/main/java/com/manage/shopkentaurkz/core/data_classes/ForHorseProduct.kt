package com.manage.shopkentaurkz.core.data_classes

import android.os.Parcel
import android.os.Parcelable


data class ForHorseProduct(
    val name: String?,
    val description: String?,
    val sizes: List<String>?,
    val price: Double,
    val image:  List<String>?,
    val categories: List<String>?
) : Parcelable {
    constructor() : this("", "", emptyList(), 0.0, emptyList(), emptyList())
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readDouble(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeStringList(sizes)
        parcel.writeDouble(price)
        parcel.writeStringList(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForRiderProduct> {
        override fun createFromParcel(parcel: Parcel): ForRiderProduct {
            return ForRiderProduct(parcel)
        }

        override fun newArray(size: Int): Array<ForRiderProduct?> {
            return arrayOfNulls(size)
        }
    }
}
