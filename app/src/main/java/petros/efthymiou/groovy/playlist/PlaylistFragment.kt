package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.view.*
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.viewModel.PlaylistViewModel
import petros.efthymiou.groovy.playlist.viewModel.PlaylistViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setUpViewModel()

        observeLoader()
        observePlaylists(view)

        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                false -> loader.visibility = View.GONE
            }
        }
    }

    private fun observePlaylists(view: View) {
        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            playlists.getOrNull()?.let { list ->
                setUpList(view.playlists_list, list)
            }
        }
    }

    private fun setUpList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                val action = PlaylistFragmentDirections
                    .actionPlaylistFragmentToPlaylistDetailsFragment(id)

                findNavController().navigate(action)
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(PlaylistViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}