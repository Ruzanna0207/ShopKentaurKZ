package com.manage.shopkentaurkz.core.data_classes

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore
import com.manage.shopkentaurkz.core.roomDataBase.for_rider.ForRiderEntity

data class ForRiderProduct(
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
    )

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
    @Ignore
    fun toRiderEntity(): ForRiderEntity {
        return ForRiderEntity(0, name!!, description!!, sizes!!, price, image!!, categories!!)
    }
}