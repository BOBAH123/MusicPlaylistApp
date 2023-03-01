package petros.efthymiou.groovy.playlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.playlist.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistViewModelFactory @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(repository) as T
    }
}
