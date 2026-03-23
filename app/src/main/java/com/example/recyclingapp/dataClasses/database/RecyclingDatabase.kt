package com.example.recyclingapp.dataClasses.database

/**
 * Wraps the API calls for easy access!
 */
class RecyclingDatabase(private val api: SupabaseApi) {

    /**
     * User functions
     */
    suspend fun getUser(username: String): User? =
        api.getUser("eq.$username").firstOrNull()   //Makes a call to the db through the api connection!

    suspend fun getUserByEmail(email: String): User? =
        api.getUserByEmail("eq.$email").firstOrNull()

    suspend fun deleteUser(username: String): User? =
        api.deleteUser("eq.$username").firstOrNull()

    suspend fun addUser(user: User): User? =
        api.insertUser(user).firstOrNull()

    suspend fun getAllUsers(): List<User> =
        api.getAllUsers()

    suspend fun updateUserCount(username: String): User? {  //Adds 1 to user recycling count.
        var usr = getUser(username)
        if (usr != null) {
            usr = api.updateUserRecyclingCount(
                "eq.$username",
                mapOf("num_items_recycled" to (usr.num_items_recycled + 1))
            ).firstOrNull()
        }
        return usr
    }

    /**
     * Package functions
     */
    suspend fun getPackage(barcode: String): Package? =
        api.getPackage("eq.$barcode").firstOrNull()

    suspend fun deletePackage(barcode: String): Package? =
        api.deletePackage("eq.$barcode").firstOrNull()

    suspend fun addPackage(newPackage: Package): Package? =
        api.insertPackage(newPackage).firstOrNull()

    suspend fun getAllPackages(): List<Package> =
        api.getAllPackages()

    /**
     * Previous Search functions
     */
    suspend fun getSearchesByUsername(username: String): List<PreviousSearch> =
        api.getSearchByUser("eq.$username")

    suspend fun getSpecificSearch(username: String, barcode: String): PreviousSearch? =
        api.getSearchByUserAndBarcode("eq.$username", "eq.$barcode").firstOrNull()

    suspend fun deleteSearch(barcode: String, username: String): PreviousSearch? =
        api.deleteSearch("eq.$barcode", "eq.$username").firstOrNull()

    suspend fun addSearch(search: PreviousSearch): PreviousSearch? =
        api.insertSearch(search).firstOrNull()

    suspend fun getAllSearches(): List<PreviousSearch> =
        api.getAllSearches()
}