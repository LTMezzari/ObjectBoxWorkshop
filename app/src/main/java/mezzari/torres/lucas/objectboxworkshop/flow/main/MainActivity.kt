package mezzari.torres.lucas.objectboxworkshop.flow.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.adapter.PersonsAdapter
import mezzari.torres.lucas.objectboxworkshop.annotation.LayoutReference
import mezzari.torres.lucas.objectboxworkshop.flow.filter.FilterActivity
import mezzari.torres.lucas.objectboxworkshop.generic.BaseActivity

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 22/04/2019
 */
@LayoutReference(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        MainActivityViewModel()
    }

    private val adapter: PersonsAdapter by lazy {
        PersonsAdapter(this)
    }

    private var isEditing = false
    private var menu: Menu? = null

    override fun onInitValues() {

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.errorId.observe(this, Observer {wrapped ->
            wrapped?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = adapter

        adapter.setItems(viewModel.getPersons())
        adapter.onPersonClick = {
            tvTitle.setText(R.string.edit_person)
            etName.setText(it.name)
            etAge.setText(it.age.toString())
            viewModel.personId = it.id

            isEditing = true
        }

        btnSave.setOnClickListener {
            viewModel.savePerson(etName.text.toString(), etAge.text.toString().toInt(), isEditing) { person ->
                if (isEditing) {
                    tvTitle.setText(R.string.add_person)
                    isEditing = false
                    etName.text = null
                    etAge.text = null
                    adapter.update(person)

                } else {
                    adapter.add(person)
                    etName.text = null
                    etAge.text = null
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILTER_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleMenu(true)
                    adapter.setItems(getInstance("persons"))
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.filter -> {
                startActivityForResult(FilterActivity::class, FILTER_REQUEST)
                return true
            }
            R.id.close -> {
                handleMenu(false)
                adapter.setItems(viewModel.getPersons())
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun handleMenu(isFiltering: Boolean) {
        menu?.findItem(R.id.close)?.isVisible = isFiltering
        menu?.findItem(R.id.filter)?.isVisible = !isFiltering
    }

    companion object {
        const val FILTER_REQUEST: Int = 0
    }
}
