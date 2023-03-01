package petros.efthymiou.groovy.details.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.details.api.PlaylistDetailsAPI
import petros.efthymiou.groovy.details.models.PlaylistDetails
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(
    private val playlistDetailsAPI: PlaylistDetailsAPI
) {
    suspend fun fetchPlaylistDetails(
        id: String
    ): Flow<Result<PlaylistDetails>> = flow {
        emit(Result.success(playlistDetailsAPI.getPlaylistDetails(id)))
    }.catch {
        emit(Result.failure(RuntimeException("Something went wrong")))
    }
}
