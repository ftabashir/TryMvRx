package ir.tabashir.trymvrx.network

import ir.tabashir.trymvrx.network.model.Category
import ir.tabashir.trymvrx.network.result.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"
    }

    @GET("api/categories")
    suspend fun categories(
        @Query(PAGE) page: Int,
        @Query(SIZE) size: Int
    ): Result<List<Category>>

}