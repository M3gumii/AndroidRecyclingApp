package com.example.recyclingapp

import androidx.activity.viewModels
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recyclingapp.dataClasses.database.RecyclingDatabase
import com.example.recyclingapp.dataClasses.database.SupabaseApi
import com.example.recyclingapp.dataClasses.database.SupabaseConnection
import com.example.recyclingapp.dataClasses.database.User
import com.example.recyclingapp.viewmodels.database.UserViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import kotlin.getValue

@OptIn(ExperimentalCoroutinesApi::class)
class UnitTests {

    /**
     * Unit test testing rules...
     */

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: RecyclingDatabase    //Mock repo for subabase
    private lateinit var userViewModel: UserViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) //Makes a fake main thread...
        //As not run on a real app!

        repo = mockk()  //Sets the fake repo to a mock item!
        userViewModel = UserViewModel(repo)
        //the userviewmodel now is set to call the fake repo!
        //the fake repo returns whatever we want!
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    /**
     * Tests to make sure the viewModel calls the specified repo's getUser on
     * an existing user - should return that user!
     */
    @Test
    fun getExistingUser() = runTest {   //Use runTest as uses coroutines to access the view model...
        //Set the mock repo to return a fake user when prompted!
        val fakeUser = User("bob", "pw", "email")
        coEvery { repo.getUser("bob") } returns fakeUser

        userViewModel.attemptedUsername = "bob" //Set username to user we want to find...

        userViewModel.loadUser()    //Try and get 'bob' from the repo...

        //Run all coroutines I asked for - aka run the loadUser thing...
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(fakeUser, userViewModel.selectedUser.value)
    }

    /**
     * Tests to make sure the viewModel calls the specified repo's getUser on
     * a non-existing user - should not return anything as user not present...
     */
    @Test
    fun getNonExistingUser() = runTest {   //Use runTest as uses coroutines to access the view model...
        coEvery { repo.getUser("bob") } returns null    //Should return null on user not found...

        userViewModel.attemptedUsername = "bob" //Set username to user we want to find...

        userViewModel.loadUser()    //Try and get 'bob' from the repo... Shouldn't get anything back...

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(null, userViewModel.selectedUser.value)
    }

    /**
     * Tests to make sure the viewModel calls the specified repo's updateUserCount
     * which returns the user that had their count updated!
     *
     * Existing user case...
     */
    @Test
    fun updateUserRecyclingCount() = runTest {   //Use runTest as uses coroutines to access the view model...
        coEvery { repo.updateUserCount("bob") } returns User("bob", "pw", "b@gmail.com", 1)
        val usrList: List<User> = listOf(User("bob", "pw", "b@gmail.com", 1))

        coEvery { repo.getAllUsers() } returns usrList
        //repo should return the user updated as well as update the list for _users of the viewModel!

        userViewModel.addToUserRecyclingCount("bob")

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { repo.updateUserCount("bob") }   //ensure updateUserRecyclingCount called once...
        coVerify(exactly = 1) { repo.getAllUsers() }

        assertEquals(usrList, userViewModel.users.value)
    }

}