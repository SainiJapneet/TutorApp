package com.example.myapplication.Data

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREF_NAME = "MY_SHARED_PREFERENCES"
    private lateinit var sharedPreferences: SharedPreferences

    private const val USER_IMAGE_URI = "image_uri"
    private const val USER_NAME = "name"
    private const val USER_EMAIL = "user_email"
    private const val KEY_USERNAME = "username"
    private const val UNIQUE_ID = "unique_id"
    private const val DEFAULT_USERNAME = ""
    private const val KEY_LOGGED_IN = "loggedIn"
    private const val VERIFICATION_ID_KEY = "verificationId"


    var username: String
        get() = sharedPreferences.getString(KEY_USERNAME, DEFAULT_USERNAME) ?: DEFAULT_USERNAME
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    private fun getPrefs(context: Context): SharedPreferences {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
        return sharedPreferences
    }

    fun saveUsername(context: Context, name: String){
        getPrefs(context).edit().putString(USER_NAME,name).apply()
    }
    fun getUsername(context: Context): String?{
        return getPrefs(context).getString(USER_NAME,null)
    }
    fun getLoggedIn(context: Context, isLogged: Boolean){
        getPrefs(context).edit().putBoolean(KEY_LOGGED_IN, isLogged).apply()
    }
    fun isLoggedIn(context: Context): Boolean?{
        return getPrefs(context).getBoolean(KEY_LOGGED_IN, false)
    }
    fun saveVerificationId(context: Context, verificationId: String) {
        getPrefs(context).edit().putString(VERIFICATION_ID_KEY, verificationId).apply()
    }

    fun getVerificationId(context: Context): String? {
        return getPrefs(context).getString(VERIFICATION_ID_KEY, null)
    }

    fun saveUID(context: Context, uid: String){
        getPrefs(context).edit().putString(UNIQUE_ID,uid).apply()
    }

    fun getUID(context: Context): String?{
        return getPrefs(context).getString(UNIQUE_ID, null)
    }

    fun saveUserImageURI(context: Context, uri: String){
        getPrefs(context).edit().putString(USER_IMAGE_URI, uri).apply()
    }
    fun getUserImageURI(context: Context): String?{
        return  getPrefs(context).getString(USER_IMAGE_URI, null)
    }

    fun saveUserEmailEncoded(context: Context, email: String){
        getPrefs(context).edit().putString(USER_EMAIL, email).apply()
    }
    fun getUSerEmailEncoded(context: Context): String?{
        return getPrefs(context).getString(USER_EMAIL, null)
    }

    fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }
}