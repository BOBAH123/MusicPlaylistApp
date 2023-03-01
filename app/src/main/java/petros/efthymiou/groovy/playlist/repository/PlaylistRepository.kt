package petros.efthymiou.groovy.playlist.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.mappers.PlaylistMapper
import petros.efthymiou.groovy.playlist.service.PlaylistService
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val service: PlaylistService,
    private val mapper: PlaylistMapper
) {
    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists().map { result ->
            if (result.isSuccess)
                Result.success(mapper(result.getOrNull()!!))
            else
                Result.failure(result.exceptionOrNull()!!)
        }
}
