package mezzari.torres.lucas.objectboxworkshop.util

import android.app.Application
import io.objectbox.Box
import io.objectbox.BoxStore
import mezzari.torres.lucas.objectboxworkshop.model.MyObjectBox
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
object BoxUtils {

    private var boxStore: BoxStore? = null

    fun initialize(application: Application) {
        if (boxStore == null)
            boxStore = MyObjectBox.builder().androidContext(application).build()
    }

    fun <T>getBox(kClass: KClass<*>): Box<T>? {
        return boxStore?.let {
            it.boxFor(kClass.java) as Box<T>
        }
    }
}