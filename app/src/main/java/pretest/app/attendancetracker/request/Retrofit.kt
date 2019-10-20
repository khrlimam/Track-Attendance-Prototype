package pretest.app.attendancetracker.request

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import pretest.app.attendancetracker.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val okHttpClient by lazy {
  OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = BASIC })
    .build()
}

val client: Retrofit = Retrofit.Builder()
  .baseUrl(BuildConfig.BASE_URL)
  .client(okHttpClient)
  .addConverterFactory(GsonConverterFactory.create())
  .build()