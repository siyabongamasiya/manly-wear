package ecommerce.project.manlywear.Presentation.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Repository.AuthRepository
import ecommerce.project.manlywear.Firebase.AuthRepositoryImpl
import ecommerce.project.manlywear.Utils.InvalidEmailException
import ecommerce.project.manlywear.Utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(val authRepositoryImpl: AuthRepository) : ViewModel(){

    /**Test cases
     * 1.failing result for invalid email
     * 2.success for valid email
     */
    suspend fun SignInUser(email : String ,password : String) : Result<FirebaseUser?>{
        if(!isValidEmail(email)){
            return Result.failure(throw InvalidEmailException("Invalid email"))
        }

        return authRepositoryImpl.signIn(email, password)
    }

    /**Test cases
     * 1.returns failing result for invalid email
     * 2.returns success for valid email
     */
    suspend fun SignUpUser(email: String,password: String) : Result<FirebaseUser?>{
        return authRepositoryImpl.signUp(email, password)
    }

    fun signOutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepositoryImpl.signOut()
            println("User signed out.")
        }

    }

}