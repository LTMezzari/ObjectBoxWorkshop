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

    private val personsBox: Box<Person> by lazy { BoxUtils.getBox<Person>() }

    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorId: MutableLiveData<Int> = MutableLiveData()
    var personId: Long = 0

    fun getPersons(): List<Person> {
        return personsBox.all
    }

    fun savePerson(name: String, age: Int, isEditing: Boolean, success: (Person) -> Unit) {
        when {
            name.isEmpty() -> {
                errorId.value = R.string.empty_name
            }
            age <= 0 -> {
                errorId.value = R.string.invalid_age
            }
            else -> {
                val person: Person = Person()
                person.name = name
                person.age = age
                
                if (isEditing)
                    person.id = personId
    
                personsBox.put(person)
    
                success(person)
            }
        }
    }
}