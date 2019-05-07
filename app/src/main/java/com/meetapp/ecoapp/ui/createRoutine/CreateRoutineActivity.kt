package com.meetapp.ecoapp.ui.createRoutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.AppDatabase
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Frequency
import com.meetapp.ecoapp.database.entities.Resource
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin
import com.meetapp.ecoapp.utils.Constants
import com.meetapp.ecoapp.utils.Tools.makeToast
import kotlinx.android.synthetic.main.activity_create_routine.*
import org.jetbrains.anko.doAsync

class CreateRoutineActivity : AppCompatActivity() {

    var routine: Routine? = null
    lateinit var resources: List<Resource>
    lateinit var frequencies: List<Frequency>
    val repository = RoutineRepository.instance()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_routine)

        resources = repository.getAllResources()
        frequencies = repository.getAllFrequenciesList()

        val adapterR = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources.map { it.resourceName })
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_resources.adapter = adapterR

        val adapterF = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequencies.map { it.name })
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_frequency.adapter = adapterF

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            edit = true
            val routine: Routine = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.routine = routine
            prePopulateData(routine)
        }

    }

    private fun prePopulateData(routine: Routine) {
        et_routine_title.setText(routine.routineName)
        et_routine_amount.setText(routine.amountSaved.toString())

        val resource = repository.getResourcesNames(routine.routineId!!)
        if (resource.isNotEmpty()){
            sp_resources.setSelection(resources.withIndex().filter { it.value.resourceName == resource[0] }.map { it.index }[0])
        }

        val fName = repository.getFrequencyName(routine.freqId)
        sp_frequency.setSelection(frequencies.withIndex().filter { it.value.name == fName }.map { it.index }[0])
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                saveRoutine()
            }
        }
        return true
    }

    private fun saveRoutine() {
        if (validateFields()) {
            val id = if (routine != null) routine?.routineId else null
            val rout = Routine(id, et_routine_title.text.toString(),
                frequencies[sp_frequency.selectedItemPosition].freqId, et_routine_amount.text.toString().toDouble())

            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, rout)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (et_routine_title.text.isEmpty()) {
            et_routine_title.error = getString(R.string.please_enter_title)
            et_routine_title.requestFocus()
            return false
        }
        if (et_routine_amount.text.isEmpty()) {
            et_routine_amount.error = getString(R.string.please_enter_amount)
            et_routine_amount.requestFocus()
            return false
        }

        if (!sp_resources.isSelected && !edit) {
            makeToast(getString(R.string.please_select_category), this)
            return false
        }

        if (!sp_frequency.isSelected && !edit) {
            makeToast(getString(R.string.please_select_frequency), this)
            return false
        }
        return true
    }
}
