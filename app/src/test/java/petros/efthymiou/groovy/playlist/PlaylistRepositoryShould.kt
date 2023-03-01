package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.PlaylistRaw
import petros.efthymiou.groovy.playlist.models.mappers.PlaylistMapper
import petros.efthymiou.groovy.playlist.repository.PlaylistRepository
import petros.efthymiou.groovy.playlist.service.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val repository = PlaylistRepository(service, mapper)
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getPlaylistFromService() = runBlockingTest {
        repository.getPlaylist()

        verify(service).fetchPlaylists()
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        mockErrorCase()

        assertEquals(exception, repository.getPlaylist().first().exceptionOrNull())
    }

    @Test
    fun emitMappedPlaylistsFromService() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylist().first().getOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        mockSuccessfulCase()
        repository.getPlaylist().first()

        verify(mapper).invoke(playlistsRaw)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistsRaw))
            }
        )

        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)
    }

    private suspend fun mockErrorCase() {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
    }
}