package mezzari.torres.lucas.objectboxworkshop.flow.filter

import io.objectbox.Box
import io.objectbox.kotlin.between
import io.objectbox.kotlin.equal
import io.objectbox.query.QueryBuilder
import mezzari.torres.lucas.objectboxworkshop.model.Person_
import mezzari.torres.lucas.objectboxworkshop.util.BoxUtils
import mezzari.torres.lucas.objectboxworkshop.model.Person

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 22/04/19
 */
class FilterActivityViewModel {

    //Declares a lazy persons box
    private val personsBox: Box<Person> by lazy { BoxUtils.getBox<Person>() }

    /**
     * Method that filters the persons with the given parameters
     *
     * @param id The Person identifier
     * @param name The Person name
     * @param age The Person age
     * @param periodStart The starting age
     * @param periodEnd The ending age
     * @param success The closure callback of success
     */
    fun filter(id: Long, name: String, age: Int, periodStart: Int, periodEnd: Int, success: (List<Person>) -> Unit) {
        //Declares a QueryBuilder
        val query: QueryBuilder<Person> = personsBox.query()

        //If the identifier is greater than 0, filter for them
        if (id > 0) {
            //Defines that the property id should be equal to the given parameter
            query.equal(Person_.id, id)
        } else {
            //If the name is not empty, filter if the property name contains the given name parameter
            if (name.isNotEmpty()) {
                query.contains(Person_.name, name)
            }

            //If given age is greater than 0 is equal to the age property
            if (age > 0) {
                query.equal(Person_.age, age)
            } else if (periodStart > 0 && periodEnd > 0) {
                //If the periodStart and End is greater than 0, search the persons with the age property between the period
                query.between(Person_.age, periodStart, periodEnd)
            }
        }

        //Build the query and find the items
        success(query.build().find())
    }
}