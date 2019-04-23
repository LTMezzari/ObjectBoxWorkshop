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

    private val personsBox: Box<Person> by lazy { BoxUtils.getBox<Person>() }

    fun filter(id: Long, name: String, age: Int, periodStart: Int, periodEnd: Int, success: (List<Person>) -> Unit) {
        val query: QueryBuilder<Person> = personsBox.query()

        if (id > 0) {
            query.equal(Person_.id, id)
        } else {
            if (!name.isEmpty()) {
                query.contains(Person_.name, name)
            }

            if (age > 0) {
                query.equal(Person_.age, age)
            } else if (periodStart > 0 && periodEnd > 0) {
                query.between(Person_.age, periodStart, periodEnd)
            }
        }

        success(query.build().find())
    }
}