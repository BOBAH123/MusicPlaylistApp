package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistDetailsServiceShould : BaseUnitTest() {

    private val id = "1"
    private val playlistDetailsAPI: PlaylistDetailsAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val service = PlaylistDetailsService(playlistDetailsAPI)

    @Test
    fun fetchPlaylistDetailsFromApi() = runBlockingTest {
        service.fetchPlaylistDetails(id).first()

        verify(playlistDetailsAPI).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        whenever(playlistDetailsAPI.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        Assert.assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest{
        whenever(playlistDetailsAPI.fetchPlaylistDetails(id)).thenThrow(
            RuntimeException("System not responding")
        )

        Assert.assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).first().exceptionOrNull()?.message
        )
    }
}