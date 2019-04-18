package mezzari.torres.lucas.objectboxworkshop.persistence

import java.lang.reflect.Field

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
object InstanceManager {

    fun <T>getInstance(key: String, shouldClearInstance: Boolean = false): T? {
        try {
            val t: T? = InstanceHolder.holder[key] as T?

            if (shouldClearInstance) {
                removeInstance(key)
            }

            return t
        } catch (e: ClassCastException) {
            return null
        }
    }

    fun removeInstance(key: String) {
        InstanceHolder.holder.remove(key)
    }

    fun clearInstances() {
        InstanceHolder.holder.clear()
    }

    fun saveInstances(oClass: Class<*>, oInstance: Any) {
        val fields: Array<Field> = oClass.fields
        for (field in fields) {
            val isAccessible: Boolean = field.isAccessible
            field.isAccessible = true

            try {
                saveInstance(field.name, field.get(oInstance))
            } finally {
                field.isAccessible = isAccessible
            }
        }
    }

    private fun saveInstance(key: String, o: Any?) {
        o?.let {
            InstanceHolder.holder[key] = it
        }
    }
}