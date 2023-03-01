package petros.efthymiou.groovy.details.api

import petros.efthymiou.groovy.details.models.PlaylistDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailsAPI {

    @GET("playlist-details/{id}")
    suspend fun getPlaylistDetails(@Path("id") id: String): PlaylistDetails
}
