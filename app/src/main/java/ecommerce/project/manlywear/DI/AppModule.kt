package ecommerce.project.manlywear.DI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ecommerce.project.manlywear.Domain.Model.Repository.AuthRepository
import ecommerce.project.manlywear.Firebase.AuthRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun ProvideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
}

@Module
@InstallIn(SingletonComponent :: class)
abstract class AbstractionModule{

    @Binds
    abstract fun BindRepo(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository
}