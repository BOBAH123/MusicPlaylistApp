package petros.efthymiou.groovy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory
    private val repository = PlaylistRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setUpViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                setUpList(view, playlists.getOrNull()!!)
            }
        }

        return view
    }

    private fun setUpList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlists)
        }
    }

    private fun setUpViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}