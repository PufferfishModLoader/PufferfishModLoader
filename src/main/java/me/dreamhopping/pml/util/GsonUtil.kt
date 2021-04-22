package me.dreamhopping.pml.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T : Any> String?.fromJson(): T? = this?.let {
    val type = object : TypeToken<T>() {}.type
    Gson().fromJson(this, type)
}
