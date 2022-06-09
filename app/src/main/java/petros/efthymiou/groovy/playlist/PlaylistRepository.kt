package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists()
}
