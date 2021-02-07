package com.example.cores.di

import androidx.room.Room
import com.example.cores.data.TourismRepository
import com.example.cores.data.source.local.LocalDataSource
import com.example.cores.data.source.local.room.TourismDatabase
import com.example.cores.data.source.remote.RemoteDataSource
import com.example.cores.data.source.remote.network.ApiService
import com.example.cores.domain.repository.ITourismRepository
import com.example.cores.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module{
    factory { get<TourismDatabase>().tourismDao() }

    single {
        // passphrase yang merupakan password untuk membuat enkripsi.
        val passpharse:ByteArray = SQLiteDatabase.getBytes("raja-batu".toCharArray())
        // SupportFactory sendiri yang dapat digunakan untuk membuat enkripsi.
        val factory = SupportFactory(passpharse)
        Room.databaseBuilder(
            androidContext(),
            TourismDatabase::class.java, "Tourism.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tourism-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ITourismRepository> { TourismRepository(get(), get(), get()) }

}