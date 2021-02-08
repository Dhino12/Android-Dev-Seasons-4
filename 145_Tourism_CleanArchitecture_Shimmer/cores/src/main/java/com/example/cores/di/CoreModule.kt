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
import okhttp3.CertificatePinner
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
        // hostname adalah nama base-url dari server Anda.
        val hostname = "tourism-api.dicoding.dev"
        //  Selanjutnya untuk menambahkan konfigurasi CertificatePinning Anda dapat menggunakan
        //  CertificatePinning.Bulder dengan fungsi add
        val certificatepinner = CertificatePinner.Builder()
            .add(hostname,"sha256/efJRcxel8l8esct4H2iQRwcvs79t8AKGlcgiaBPor98=")
            .add(hostname,"sha256/qPerI4uMwY1VrtRE5aBY8jIQJopLUuBt2+GDUWMwZn4=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatepinner) // Setelah usai baru ditambahkan ke OkHttp.Builder dengan fungsi certificatePinner().
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