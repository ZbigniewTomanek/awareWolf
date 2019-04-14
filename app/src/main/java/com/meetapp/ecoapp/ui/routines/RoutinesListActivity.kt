package com.meetapp.ecoapp.ui.routines



import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.ui.createRoutine.CreateRoutineActivity
import com.meetapp.ecoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_routines_list.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "routineListActivity"
class RoutinesListActivity : AppCompatActivity(), RoutineListAdapter.RoutineEvents {

    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var searchView: SearchView
    private lateinit var routinesAdapter: RoutineListAdapter
    private lateinit var repository: RoutineRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routines_list)

        RoutineRepository.initInstance(application)
        repository = RoutineRepository.instance()

        initList()
    }

    private fun initList() {
        rv_routine_list.layoutManager = LinearLayoutManager(this)

        routinesAdapter = RoutineListAdapter(this)

        rv_routine_list.adapter = routinesAdapter

        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel::class.java)

        routineViewModel.getAllRoutinesList().observe(this, Observer {
            routinesAdapter.setAllRoutineItems(it)
        })


        fab_new_todo.setOnClickListener {
            resetSearchView()
            val intent = Intent(this@RoutinesListActivity, CreateRoutineActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_CREATE_TODO)
        }
    }

    override fun onDeleteClicked(routine: Routine) {
        routineViewModel.deleteRoutine(routine)
    }

    //Callback when user clicks on view note
    override fun onViewClicked(routine: Routine) {
        resetSearchView()
        val intent = Intent(this@RoutinesListActivity, CreateRoutineActivity::class.java)
        intent.putExtra(Constants.INTENT_OBJECT, routine)
        startActivityForResult(intent, Constants.INTENT_UPDATE_TODO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val routine = data?.getParcelableExtra<Routine>(Constants.INTENT_OBJECT)!!
            when (requestCode) {
                Constants.INTENT_CREATE_TODO -> {
                    repository.addRoutine(routine)
                    Log.d(TAG, "Rutine added")
                }
                Constants.INTENT_UPDATE_TODO -> {
                   repository.updateRoutine(routine)
                    Log.d(TAG, "Routine updated")
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_routine)
            ?.actionView as SearchView
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                routinesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               routinesAdapter.filter.filter(newText)
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.search_routine -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        resetSearchView()
        super.onBackPressed()
    }

    private fun resetSearchView() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}
