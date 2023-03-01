package petros.efthymiou.groovy.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.details.repository.PlaylistDetailsRepository
import javax.inject.Inject

class PlaylistDetailsViewModelFactory @Inject constructor(
    val repository: PlaylistDetailsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistDetailsViewModel(repository) as T
    }
}
