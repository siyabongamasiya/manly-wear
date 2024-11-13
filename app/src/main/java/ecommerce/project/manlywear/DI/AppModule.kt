package ecommerce.project.manlywear.DI

import android.content.Context
import androidx.room.Room
import androidx.work.ListenableWorker
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ecommerce.project.manlywear.App.MenlyWear
import ecommerce.project.manlywear.Data.RepositoryImpl.RepositoryImpl
import ecommerce.project.manlywear.Data.Retrofit.ProductApiService
import ecommerce.project.manlywear.Data.Room.AppDatabase
import ecommerce.project.manlywear.Data.Room.BasketRoomDAO
import ecommerce.project.manlywear.Data.Room.OrderProductRoomDao
import ecommerce.project.manlywear.Data.Room.OrderRoomDao
import ecommerce.project.manlywear.Data.Room.ShoppableRoomDAO
import ecommerce.project.manlywear.Domain.Repository.AuthRepository
import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.UseCases.DeleteBasketProduct
import ecommerce.project.manlywear.Domain.UseCases.GetBasketProducts
import ecommerce.project.manlywear.Domain.UseCases.LocallyFetchShoppableProduct
import ecommerce.project.manlywear.Domain.UseCases.LocallyFetchShoppableProducts
import ecommerce.project.manlywear.Domain.UseCases.RemoteFetchAndStoreShoppableProducts
import ecommerce.project.manlywear.Domain.UseCases.SaveBasketProduct
import ecommerce.project.manlywear.Domain.UseCases.SaveOrder
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import ecommerce.project.manlywear.Firebase.AuthRepositoryImpl
import ecommerce.project.manlywear.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun ProvideLocallyFetchShoppableProducts(repository: Repository) : LocallyFetchShoppableProducts{
        return LocallyFetchShoppableProducts(repository)
    }

    @Provides
    @Singleton
    fun ProvideLocallyFetchShoppableProduct(repository: Repository) : LocallyFetchShoppableProduct{
        return LocallyFetchShoppableProduct(repository)
    }

    @Provides
    @Singleton
    fun ProvideRemoteFetchShoppableProduct(repository: Repository) : RemoteFetchAndStoreShoppableProducts{
        return RemoteFetchAndStoreShoppableProducts(repository)
    }

    @Provides
    @Singleton
    fun ProvideSaveBasketProduct(repository: Repository) : SaveBasketProduct{
        return SaveBasketProduct(repository)
    }

    @Provides
    @Singleton
    fun ProvideGetBasketProducts(repository: Repository) : GetBasketProducts{
        return GetBasketProducts(repository)
    }

    @Provides
    @Singleton
    fun ProvideDeleteBasketProducts(repository: Repository) : DeleteBasketProduct{
        return DeleteBasketProduct(repository)
    }

    @Provides
    @Singleton
    fun ProvideSaveOrder(repository: Repository) : SaveOrder{
        return SaveOrder(repository)
    }

    @Provides
    @Singleton
    fun ProvideUseCasePack(locallyFetchShoppableProducts: LocallyFetchShoppableProducts,
                           remoteFetchAndStoreShoppableProducts: RemoteFetchAndStoreShoppableProducts,
                           locallyFetchShoppableProduct: LocallyFetchShoppableProduct,
                           saveBasketProduct: SaveBasketProduct,
                           getBasketProducts: GetBasketProducts,
                           deleteBasketProduct: DeleteBasketProduct,
                           saveOrder: SaveOrder) : UseCasePack{

        return UseCasePack(locallyFetchShoppableProducts,
            remoteFetchAndStoreShoppableProducts,
            locallyFetchShoppableProduct,
            saveBasketProduct,
            getBasketProducts,
            deleteBasketProduct,
            saveOrder)
    }

    @Provides
    @Singleton
    fun ProvidesApp(@ApplicationContext context: Context) : MenlyWear{
        return context as MenlyWear
    }


    @Provides
    @Singleton
    fun ProvideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun ProvideService() : ProductApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun ProvideShoppableDAO(context: MenlyWear) : ShoppableRoomDAO{
        return Room.databaseBuilder(context, AppDatabase::class.java,
            context.getString(R.string.Prouct_DataBase_Name))
            .fallbackToDestructiveMigration()
            .build()
            .shoppableDao()
    }

    @Provides
    @Singleton
    fun ProvideBasketDAO(context: MenlyWear) : BasketRoomDAO{
        return Room.databaseBuilder(context, AppDatabase::class.java,
            context.getString(R.string.Prouct_DataBase_Name))
            .fallbackToDestructiveMigration()
            .build()
            .basketDao()
    }

    @Provides
    @Singleton
    fun ProvideOrderDAO(context: MenlyWear) : OrderRoomDao{
        return Room.databaseBuilder(context, AppDatabase::class.java,
            context.getString(R.string.Prouct_DataBase_Name))
            .fallbackToDestructiveMigration()
            .build()
            .orderDao()
    }

    @Provides
    @Singleton
    fun ProvideOrderProductDAO(context: MenlyWear) : OrderProductRoomDao{
        return Room.databaseBuilder(context, AppDatabase::class.java,
            context.getString(R.string.Prouct_DataBase_Name))
            .fallbackToDestructiveMigration()
            .build()
            .orderProductDao()
    }

}

@Module
@InstallIn(SingletonComponent :: class)
abstract class AbstractionModule{

    @Binds
    abstract fun BindAuthRepo(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun BindProductRepo(repositoryImpl: RepositoryImpl) : Repository
}


