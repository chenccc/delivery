package com.james.delivery.ui.deliveries.detail

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.filters.MediumTest
import com.james.delivery.DeliveryFactory
import com.james.delivery.launchFragmentInHiltContainer
import com.james.delivery.util.MyPreference
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.james.delivery.R
import com.google.common.truth.Truth.assertThat
import com.james.delivery.data.model.Delivery
import io.mockk.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class DeliveryDetailFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var preference: MyPreference

    private lateinit var deliveryFactory: DeliveryFactory
    private lateinit var delivery: Delivery

    @Before
    fun setUp() {
        hiltRule.inject()
        deliveryFactory = DeliveryFactory()
        delivery = deliveryFactory.getTestDelivery()
        preference.setBoolean(delivery.id, false)
    }

    @Test
    fun givenDefaultDeliveryWithoutFavSetInPref() {
        val navController = mockk<NavController>(relaxed = true)
        val bundle = Bundle().apply {
            putParcelable("delivery", delivery)
        }

        launchFragmentInHiltContainer<DeliveryDetailFragment>(
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.add_remove_fav_button)).check(matches(withText(R.string.add_to_fav)))
        onView(withId(R.id.add_remove_fav_button)).perform(click())

        assertThat(preference.getBoolean(delivery.id)).isTrue()
        verify {
            navController.navigateUp()
        }
    }

    @Test
    fun givenDefaultDeliveryWithFavSetInPref() {
        val navController = mockk<NavController>(relaxed = true)
        val bundle = Bundle().apply {
            putParcelable("delivery", delivery)
        }

        preference.setBoolean(delivery.id, true)

        launchFragmentInHiltContainer<DeliveryDetailFragment>(
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.add_remove_fav_button)).check(matches(withText(R.string.remove_from_fav)))

        onView(withId(R.id.add_remove_fav_button)).perform(click())

        assertThat(preference.getBoolean(delivery.id)).isFalse()
        verify {
            navController.navigateUp()
        }
    }
}