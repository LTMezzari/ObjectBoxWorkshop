package mezzari.torres.lucas.objectboxworkshop.flow.filter

import android.app.Activity
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
class FilterActivity : BaseActivity() {

    private val viewModel: FilterActivityViewModel by lazy {
        FilterActivityViewModel()
    }

    override fun onInitValues() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnFilter.setOnClickListener {
            val id: Long = if (etId.text.toString().isEmpty()) 0 else etId.text.toString().toLong()
            val name: String = etName.text.toString()
            val age: Int = if (etAge.text.toString().isEmpty()) 0 else etAge.text.toString().toInt()

            viewModel.filter(id, name, age) { persons ->
                setResult(Activity.RESULT_OK, object: Wrapper() {
                    val persons: List<Person> = persons
                })
                finish()
            }
        }
    }
}
