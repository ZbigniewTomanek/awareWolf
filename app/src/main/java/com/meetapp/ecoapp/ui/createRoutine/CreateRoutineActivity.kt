package com.meetapp.ecoapp.ui.createRoutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.AppDatabase
import com.meetapp.ecoapp.database.entities.Frequency
import com.meetapp.ecoapp.database.entities.Resource
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin
import com.meetapp.ecoapp.utils.Constants
import kotlinx.android.synthetic.main.activity_create_routine.*
import org.jetbrains.anko.doAsync

class CreateRoutineActivity : AppCompatActivity() {

    var routine: Routine? = null
    lateinit var instance: AppDatabase
    lateinit var resources: List<Resource>
    lateinit var frequencies: List<Frequency>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_routine)

        val adapterR = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources.map { it.resourceName })
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_resources.adapter = adapterR

        val adapterF = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequencies.map { it.name })
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_frequency.adapter = adapterF

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val routine: Routine = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.routine = routine
            prePopulateData(routine)
        }

    }

    private fun prePopulateData(routine: Routine) {
        et_routine_title.setText(routine.routineName)
        et_routine_amount.setText(routine.amountSaved.toString())

        val resource = listOf<String>() // TODO zassać tą listę6
        if (resource.isNotEmpty()){
            sp_resources.setSelection(resources.withIndex().filter { it.value.resourceName == resource[0] }.map { it.index }[0])
        }

        val fName = AppDatabase.getInstance(this).frequencyDao().loadAllByIds(arrayOf(routine.freqId))[0]
        sp_frequency.setSelection(frequencies.withIndex().filter { it.value == fName }.map { it.index }[0])
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


            doAsync {
                val inst =  instance
                inst.routineDao().insert(rout)

                val routineId = inst.routineDao().findByName(routine!!.routineName)
                inst.routineResourceJoinDao().insert(RoutineResourceJoin(routineId.routineId!!, resources[sp_resources.selectedItemPosition].resourceId))
            }


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

        if (!sp_resources.isSelected) {
            Toast.makeText(this, getString(R.string.please_select_category), Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
