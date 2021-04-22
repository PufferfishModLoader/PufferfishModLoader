package me.dreamhopping.pml.mods.json

import com.google.gson.annotations.SerializedName

data class PMLModJson(val mods: List<Entry>) {
    data class Entry(
        val id: String,
        @SerializedName("class") val clazz: String
    )
}
