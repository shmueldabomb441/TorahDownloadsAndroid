package tech.torah.aldis.androidapp.adapters.shiurAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import tech.torah.aldis.androidapp.R
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.FunctionLibrary
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.shiurVariants.ShiurFullPage
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TabType
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TorahFilterable
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.shiurVariants.Shiur

private const val TAG = "ShiurAdapter"
class ShiurAdapter(private val originalShiurFullPageList: List<Shiur/*FullPage*/>) :
    RecyclerView.Adapter<ShiurAdapter.ShiurViewHolder>(), FastScroller.SectionIndexer, TorahFilterable {
    //TODO consider making originalShiurFullPageList an immutable set (it never changes and it doesn't need doubles
    private val shiurFullPageList: MutableList<Shiur/*FullPage*/> = originalShiurFullPageList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiurViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.individual_shiur_card_layout, parent, false)
        return ShiurViewHolder(v)
    }

    override fun getItemCount(): Int = shiurFullPageList.size

    override fun onBindViewHolder(holder: ShiurViewHolder, position: Int) =
        holder.bindItem(shiurFullPageList[position])

    override fun getSectionText(position: Int): CharSequence =
        shiurFullPageList[position].baseTitle!!.first().toString()

    override fun filter(
        constraint: String,
        tabType: TabType,
        exactMatch: Boolean) {
        FunctionLibrary.filter(
            constraint,
            originalShiurFullPageList,
            shiurFullPageList,
            this,
            tabType,
            exactMatch
        )
    }

    override fun reset() {
       FunctionLibrary.reset(originalShiurFullPageList,shiurFullPageList, this)
    }

    class ShiurViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(shiurFullPage: Shiur/*FullPage*/) {
            val shiurTitle = itemView.findViewById(R.id.shiur_title) as TextView?
            val shiurSpeaker = itemView.findViewById(R.id.shiur_speaker) as TextView?
                shiurTitle?.text = shiurFullPage.baseTitle
                shiurSpeaker?.text = shiurFullPage.baseSpeaker
        }
    }
}