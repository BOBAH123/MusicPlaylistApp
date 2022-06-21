package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private val playlistAPI: PlaylistAPI = mock()
    private val playlists: List<PlaylistRaw> = mock()
    private val service = PlaylistService(playlistAPI)

    @Test
    fun callFetchAllPlaylistsMethod() = runBlockingTest {
        service.fetchPlaylists().first()

        verify(playlistAPI).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        whenever(playlistAPI.fetchAllPlaylists()).thenReturn(playlists)

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitResultWhenNetworkFails() = runBlockingTest {
        whenever(playlistAPI.fetchAllPlaylists()).thenThrow(
            RuntimeException("System not responding")
        )

        assertEquals("Something went wrong", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }
}