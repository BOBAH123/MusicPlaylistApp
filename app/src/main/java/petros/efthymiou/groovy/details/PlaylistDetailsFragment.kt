package petros.efthymiou.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_details.*
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.details.models.PlaylistDetails
import petros.efthymiou.groovy.details.viewModel.PlaylistDetailsViewModel
import petros.efthymiou.groovy.details.viewModel.PlaylistDetailsViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    private val args: PlaylistDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_details, container, false)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(PlaylistDetailsViewModel::class.java)
        viewModel.getPlaylistDetails(args.playlistId)

        observeDetails()
        observeLoader()

        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> details_loader.visibility = View.VISIBLE
                false -> details_loader.visibility = View.GONE
            }
        }
    }

    private fun observeDetails() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                Snackbar.make(
                    playlist_details_root,
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailsFragment()
    }
}