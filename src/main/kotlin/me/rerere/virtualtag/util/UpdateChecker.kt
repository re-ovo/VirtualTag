package me.rerere.virtualtag.util

import me.rerere.virtualtag.virtualTag
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

object UpdateChecker {
    fun getLatestVersion(): String = try {
        val httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build()
        val request = HttpRequest.newBuilder()
            .uri(URI("https://cdn.jsdelivr.net/gh/jiangdashao/VirtualTag/build.gradle.kts"))
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val body = response.body()
        body.substring(
            body.indexOf("version = \"") + "version = \"".length,
            body.indexOf(char = '"', startIndex = body.indexOf("version = \"") + "version = \"".length)
        ).trim()
    } catch (e: Exception) {
        e.printStackTrace()
        virtualTag().description.version
    }
}