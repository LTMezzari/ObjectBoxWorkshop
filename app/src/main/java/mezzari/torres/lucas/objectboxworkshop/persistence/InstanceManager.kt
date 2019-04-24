package mezzari.torres.lucas.objectboxworkshop.persistence

import java.lang.reflect.Field

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 *
 * Singleton that manages the InstanceHolder
 */
object InstanceManager {

    /**
     * Method that returns instances from the InstanceHolder
     *
     * @param key The key in witch the instance was saved
     * @param shouldClearInstance Optional param that indicates if the manager should
     * remove the instance from the holder after retrieving. Default is false.
     * @return The casted Instance, or null if a ClassCastException happened, or there is no instance in that key
     */
    fun <T>getInstance(key: String, shouldClearInstance: Boolean = false): T? {
        //Try to retrieve the instance
        try {
            //Retrieve the instance an cast it to the expected type
            val t: T? = InstanceHolder.holder[key] as? T

            //Check if the instance should be removed
            if (shouldClearInstance) {
                //Remove the instance
                removeInstance(key)
            }

            //Return the needed instance
            return t
        } catch (e: ClassCastException) {
            //Return null after a ClassCastException happened
            return null
        }
    }

    /**
     * Method that removes the instance from the InstanceHolder
     *
     * @param key The key to remove
     */
    fun removeInstance(key: String) {
        //Remove the instance in the given key
        InstanceHolder.holder.remove(key)
    }

    /**
     * Method that removes all instances from the InstanceHolder
     */
    fun clearInstances() {
        //Clear all instanced
        InstanceHolder.holder.clear()
    }

    /**
     * Method that saves the DeclaredField from the given object
     *
     * @param wrapperObject The Object from where you want to save the fields
     */
    fun saveInstances(wrapperObject: Any) {
        //Call the saveInstances method passing the object and the object class
        saveInstances(wrapperObject.javaClass, wrapperObject)
    }

    /**
     * Private method that saves the DeclaredField from the given object
     *
     * @param oClass The Class of the given Object
     * @param oInstance The Instance of the given class
     */
    private fun saveInstances(oClass: Class<*>, oInstance: Any) {
        //Get the declared fields from the class
        val fields: Array<Field> = oClass.declaredFields
        //Loops through the fields
        for (field in fields) {
            //Save the fields accessibility
            val isAccessible: Boolean = field.isAccessible
            //Set the fields accessibility to true
            field.isAccessible = true

            //Save the fields instance from the given object
            try {
                //Save the instance
                saveInstance(field.name, field.get(oInstance))
            } finally {
                //Set the fields accessibility back to normal
                field.isAccessible = isAccessible
            }
        }
    }

    /**
     * Private method that save the fields instance to the InstanceHolder
     *
     * @param key Key in witch the object will be saved
     * @param o Object that will be saved
     */
    private fun saveInstance(key: String, o: Any?) {
        //Unwrap the object
        o?.let {
            //Save the object to the InstanceHolder
            InstanceHolder.holder[key] = it
        }
    }
}