package com.example.project2.app
import android.app.Application
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration

class MyApplication : Application() {
    lateinit var app: App

    override fun onCreate() {
        super.onCreate()
        Realm.init(this) // Initialize Realm

        app = App.create(AppConfiguration.Builder("your-app-id").build())

        // Optional: Setup default configuration
        val config = RealmConfiguration.Builder(schema = setOf(User::class))
            .name("default.realm")
            .schemaVersion(1)
            .build()
        Realm.open(config)
    }
}
