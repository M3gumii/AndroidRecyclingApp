package com.example.recyclingapp.database

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Tells retrofit how to generate the functions for getUsers, etc!
 */
interface SupabaseApi {

    /**
     * USER FUNCTIONS
     */

    @GET("rest/v1/Users")   //Get request
    suspend fun getAllUsers(   //suspend fun so runs asynch!
        @Query("select") select: String = "*"
    ): List<User>   //after retrofit parsing, gives a list of Users via Moshi conversion!

    /*
    Returns the user as retrieved by their username...
     */
    @GET("rest/v1/Users")
    suspend fun getUser(
        @Query("username") username: String,
        @Query("select") select: String = "*"
    ): List<User>

    @GET("rest/v1/Users")
    suspend fun getUserByEmail(
        @Query("email") email: String,
        @Query("select") select: String = "*"
    ): List<User>

    @Headers("Prefer: return=representation")   //Return inserted user in the resp...
    @POST("rest/v1/Users")
    suspend fun insertUser(
        @Body user: User    //Sends the user object in the body of the request!
    ): List<User>

    @Headers("Prefer: return=representation")
    @DELETE("rest/v1/Users")
    suspend fun deleteUser(
        @Query("username") username: String
    ): List<User>

    @Headers("Prefer: return=representation")
    @PATCH("rest/v1/Users")
    suspend fun updateUserRecyclingCount(
        @Query("username") username: String,
        @Body body: Map<String, Int>
    ): List<User>

    /**
     * PACKAGE FUNCTIONS
     */

    @GET("rest/v1/Packages")
    suspend fun getAllPackages(
        @Query("select") select: String = "*"
    ): List<Package>

    /*
    Retrieves the package via its barcode...
     */
    @GET("rest/v1/Packages")
    suspend fun getPackage(
        @Query("barcode") barcode: String,
        @Query("select") select: String = "*"
    ): List<Package>

    @Headers("Prefer: return=representation")
    @POST("rest/v1/Packages")
    suspend fun insertPackage(
        @Body newPackage: Package
    ): List<Package>

    @Headers("Prefer return=representation")
    @DELETE("rest/v1/Packages")
    suspend fun deletePackage(
        @Query("barcode") barcode: String
    ): List<Package>


    /**
     * PREVIOUS SEARCHES FUNCTIONS
     */

    @GET("rest/v1/PreviousSearches")
    suspend fun getAllSearches(
        @Query("select") select: String = "*"
    ): List<PreviousSearch>

    /*
    Retrieves the user previous searches via their username.
     */
    @GET("rest/v1/PreviousSearches")
    suspend fun getSearchByUser(
        @Query("username") username: String,
        @Query("select") select: String = "*"
    ): List<PreviousSearch>

    /*
    Get user searched item
     */
    @GET("rest/v1/PreviousSearches")
    suspend fun getSearchByUserAndBarcode(
        @Query("username") username: String,
        @Query("barcode") barcode: String,
        @Query("select") select: String = "*"
    ): List<PreviousSearch>

    @Headers("Prefer: return=representation")
    @POST("rest/v1/PreviousSearches")
    suspend fun insertSearch(
        @Body search: PreviousSearch
    ): List<PreviousSearch>

    /*
    Removes the previous search via barcode and username.
     */
    @Headers("Prefer return=representation")
    @DELETE("rest/v1/PreviousSearches")
    suspend fun deleteSearch(
        @Query("barcode") barcode: String,
        @Query("username") username: String
    ): List<PreviousSearch>

}