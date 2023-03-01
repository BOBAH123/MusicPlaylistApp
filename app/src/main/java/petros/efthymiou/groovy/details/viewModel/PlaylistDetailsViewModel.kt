package petros.efthymiou.groovy.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import petros.efthymiou.groovy.details.models.PlaylistDetails
import petros.efthymiou.groovy.details.repository.PlaylistDetailsRepository

class PlaylistDetailsViewModel(
    private val repository: PlaylistDetailsRepository
) : ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()
    val loader = MutableLiveData<Boolean>()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            repository.fetchPlaylistDetails(id)
                .onEach {
                    loader.postValue(false)
                }.collect {
                    playlistDetails.postValue(it)
                }
        }
    }

}
