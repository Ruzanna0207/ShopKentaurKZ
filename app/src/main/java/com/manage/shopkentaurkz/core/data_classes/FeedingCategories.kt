package com.manage.shopkentaurkz.core.data_classes

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore
import com.manage.shopkentaurkz.core.roomDataBase.for_feeding.ForFeedingEntity

data class FeedingCategories(
    val name: String?,
    val description: String?,
    val compound: String?,
    val price: Double,
    val image: String?,
    val categories: List<String>?
) : Parcelable {
    constructor() : this("", "", "", 0.0, "", emptyList())
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(compound)
        parcel.writeDouble(price)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedingCategories> {
        override fun createFromParcel(parcel: Parcel): FeedingCategories {
            return FeedingCategories(parcel)
        }

        override fun newArray(size: Int): Array<FeedingCategories?> {
            return arrayOfNulls(size)
        }
    }
    @Ignore
    fun toFeedingEntity(): ForFeedingEntity {
        return ForFeedingEntity(0, name!!, description!!, compound!!, price, image!!, categories!!)
    }
}