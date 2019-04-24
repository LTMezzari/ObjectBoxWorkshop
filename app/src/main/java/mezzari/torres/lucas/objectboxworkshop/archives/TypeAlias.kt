package mezzari.torres.lucas.objectboxworkshop.archives

import mezzari.torres.lucas.objectboxworkshop.model.Person

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 23/04/19
 */

/**
 * Type Alias for the adapter onPersonClick closure
 */
typealias OnPersonClickListener = ((Person) -> Unit)

/**
 * Type Alias for the adapter OnPersonCheckedChange closure
 */
typealias OnPersonCheckedChangeListener = ((isInSelectionMode: Boolean) -> Unit)