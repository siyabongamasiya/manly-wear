package ecommerce.project.manlywear.Utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    private const val PREFS_NAME = "user_logins"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    // Save username and password to SharedPreferences
    fun saveCredentials(context: Context, email: String, password: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply() // apply() is asynchronous, while commit() is synchronous
    }

    // Retrieve username from SharedPreferences
    fun getEmail(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    // Retrieve password from SharedPreferences
    fun getPassword(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

    // Clear username and password from SharedPreferences (e.g., for logout)
    fun clearCredentials(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_PASSWORD)
        editor.apply()
    }
}