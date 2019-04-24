package mezzari.torres.lucas.objectboxworkshop.flow.filter

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_filter.*
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.annotation.LayoutReference
import mezzari.torres.lucas.objectboxworkshop.generic.BaseActivity
import mezzari.torres.lucas.objectboxworkshop.model.Person
import mezzari.torres.lucas.objectboxworkshop.persistence.Wrapper

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 22/04/2019
 */
@LayoutReference(R.layout.activity_filter)
class FilterActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {

    //Declares a lazy view model
    private val viewModel: FilterActivityViewModel by lazy {
        FilterActivityViewModel()
    }

    override fun onInitValues() {
        //Set home button enabled
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Set the checked change listeners on the checkboxes
        cbId.setOnCheckedChangeListener(this)
        cbName.setOnCheckedChangeListener(this)
        cbAge.setOnCheckedChangeListener(this)
        cbPeriod.setOnCheckedChangeListener(this)

        //Set the text change listeners
        //Set the check boxes enabled and checked if the texts are not empty
        addDefaultListener(etId) {
            cbId.isChecked = it.toString().isNotEmpty()
            cbId.isEnabled = it.toString().isNotEmpty()
        }
        addDefaultListener(etName) {
            cbName.isChecked = it.toString().isNotEmpty()
            cbName.isEnabled = it.toString().isNotEmpty()
        }
        addDefaultListener(etAge) {
            val isChecked = it.toString().isNotEmpty()
            cbAge.isChecked = isChecked
            cbAge.isEnabled = isChecked

            //If isChecked is true
            if (isChecked) {
                //Uncheck the period and clear the period edit texts
                cbPeriod.isChecked = false
                etPeriodStart.text = null
                etPeriodEnd.text = null
            }
        }
        addDefaultListener(etPeriodStart) {
            val isChecked = it.toString().isNotEmpty() && etPeriodEnd.text.toString().isNotEmpty()
            cbPeriod.isChecked = isChecked
            cbPeriod.isEnabled = isChecked

            //If isChecked is true
            if (isChecked) {
                //Uncheck the age and clear the age edit text
                cbAge.isChecked = false
                etAge.text = null
            }
        }
        addDefaultListener(etPeriodEnd) {
            val isChecked = it.toString().isNotEmpty() && etPeriodStart.text.toString().isNotEmpty()
            cbPeriod.isChecked = isChecked
            cbPeriod.isEnabled = isChecked

            //If isChecked is true
            if (isChecked) {
                //Uncheck the age and clear the age edit text
                cbAge.isChecked = false
                etAge.text = null
            }
        }

        //Set the button click listener
        btnFilter.setOnClickListener {
            //Verify the contents and cast to the expected type
            val id: Long = if (etId.text.toString().isEmpty()) 0 else etId.text.toString().toLong()
            val name: String = etName.text.toString()
            val age: Int = if (etAge.text.toString().isEmpty()) 0 else etAge.text.toString().toInt()
            val periodStart: Int = if (etPeriodStart.text.toString().isEmpty()) 0 else etPeriodStart.text.toString().toInt()
            val periodEnd: Int = if (etPeriodEnd.text.toString().isEmpty()) 0 else etPeriodEnd.text.toString().toInt()

            //Call the filter method of the view model
            //TODO: uncomment
//            viewModel.filter(id, name, age, periodStart, periodEnd) { persons ->
//                //When the method succeeds
//                //Set the result to ok, and wrap the list of persons filtered
//                setResult(Activity.RESULT_OK, object: Wrapper() {
//                    val persons: List<Person> = persons
//                })
//                //Finish the activity
//                finish()
//            }
        }
    }

    /**
     * Method that adds a TextWatcher to the given edit text
     *
     * @param editText The edit text witch will receive the listener
     * @param logic A closure that will be invoked in the afterTextChanged
     */
    private fun addDefaultListener(editText: EditText, logic: (s: Editable) -> Unit) {
        //Add a Anonymous TextWatcher to the given edit text
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Unwrap the editable
                s?.let {
                    //Invoke the closure passing the editable unwrapped
                    logic(it)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        //Ignores if the isChecked is true
        if (isChecked) return

        //Switch the buttonView id
        when (buttonView?.id) {
            R.id.cbId -> {
                //Clear the text in the identifier edit text
                etId.text = null
            }
            R.id.cbName -> {
                //Clear the text in the name edit text
                etName.text = null
            }
            R.id.cbAge -> {
                //Clear the text in the age edit text
                etAge.text = null
            }
            R.id.cbPeriod -> {
                //Clear the text in the start period edit text
                etPeriodStart.text = null
                //Clear the text in the end period edit text
                etPeriodEnd.text = null
            }
        }
    }
}
