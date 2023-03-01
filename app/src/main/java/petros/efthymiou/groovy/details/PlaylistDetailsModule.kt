package petros.efthymiou.groovy.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import petros.efthymiou.groovy.details.api.PlaylistDetailsAPI
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistDetailsAPI(retrofit: Retrofit): PlaylistDetailsAPI =
        retrofit.create(PlaylistDetailsAPI::class.java)
}