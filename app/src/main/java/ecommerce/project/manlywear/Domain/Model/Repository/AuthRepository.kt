package ecommerce.project.manlywear.Domain.Model.Repository

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(email: String,password: String) : Result<FirebaseUser?>
    suspend fun signIn(email: String,password: String) : Result<FirebaseUser?>

    suspend fun signOut()

    fun addStateListener(context : Context,ongotologin : () -> Unit)

    fun removeStateListener()

    fun getCurrentUser() : FirebaseUser?
}

