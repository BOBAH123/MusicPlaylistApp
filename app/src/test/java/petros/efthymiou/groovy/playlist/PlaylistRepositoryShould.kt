package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val repository = PlaylistRepository(service)
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getPlaylistFromService() = runBlockingTest {
        repository.getPlaylist()

        verify(service).fetchPlaylists()
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        assertEquals(exception, repository.getPlaylist().first().exceptionOrNull())
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        assertEquals(playlists, repository.getPlaylist().first().getOrNull())
    }
}