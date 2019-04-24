package mezzari.torres.lucas.objectboxworkshop.flow.main

import android.arch.lifecycle.MutableLiveData
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.model.Person

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
class MainActivityViewModel {

    //TODO: Declares a lazy persons box

    //Declares a mutable constant to deal with error messages
    val errorId: MutableLiveData<Int> = MutableLiveData()
    //Declares a variable to hold the edited person id
    var personId: Long = 0

    //TODO: getPersons

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

                //TODO: Put the object in the database
                //If the person`s id is set in the database, it will update

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
            //TODO: Remove the items from the box
        }
    }
}