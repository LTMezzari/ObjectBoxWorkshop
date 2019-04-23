package mezzari.torres.lucas.objectboxworkshop.util

import android.app.Application
import io.objectbox.Box
import io.objectbox.BoxStore
import mezzari.torres.lucas.objectboxworkshop.model.MyObjectBox
import java.lang.RuntimeException
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
object BoxUtils {

    private var boxStore: BoxStore? = null

    inline fun <reified T> getBox() = getBox<T>(T::class)

    fun initialize(application: Application) {
        if (boxStore == null)
            boxStore = MyObjectBox.builder().androidContext(application).build()
    }

    fun <T>getBox(kClass: KClass<*>): Box<T> {
        if (boxStore == null)
            throw RuntimeException("You should initialize the BoxUtils")

        return boxStore?.boxFor(kClass.java) as Box<T>
    }
}