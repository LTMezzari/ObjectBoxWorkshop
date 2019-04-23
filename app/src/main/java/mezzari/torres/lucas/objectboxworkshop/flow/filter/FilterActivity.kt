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

    private val viewModel: FilterActivityViewModel by lazy {
        FilterActivityViewModel()
    }

    override fun onInitValues() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cbId.setOnCheckedChangeListener(this)
        cbName.setOnCheckedChangeListener(this)
        cbAge.setOnCheckedChangeListener(this)
        cbPeriod.setOnCheckedChangeListener(this)

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

            if (isChecked) {
                cbPeriod.isChecked = false
                etPeriodStart.text = null
                etPeriodEnd.text = null
            }
        }
        addDefaultListener(etPeriodStart) {
            val isChecked = it.toString().isNotEmpty() && etPeriodEnd.text.toString().isNotEmpty()
            cbPeriod.isChecked = isChecked
            cbPeriod.isEnabled = isChecked

            if (isChecked) {
                cbAge.isChecked = false
                etAge.text = null
            }
        }
        addDefaultListener(etPeriodEnd) {
            val isChecked = it.toString().isNotEmpty() && etPeriodStart.text.toString().isNotEmpty()
            cbPeriod.isChecked = isChecked
            cbPeriod.isEnabled = isChecked

            if (isChecked) {
                cbAge.isChecked = false
                etAge.text = null
            }
        }

        btnFilter.setOnClickListener {
            val id: Long = if (etId.text.toString().isEmpty()) 0 else etId.text.toString().toLong()
            val name: String = etName.text.toString()
            val age: Int = if (etAge.text.toString().isEmpty()) 0 else etAge.text.toString().toInt()
            val periodStart: Int = if (etPeriodStart.text.toString().isEmpty()) 0 else etPeriodStart.text.toString().toInt()
            val periodEnd: Int = if (etPeriodEnd.text.toString().isEmpty()) 0 else etPeriodEnd.text.toString().toInt()

            viewModel.filter(id, name, age, periodStart, periodEnd) { persons ->
                setResult(Activity.RESULT_OK, object: Wrapper() {
                    val persons: List<Person> = persons
                })
                finish()
            }
        }
    }

    private fun addDefaultListener(editText: EditText, logic: (s: Editable) -> Unit) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    logic(it)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) return

        when (buttonView?.id) {
            R.id.cbId -> {
                etId.text = null
            }
            R.id.cbName -> {
                etName.text = null
            }
            R.id.cbAge -> {
                etAge.text = null
            }
            R.id.cbPeriod -> {
                etPeriodStart.text = null
                etPeriodEnd.text = null
            }
        }
    }
}
