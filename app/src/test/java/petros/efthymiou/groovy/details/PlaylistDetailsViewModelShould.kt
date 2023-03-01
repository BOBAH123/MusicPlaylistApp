package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.details.models.PlaylistDetails
import petros.efthymiou.groovy.details.repository.PlaylistDetailsRepository
import petros.efthymiou.groovy.details.viewModel.PlaylistDetailsViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould : BaseUnitTest() {
    private lateinit var viewModel: PlaylistDetailsViewModel
    private val repository: PlaylistDetailsRepository = mock()
    private val id = "1"
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Before
    fun setUp() {
        viewModel = PlaylistDetailsViewModel(repository)
    }

    @Test
    fun fetchPlaylistDetails() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(repository).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceError() = runBlockingTest {
        mockErrorCase()
        assertEquals(Result.failure<PlaylistDetails>(exception), viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterPlaylistDetailsLoad() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockErrorCase() {
        whenever(repository.fetchPlaylistDetails(id)).thenReturn(flow { emit(error) })
        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(repository.fetchPlaylistDetails(id)).thenReturn(flow { emit(expected) })
    }
}