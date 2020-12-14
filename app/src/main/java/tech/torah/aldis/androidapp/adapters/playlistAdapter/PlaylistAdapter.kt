package tech.torah.aldis.androidapp.adapters.playlistAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import tech.torah.aldis.androidapp.R
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.Playlist
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TabType
import java.util.*

private const val TAG = "PlaylistAdapter"

class PlaylistAdapter(private val originalListOfPlaylists: List<Playlist>) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private val temporaryListOfPlaylists = originalListOfPlaylists.toMutableList()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val playlistTitle: MaterialTextView
        val playlistSubtitle: MaterialTextView

        init {
            v.setOnClickListener { Log.d("", "Element $adapterPosition clicked.") }
            playlistTitle = v.findViewById(R.id.playlist_title)
            playlistSubtitle = v.findViewById(R.id.playlist_number_of_shiurim)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.individual_playlist_list_item_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("", "Element $position set.")

        viewHolder.playlistTitle.text = originalListOfPlaylists[position].playlistName
        //23 shiurim • 4 completed
        val subtitle =
            "${originalListOfPlaylists[position].totalNumberOfShiurim} shiurim • ${originalListOfPlaylists[position].numberOfCompletedShiurim} completed"
        viewHolder.playlistTitle.text = subtitle
        //for some reason Lint is giving a warning of "Do not concatenate text displayed
        // with setText. Use resource string with placeholders." when I inline the val "subtitle". Not sure why that should be.
    }

    override fun getItemCount(): Int = originalListOfPlaylists.size

    fun filter(tabType: TabType, constraint: String) {
//TODO make filtering more efficient by using indices.
/*
        var completeListIndex = 0
        var filteredListIndex = 0
        while (completeListIndex < originalshiurList.size) {
            val shiur: shiur = originalshiurList[completeListIndex]
            if (shiur.name.toLowerCase(Locale.ROOT).trim().contains(constraint)) {
                if (filteredListIndex < shiurFullPageList.size) {
                    val filter: shiur = shiurFullPageList[filteredListIndex]
                    if (shiur.name != filter.name) {
                        shiurFullPageList.add(filteredListIndex, shiur)
                        notifyItemInserted(filteredListIndex)
                    }
                } else {
                    shiurFullPageList.add(filteredListIndex, shiur)
                    notifyItemInserted(filteredListIndex)
                }
                filteredListIndex++
            } else if (filteredListIndex < shiurFullPageList.size) {
                val filter: shiur = shiurFullPageList[filteredListIndex]
                if (shiur.name==filter.name) {
                    shiurFullPageList.removeAt(filteredListIndex)
                    notifyItemRemoved(filteredListIndex)
                }
            }
            completeListIndex++
        }
*/

        temporaryListOfPlaylists.clear()
        if (constraint.isEmpty()) {
            temporaryListOfPlaylists.addAll(originalListOfPlaylists)
        } else {
/*            val filterPattern = constraint.toLowerCase(Locale.ROOT).trim()
            for (shiur in originalListOfPlaylists) {
                val filterReciever = when (tabType) {
                    TabType.CATEGORY -> shiur.category
                    TabType.SERIES -> shiur.series
                    TabType.SPEAKER -> shiur.speaker
                    else -> ""
                }
                if (filterReciever.toLowerCase(Locale.ROOT) == filterPattern) {
                    temporaryListOfPlaylists.add(shiur)
                }
            }*/
        }
        notifyDataSetChanged()
    }

    fun reset() {
        //TODO make reset more efficient by using indices.
        temporaryListOfPlaylists.clear()
        temporaryListOfPlaylists.addAll(originalListOfPlaylists)
        notifyDataSetChanged()
    }
}