package ecommerce.project.manlywear.Firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ecommerce.project.manlywear.Domain.Repository.AuthRepository
import ecommerce.project.manlywear.Utils.SharedPreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    override suspend fun signUp(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun addStateListener(context : Context,ongotologin : () -> Unit) {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                ongotologin.invoke()
            }
        }

        auth.addAuthStateListener(authStateListener)
    }

    override fun removeStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}