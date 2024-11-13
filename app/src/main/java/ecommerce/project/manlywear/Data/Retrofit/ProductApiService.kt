package ecommerce.project.manlywear.Data.Retrofit

import ecommerce.project.manlywear.Domain.Model.retrofit.RetrofitProduct
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products/category/men's clothing")
    suspend fun fetchAllProducts(): Response<List<RetrofitProduct>>
}