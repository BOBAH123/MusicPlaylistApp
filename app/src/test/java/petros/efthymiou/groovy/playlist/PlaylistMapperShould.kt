package petros.efthymiou.groovy.playlist

import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistMapperShould : BaseUnitTest() {

    private val mapper = PlaylistMapper()
    private val playlistRaw = PlaylistRaw("1", "name", "category")
    private val playlistRawRock = PlaylistRaw("1", "name", "rock")
    private val playlists = mapper(listOf(playlistRaw))
    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun matchPlaylistId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun matchPlaylistName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun matchPlaylistCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapDefaultImageWhenCategoryIsRock() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}