package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistViewModelShould : BaseUnitTest() {

    private lateinit var viewModel: PlaylistViewModel
    private val repository: PlaylistRepository = mock()
    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)
    private val exception = RuntimeException("Something went wrong")

    @Before
    fun setUp() {
        runBlocking {
            whenever(repository.getPlaylist()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        viewModel = PlaylistViewModel(repository)
    }

    @Test
    fun emitErrorWhenReceiveError() {
        runBlocking {
            whenever(repository.getPlaylist()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun getPlaylistFromRepository() = runBlockingTest {
        viewModel.playlists.getValueForTest()

        verify(repository).getPlaylist()
    }

    @Test
    fun emitsPlaylistFromRepository() = runBlockingTest {

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }
}