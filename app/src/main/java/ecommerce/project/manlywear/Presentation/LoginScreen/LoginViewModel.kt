package ecommerce.project.manlywear.Presentation.LoginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Model.Repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel(){

    suspend fun Login(email : String,password : String) : Result<FirebaseUser?>{
        return authRepository.signIn(email,password)
    }
}