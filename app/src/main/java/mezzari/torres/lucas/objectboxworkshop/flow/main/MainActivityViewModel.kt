package mezzari.torres.lucas.objectboxworkshop.flow.main

import android.arch.lifecycle.MutableLiveData
import io.objectbox.Box
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.model.Person
import mezzari.torres.lucas.objectboxworkshop.util.BoxUtils

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
class MainActivityViewModel {

    //Declares a lazy persons box
    private val personsBox: Box<Person> by lazy { BoxUtils.getBox<Person>() }

    //Declares a mutable constant to deal with error messages
    val errorId: MutableLiveData<Int> = MutableLiveData()
    //Declares a variable to hold the edited person id
    var personId: Long = 0

    /**
     * Method that get the persons from the database
     *
     * @return All persons from the database
     */
    fun getPersons(): List<Person> {
        //Return the persons
        return personsBox.all
    }

    /**
     * Method that save the person to the database
     *
     * @param name The person name
     * @param age The persons age
     * @param isEditing A boolean that indicates if the id should be added
     * @param success A closure callback for when the person is added
     */
    fun savePerson(name: String, age: Int, isEditing: Boolean, success: (Person) -> Unit) {
        //Do some validations
        when {
            name.isEmpty() -> {
                //Set a error message for when the name is empty
                errorId.value = R.string.empty_name
            }
            age <= 0 -> {
                //Set a error message for when the age is invalid
                errorId.value = R.string.invalid_age
            }
            else -> {
                //Declares a person and set the values
                val person: Person = Person().apply {
                    this.name = name
                    this.age = age

                    //If is editing, add the person id
                    if (isEditing)
                        this.id = personId
                }

                //Put the object in the database
                //If the person`s id is set in the database, it will update
                personsBox.put(person)

                //Invoke the success closure
                success(person)
            }
        }
    }

    /**
     * Method that delete a list of persons from the database
     *
     * @param persons The list of persons to be deleted
     */
    fun deletePersons(persons: List<Person>) {
        //Verify if the list is not empty
        if (persons.isNotEmpty()){
            //Remove the items from the box
            personsBox.remove(persons)
        }
    }
}