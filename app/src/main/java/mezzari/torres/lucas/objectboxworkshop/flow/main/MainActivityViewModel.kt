package mezzari.torres.lucas.objectboxworkshop.flow.main

import io.objectbox.Box
import mezzari.torres.lucas.objectboxworkshop.model.Person
import mezzari.torres.lucas.objectboxworkshop.util.BoxUtils

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
class MainActivityViewModel {

    private val personsBox: Box<Person>? by lazy { BoxUtils.getBox<Person>(Person::class) }
}