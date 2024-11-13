package ecommerce.project.manlywear.Presentation.SignUpScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Repository.AuthRepository
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel(){

    suspend fun SignUp(email : String ,password : String) : Result<FirebaseUser?>{
        return authRepository.signUp(email,password)
    }
}