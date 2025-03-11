package com.example.project_aksis.hilt

import android.content.Context
import androidx.room.Room
import com.example.project_aksis.database.CharDataBase
import com.example.project_aksis.service.CharAPI
import com.example.project_aksis.service.repository.ApiRepo
import com.example.project_aksis.service.repository.ApiRepository
import com.example.project_aksis.service.repository.CharRepo
import com.example.project_aksis.service.repository.CharRepository
import com.example.project_aksis.util.Constants.BASE_URL
import com.example.project_aksis.util.Constants.PRIVATE_KEY
import com.example.project_aksis.util.Constants.PUBLIC_KEY
import com.example.project_aksis.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Hilt {

    @Provides
    @Singleton
    fun provideRetrofit() : CharAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CharAPI::class.java)

    @Provides
    @Singleton
    fun provideApiRepo(api: CharAPI, networkHelper: NetworkHelper) : ApiRepository = ApiRepo(api, networkHelper)

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)



    @Singleton
    val publicKey: String = PUBLIC_KEY
    private val privateKey: String = PRIVATE_KEY
    val ts = Timestamp(System.currentTimeMillis()).time.toString()
    fun generateHash(): String {
        val input = "$ts$privateKey$publicKey"
        val bytes = MessageDigest.getInstance("MD5")

        return BigInteger(1, bytes.digest(input.toByteArray())).
        toString(16).padStart(32, '0')
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, CharDataBase::class.java, "project_aksis").build()

    @Provides
    @Singleton
    fun provideDao(dataBase: CharDataBase) = dataBase.getDao()

    @Provides
    @Singleton
    fun provideDbRepo(dao : com.example.project_aksis.database.Dao) : CharRepository = CharRepo(dao)


}