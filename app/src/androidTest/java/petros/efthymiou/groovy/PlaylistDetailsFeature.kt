package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.Matchers
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

class PlaylistDetailsFeature : BaseUITest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlaylistDetails(0)

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(2000)

        navigateToPlaylistDetails(0)


        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetails(0)

        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun showErrorMessageWhenNetworkFails() {
        navigateToPlaylistDetails(1)

        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hideErrorMessage() {
        navigateToPlaylistDetails(1)
        Thread.sleep(3000)

        assertNotExist(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(row: Int) {
        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlists_list),
                        row
                    )
                )
            )
        ).perform(ViewActions.click())
    }
}