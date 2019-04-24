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
 *
 * Util class for the ObjectBox library
 */
object BoxUtils {

    //Declares a nullable boxStore
    //This variable is used to retrieve entity boxes
    private var boxStore: BoxStore? = null

    /**
     * Inline function to easily retrieve entity boxes
     *
     * @return The Box for the needed Entity
     */
    inline fun <reified T> getBox() = getBox<T>(T::class)

    /**
     * Method that initializes the box store
     * Should be called on the project Application
     *
     * @param application The project application that configure the box store
     */
    fun initialize(application: Application) {
        //Check id the box store is null
        if (boxStore == null) {
            //Build the box store with the given application
            boxStore = MyObjectBox.builder().androidContext(application).build()
        }
    }

    /**
     * Function that retrieve entity boxes
     *
     * @param kClass The Entity class
     * @throws RuntimeException If the boxStore was not initialized
     * @return The Box for the needed Entity
     */
    fun <T>getBox(kClass: KClass<*>): Box<T> {
        //Safe unwrap the box store
        boxStore?.run {
            //Return the casted box
            return this.boxFor(kClass.java) as Box<T>
        } ?: throw RuntimeException("You should initialize the BoxUtils")
    }
}