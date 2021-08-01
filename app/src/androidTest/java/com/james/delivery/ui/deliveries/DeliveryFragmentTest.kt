package com.james.delivery.ui.deliveries

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.filters.MediumTest
import com.james.delivery.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.james.delivery.DeliveryFactory
import com.james.delivery.R
import com.james.delivery.ui.adapter.DeliveryAdapter
import io.mockk.verify


import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DeliveryFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    // Currently, this test case is working for stub mode
    @Test
    fun clickRecyclerViewFirstItem_navigationToDeliveryDetailFragment() {
        val navController = mockk<NavController>(relaxed = true)
        launchFragmentInHiltContainer<DeliveryFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rv_delivery))
            .perform(RecyclerViewActions.actionOnItemAtPosition<DeliveryAdapter.DeliveryViewHolder>(0, click()))
        verify{
            val delivery = DeliveryFactory().getDefaultDelivery()
            val action = DeliveryFragmentDirections.
            actionDeliveryFragmentToDeliveryDetailFragment(delivery)
            navController.navigate(action)
        }
    }
}