package mezzari.torres.lucas.objectboxworkshop.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 *
 * Class that declares the Entity Person
 */
@Entity
class Person {

    @Id
    var id: Long = 0

    var name: String? = null

    var age: Int = 0

    var isChecked: Boolean = false
}