package petros.efthymiou.groovy.details.repository

import kotlinx.coroutines.flow.Flow
import petros.efthymiou.groovy.details.models.PlaylistDetails
import petros.efthymiou.groovy.details.service.PlaylistDetailsService
import javax.inject.Inject

class PlaylistDetailsRepository @Inject constructor(
    private val service: PlaylistDetailsService
) {
    suspend fun fetchPlaylistDetails(
        id: String
    ): Flow<Result<PlaylistDetails>> = service.fetchPlaylistDetails(id)
}