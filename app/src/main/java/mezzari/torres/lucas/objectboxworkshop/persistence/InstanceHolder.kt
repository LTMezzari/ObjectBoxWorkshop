package mezzari.torres.lucas.objectboxworkshop.persistence

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 *
 * Internal singleton that holds object instances
 */
internal object InstanceHolder {
    //Declares and initialize the holder
    val holder: HashMap<String, Any> = HashMap()
}