package mezzari.torres.lucas.objectboxworkshop.flow.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
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

    //Declares a lazy view model
    private val viewModel: MainActivityViewModel by lazy {
        MainActivityViewModel()
    }

    //Declares a lazy adapter for the recycler view
    private val adapter: PersonsAdapter by lazy {
        PersonsAdapter(this).apply {
            //Set the on click closure of the adapter
            onPersonPersonClick = {
                //Change the title to edit
                tvTitle.setText(R.string.edit_person)
                //Set the person name to the field
                etName.setText(it.name)
                //Set the age to the field
                etAge.setText(it.age.toString())
                //Set the identifier to the viewModel
                viewModel.personId = it.id

                //Set isEditing to true
                isEditing = true
            }
            //Set the checked change listener
            onPersonCheckedChangeListener = {  shouldShow ->
                //Call the method to handle the delete button
                handleDeleteMenuItem(shouldShow)
            }
        }
    }

    //Declares a boolean to verify if it is editing or not
    private var isEditing = false
    //Declares a nullable menu to manage the menu options
    private var menu: Menu? = null

    override fun onInitValues() {
        //Set a observable in the error id variable
        viewModel.errorId.observe(this, Observer {wrapped ->
            //Unwrap the variable
            wrapped?.let {
                //Show a toast with the error id
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        //Set the layout manager in the recycler
        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //Set the adapter to the recycler
        rvItems.adapter = adapter

        //TODO: Set the adapter items


        //Set the save button click listener
        btnSave.setOnClickListener {
            //Get the name and age and cast to the needed types
            val name: String = etName.text.toString()
            val age: Int = if (etAge.text.toString().isEmpty()) 0 else etAge.text.toString().toInt()

            //Call the save method of the view model
            viewModel.savePerson(name, age, isEditing) { person ->
                //Check if is editing
                if (isEditing) {
                    //Change the title back to add
                    tvTitle.setText(R.string.add_person)
                    //Set isEditing to false
                    isEditing = false
                    //Call the adapter update method
                    adapter.update(person)
                } else {
                    //call the adapter add method
                    adapter.add(person)
                }
                //Clear the fields
                etName.text = null
                etAge.text = null
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Verify the request code
        when (requestCode) {
            FILTER_REQUEST -> {
                //Check if the result is ok
                if (resultCode == Activity.RESULT_OK) {
                    //Handle the menu
                    handleFilterMenuItem(true)
                    //Set the filtered persons
                    adapter.setItems(getInstance("persons"))
                }
            }
            else -> {
                //Call super method
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Set the activity menu
        this.menu = menu
        //Inflate the custom menu
        menuInflater.inflate(R.menu.toolbar, menu)
        //return true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Verify the menu item id
        when (item?.itemId) {
            R.id.delete -> {
                //Get the marked persons from the adapter
                val marked = adapter.getMarkedPersons()
                //Delete them from the database
                viewModel.deletePersons(marked)
                //Delete them from the adapter
                adapter.deletePersons(marked)
                //Turn the selection mode off
                adapter.turnSelectionModeOff()
                //return true
                return true
            }
            R.id.filter -> {
                //Start the filter activity expecting a result
                startActivityForResult(FilterActivity::class, FILTER_REQUEST)
                //return true
                return true
            }
            R.id.close -> {
                //Handle the menu
                handleFilterMenuItem(false)
                //TODO: Set the saved items from the box to the adapter

                //return true
                return true
            }
            else -> {
                //return the super method
                return super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Method that handles the filter and close menu item
     *
     * @param isFiltering The boolean that indicates witch should be shown. True if the close menuItem should be showing, False if the filter
     */
    private fun handleFilterMenuItem(isFiltering: Boolean) {
        //Unwrap the menu
        menu?.let {
            //Hide the close button if it is not filtering
            it.findItem(R.id.close)?.isVisible = isFiltering
            //Show the filter button if it is not filtering
            it.findItem(R.id.filter)?.isVisible = !isFiltering
        }
    }

    /**
     * Method that handles the Delete Menu Item from the toolbar
     *
     * @param b A boolean that indicates the needed visibility. True if it should be visible
     */
    private fun handleDeleteMenuItem(b: Boolean) {
        //Unwrap the menu
        menu?.let {
            //Get the delete menu item
            val menuItem: MenuItem? = it.findItem(R.id.delete)
            //Get the current visibility state from the menu item
            val visibility: Boolean? = menuItem?.isVisible
            //Verify if the visibility state is already set
            if (b != visibility) {
                //Change its visibility
                menuItem?.isVisible = b
            }
        }
    }

    override fun onBackPressed() {
        //Verify if the adapter is in selection mode
        if (adapter.isInSelectionMode) {
            //Turn the selection mode of
            adapter.turnSelectionModeOff()
        } else {
            //Call super method
            super.onBackPressed()
        }
    }

    companion object {
        const val FILTER_REQUEST: Int = 0
    }
}
