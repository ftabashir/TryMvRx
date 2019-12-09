package ir.tabashir.trymvrx.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    companion object {
        private const val TOKEN = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .header("Authorization", TOKEN)
                .build()
        )
    }
}