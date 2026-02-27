package com.example.recyclingapp.database

/**
 * Wraps the API calls for easy access!
 */
class RecyclingDatabase(private val api: SupabaseApi) {

    /**
     * User functions
     */
    suspend fun getUser(username: String): User? = api.getUser(username).firstOrNull();
    suspend fun deleteUser(username: String): User? = api.deleteUser(username).firstOrNull();
    suspend fun addUser(user: User): User? = api.insertUser(user).firstOrNull();
    suspend fun getAllUsers(): List<User> = api.getAllUsers();

    /**
     * Package functions
     */
    suspend fun getPackage(barcode: String): Package? = api.getPackage(barcode).firstOrNull();
    suspend fun deletePackage(barcode: String): Package? = api.deletePackage(barcode).firstOrNull();
    suspend fun addPackage(newPackage: Package): Package? = api.insertPackage(newPackage).firstOrNull();
    suspend fun getAllPackages(): List<Package> = api.getAllPackages();

    /**
     * Previous Search functions
     */
    suspend fun getSearchesByUsername(username: String): List<PreviousSearch> = api.getSearchByUser(username);
    suspend fun getSpecificSearch(username: String, barcode: String): PreviousSearch? = api.getSearchByUserAndBarcode(username, barcode).firstOrNull();
    suspend fun deleteSearch(barcode: String, username: String): PreviousSearch? =
        api.deleteSearch(barcode, username).firstOrNull();
    suspend fun addSearch(search: PreviousSearch): PreviousSearch? =
        api.insertSearch(search).firstOrNull();
    suspend fun getAllSearches(): List<PreviousSearch> = api.getAllSearches();

}