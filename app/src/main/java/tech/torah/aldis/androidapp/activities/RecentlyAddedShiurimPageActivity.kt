package tech.torah.aldis.androidapp.activities

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import tech.torah.aldis.androidapp.R
import tech.torah.aldis.androidapp.ShiurAdapter
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TorahFilterable
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.ShiurFullPage
import tech.torah.aldis.androidapp.dataClassesAndInterfaces.TabType
import tech.torah.aldis.androidapp.fragments.ShiurOptionsBottomSheetDialogFragment
import tech.torah.aldis.androidapp.fragments.SortOrFilterDialog
import java.util.*

private lateinit var listOfSpeakerNames: MutableList<String>
private lateinit var listOfCategoryNames: MutableList<String>
private lateinit var listOfSeriesNames: MutableList<String>
private const val TAG = "RecentlyAddedShiurimPageActivity"

class RecentlyAddedShiurimPageActivity : AppCompatActivity(), TorahFilterable {
    private lateinit var shiurAdapter: ShiurAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plain_recycler_view_layout)

        val recyclerView: RecyclerView? = findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val listOfShiurim = mutableListOf(
            ShiurFullPage(speaker = "Rabbi Yehuda Ades"),
            ShiurFullPage(speaker = "Rabbi Gedaliah Anemer"),
            ShiurFullPage(speaker = "Rabbi David Ashear"),
            ShiurFullPage(speaker = "Rabbi Baruch Shalom HaLevi Ashlag"),
            ShiurFullPage(speaker = "Rabbi Shmuel Auerbach"),
            ShiurFullPage(speaker = "Rabbi Elkanah Austern"),
            ShiurFullPage(speaker = "Rabbi Stephen Baars"),
            ShiurFullPage(speaker = "Rabbi Shmuel Eliezer Baddiel"),
            ShiurFullPage(speaker = "Rabbi Yudi Bakst"),
            ShiurFullPage(speaker = "Rabbi Chaim Balter"),
            ShiurFullPage(speaker = "Rabbi Yitzchok Basser"),
            ShiurFullPage(speaker = "Rabbi David Ashear"),
            ShiurFullPage(speaker = "Rabbi Baruch Shalom HaLevi Ashlag"),
            ShiurFullPage(speaker = "Rabbi Shmuel Auerbach"),
            ShiurFullPage(speaker = "Rabbi Elkanah Austern"),
            ShiurFullPage(speaker = "Rabbi Stephen Baars"),
            ShiurFullPage(speaker = "Rabbi Shmuel Eliezer Baddiel"),
            ShiurFullPage(speaker = "Rabbi Yudi Bakst"),
            ShiurFullPage(speaker = "Rabbi Chaim Balter"),
            ShiurFullPage(speaker = "Rabbi Yitzchok Basser"),
            ShiurFullPage(speaker = "Rabbi David Ashear"),
            ShiurFullPage(speaker = "Rabbi Baruch Shalom HaLevi Ashlag"),
            ShiurFullPage(speaker = "Rabbi Shmuel Auerbach"),
            ShiurFullPage(speaker = "Rabbi Elkanah Austern"),
            ShiurFullPage(speaker = "Rabbi Stephen Baars"),
            ShiurFullPage(speaker = "Rabbi Shmuel Eliezer Baddiel"),
            ShiurFullPage(speaker = "Rabbi Yudi Bakst"),
            ShiurFullPage(speaker = "Rabbi Chaim Balter"),
            ShiurFullPage(speaker = "Rabbi Yitzchok Basser"),
            ShiurFullPage(speaker = "Rabbi David Ashear"),
            ShiurFullPage(speaker = "Rabbi Baruch Shalom HaLevi Ashlag"),
            ShiurFullPage(speaker = "Rabbi Shmuel Auerbach"),
            ShiurFullPage(speaker = "Rabbi Elkanah Austern"),
            ShiurFullPage(speaker = "Rabbi Stephen Baars"),
            ShiurFullPage(speaker = "Rabbi Shmuel Eliezer Baddiel"),
            ShiurFullPage(speaker = "Rabbi Yudi Bakst"),
            ShiurFullPage(speaker = "Rabbi Chaim Balter"),
            ShiurFullPage(speaker = "Rabbi Yitzchok Basser"),
            ShiurFullPage(speaker = "Rabbi David Ashear"),
            ShiurFullPage(speaker = "Rabbi Baruch Shalom HaLevi Ashlag"),
            ShiurFullPage(speaker = "Rabbi Shmuel Auerbach"),
            ShiurFullPage(speaker = "Rabbi Elkanah Austern"),
            ShiurFullPage(speaker = "Rabbi Stephen Baars"),
            ShiurFullPage(speaker = "Rabbi Shmuel Eliezer Baddiel"),
            ShiurFullPage(speaker = "Rabbi Yudi Bakst"),
            ShiurFullPage(speaker = "Rabbi Chaim Balter"),
            ShiurFullPage(speaker = "Rabbi Yitzchok Basser"),
            ShiurFullPage(speaker = "Rabbi Mordechai Becher"),
            ShiurFullPage(speaker = "Rabbi Yosef Bechhofer"),
            ShiurFullPage(speaker = "Rabbi Ian Beider"),
            ShiurFullPage(speaker = "Rabbi Berel Bell"),
            ShiurFullPage(speaker = "Rabbi Yisroel Belsky"),
            ShiurFullPage(speaker = "Rabbi Yaakov Bender"),
            ShiurFullPage(speaker = "Rabbi Yosef Berger"),
            ShiurFullPage(speaker = "Rabbi Motty Berger"),
            ShiurFullPage(speaker = "Rabbi Michael Berger"),
            ShiurFullPage(speaker = "Rabbi Moshe Bergman"),
            ShiurFullPage(speaker = "Rabbi Yitzchak Berkovits"),
            ShiurFullPage(speaker = "Rabbi Tzvi Berkowitz"),
            ShiurFullPage(speaker = "Rabbi Yitzchak Berkowitz"),
        )
        listOfSpeakerNames = mutableListOf()
        listOfSeriesNames = mutableListOf()
        listOfCategoryNames = mutableListOf()

        for (shiur in listOfShiurim) {
            listOfSpeakerNames.add(shiur.speaker)
            listOfSeriesNames.add(shiur.series)
            listOfCategoryNames.add(shiur.category)
        }
        shiurAdapter = ShiurAdapter(listOfShiurim)
        recyclerView?.adapter = shiurAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.downloads_favorites_history_pages_menu, menu)
        val filterItem: MenuItem = menu!!.findItem(R.id.filter_button)
        filterItem.setOnMenuItemClickListener {
            SortOrFilterDialog(
                this,
                listOfSpeakerNames.toList(),
                listOfCategoryNames.toList(),
                listOfSeriesNames.toList()
            )
                // I figure that it is worth the cost of passing new objects to the sort dialog to avoid the cost of
                // eventual bugs due to passing in a reference to a mutable list
                .show(supportFragmentManager, TAG)
            true
        }
        return true
    }

    override fun callbackFilter(tabType: TabType, data: String) {
        if (tabType == TabType.ALL) shiurAdapter.reset()
        else shiurAdapter.filter(tabType, data)
    }

    fun openOptionsMenu(v: View): Unit {
        ShiurOptionsBottomSheetDialogFragment().apply {
            show(supportFragmentManager, tag)
        }
    }
}

