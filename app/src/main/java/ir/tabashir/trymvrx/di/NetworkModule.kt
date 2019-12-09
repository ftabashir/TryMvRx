package ir.tabashir.trymvrx.di

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import ir.tabashir.trymvrx.BuildConfig
import ir.tabashir.trymvrx.network.ApiService
import ir.tabashir.trymvrx.network.AuthenticationInterceptor
import ir.tabashir.trymvrx.network.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @JvmStatic
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
    }

    @JvmStatic
    @Provides
    fun provideMoshi(dateAdapter: JsonAdapter<Date>): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, dateAdapter)
            .build()
    }

    @JvmStatic
    @Provides
    fun provideDateConverter(): JsonAdapter<Date> {
        return Rfc3339DateJsonAdapter().nullSafe()
    }

    @JvmStatic
    @Provides
    fun provideOkHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(authenticationInterceptor)
                if (BuildConfig.FLAVOR == "staging") {
                    addNetworkInterceptor(loggingInterceptor)
                    addNetworkInterceptor(chuckInterceptor)
                }
            }.build()
    }

    @JvmStatic
    @Provides
    fun provideAuthInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor()
    }

    @JvmStatic
    @Provides
    fun provideChuckInterceptor(application: Application): ChuckInterceptor {
        return ChuckInterceptor(application.applicationContext)
            .showNotification(true)
    }

    @JvmStatic
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            Timber.tag("OkHttp").d(it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}