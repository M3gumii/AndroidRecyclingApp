//package com.example.recyclingapp.database
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import okhttp3.Interceptor
//
///**
// * Connects to the online supabase db!
// *
// * Converts the JSON from REST requests to kotlin objects using
// * Moshi.
// */
//object SupabaseConnection {
//
//    /**
//     * Creates a reusable interceptor instance! Made at runtime via the lambda...
//     * Adds supabase API keys, etc. to each request made to the db...
//     */
//    private val authInterceptor: Interceptor = Interceptor { httpReq ->
//        val convertedReq = httpReq.request().newBuilder().addHeader("apiKey", SupabaseConfig.SUPABASE_API_KEY)
//            .addHeader("Authorization", "Bearer ${SupabaseConfig.SUPABASE_API_KEY}")
//            .addHeader("Content-Type", "application/json")
//            .build()
//        httpReq.proceed(convertedReq);  //Sends the Http req via okhttp!
//    }
//
//    /**
//     * Made to call the authInterceptor whenever an http call is made!
//     * Used to make the Http calls!
//     */
//    private val okHttp: OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(authInterceptor).build();
//
//
//    /**
//     * Main API helper!
//     * Connected to the supabase db and uses the http client created in this
//     * class to send the http requests.
//     */
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(SupabaseConfig.SUPABASE_URL)
//        .client(okHttp)
//        .addConverterFactory(MoshiConverterFactory.create())
//        // Converterfactory used to convert the json to kotlin objects using Moshi.
//        .build();
//
//    /**
//     * Actual API instance!
//     * Defines endpoints and how to access the db data!
//     *
//     * This tells Retrofit to gen the bodies for these various functions to use to
//     * access the db!
//     */
//    val api: SupabaseApi = retrofit.create<SupabaseApi>(SupabaseApi::class.java);
//
//
//}