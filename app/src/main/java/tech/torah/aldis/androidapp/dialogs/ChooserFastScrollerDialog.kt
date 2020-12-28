package tech.torah.aldis.androidapp.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.l4digital.fastscroll.FastScrollView
import com.l4digital.fastscroll.FastScroller
import tech.torah.aldis.androidapp.R
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TabType
import java.util.*

private lateinit var selectedListItemTextView: MaterialTextView
private lateinit var fastScrollerSelectButton: MaterialButton
private lateinit var fastScrollerCancelButton: MaterialButton
private lateinit var fastScrollerDeselectButton: MaterialButton

private const val TAG = "ChooserFastScrollerDialog"
class ChooserFastScrollerDialog(
    private val listItems: List<String>,
    private val tabTypeBeingDisplayed: TabType,
    private val individualSpeakerCategorySeriesChooserAutoCompleteTextView: AutoCompleteTextView
) :
    DialogFragment() {
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var selectedListItem: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = true
        setHasOptionsMenu(true)
        val view: View =
            inflater.inflate(R.layout.fast_scroll_recycler_dialog_layout, container, false)
        selectedListItemTextView = view.findViewById(R.id.selected_list_item)
        toolbar = view.findViewById(R.id.custom_dialog_layout_toolbar)
        fastScrollerCancelButton = view.findViewById(R.id.fast_scroller_cancel_button)
        fastScrollerDeselectButton = view.findViewById(R.id.fast_scroller_deselect_button)
        fastScrollerSelectButton = view.findViewById(R.id.fast_scroller_select_button)

        toolbar.inflateMenu(R.menu.speaker_page_menu)
        toolbar.title = resources.getString(tabTypeBeingDisplayed.nameId)

        val menu: Menu = toolbar.menu
        //<editor-fold desc="SearchView decleration">
        val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                itemAdapter.filter(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "Search text changed.")
                itemAdapter.filter(newText ?: "")
                return false
            }
        })
        //</editor-fold>
        fastScrollerCancelButton.setOnClickListener {
            dismiss()
        }
        fastScrollerSelectButton.setOnClickListener {
            individualSpeakerCategorySeriesChooserAutoCompleteTextView.setText(selectedListItem)
            dismiss()
        }
        fastScrollerDeselectButton.setOnClickListener {
            selectedListItemTextView.text = ""
            selectedListItem = ""
            fastScrollerDeselectButton.isEnabled = false
            fastScrollerSelectButton.isEnabled = false
        }
        return view
    }

    override fun getTheme(): Int = R.style.DialogTheme


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: FastScrollView? = view.findViewById(R.id.fast_scroller)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))

        fastScrollerCancelButton = view.findViewById(R.id.fast_scroller_cancel_button)
        fastScrollerDeselectButton = view.findViewById(R.id.fast_scroller_deselect_button)
        fastScrollerSelectButton = view.findViewById(R.id.fast_scroller_select_button)

        itemAdapter = ItemAdapter(listItems)
        recyclerView?.setAdapter(itemAdapter)

    }

    inner class ItemAdapter(private val listItems: List<String>) :
        RecyclerView.Adapter<ItemAdapter.ViewHolder>(), FastScroller.SectionIndexer {
        val tempListItems = listItems.toMutableList()

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: MaterialTextView = view.findViewById(R.id.text_view)

            init {

                view.setOnClickListener {

                    Log.d("", "Element $adapterPosition clicked.")
                    fastScrollerDeselectButton.isEnabled = true
                    fastScrollerSelectButton.isEnabled = true
                    tempListItems[adapterPosition].let {
                        selectedListItemTextView.text = it
                        selectedListItem = it
                    }
                }
            }
        }

        override fun getSectionText(position: Int): CharSequence {
            val s = tempListItems[position]
            return s[s.lastIndexOf(' ') + 1].toString()/*if (s.contains("Rabbi"))
                s.substring(s.indexOf(" ") + 1).first().toUpperCase().toString()
            else s.first().toUpperCase().toString()*/
        }


        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.simple_grey_text_view_card, viewGroup, false)
            )

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            Log.d("", "Element $position set.")

            viewHolder.textView.text = tempListItems[position]
        }

        override fun getItemCount(): Int = tempListItems.size
        fun filter(constraint: String) {
            Log.d(TAG, "Filter ran")
/*
        var completeListIndex = 0
        var filteredListIndex = 0
        while (completeListIndex < originalSpeakerList.size) {
            val speaker: Speaker = originalSpeakerList[completeListIndex]
            if (speaker.name.toLowerCase(Locale.ROOT).trim().contains(constraint)) {
                if (filteredListIndex < speakerList.size) {
                    val filter: Speaker = speakerList[filteredListIndex]
                    if (speaker.name != filter.name) {
                        speakerList.add(filteredListIndex, speaker)
                        notifyItemInserted(filteredListIndex)
                    }
                } else {
                    speakerList.add(filteredListIndex, speaker)
                    notifyItemInserted(filteredListIndex)
                }
                filteredListIndex++
            } else if (filteredListIndex < speakerList.size) {
                val filter: Speaker = speakerList[filteredListIndex]
                if (speaker.name==filter.name) {
                    speakerList.removeAt(filteredListIndex)
                    notifyItemRemoved(filteredListIndex)
                }
            }
            completeListIndex++
        }
*/
            tempListItems.clear()
            if (constraint.isEmpty()) {
                tempListItems.addAll(listItems)
            } else {
                val filterPattern = constraint.toLowerCase(Locale.ROOT).trim()
                for (listItem in listItems) {
                    if (listItem.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        tempListItems.add(listItem)
                    }
                }
            }
            notifyDataSetChanged()
        }

    }
}