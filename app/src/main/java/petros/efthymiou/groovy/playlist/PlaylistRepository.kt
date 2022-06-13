package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val service: PlaylistService
) {
    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists()
}
