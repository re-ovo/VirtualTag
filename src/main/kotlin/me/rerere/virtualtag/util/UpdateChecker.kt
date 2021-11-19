package me.rerere.virtualtag.util

import me.rerere.virtualtag.virtualTag
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object UpdateChecker {
    fun getLatestVersion(): String = try {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("https://cdn.jsdelivr.net/gh/re-ovo/VirtualTag/build.gradle.kts")
            .get()
            .build()
        val response = httpClient.newCall(request).execute()
        val body = response.body!!.string()
        body.substring(
            body.indexOf("version = \"") + "version = \"".length,
            body.indexOf(char = '"', startIndex = body.indexOf("version = \"") + "version = \"".length)
        ).trim()
    } catch (e: Exception) {
        e.printStackTrace()
        virtualTag().description.version
    }
}