package mezzari.torres.lucas.objectboxworkshop.generic

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import mezzari.torres.lucas.objectboxworkshop.annotation.LayoutReference
import mezzari.torres.lucas.objectboxworkshop.persistence.InstanceHolder
import mezzari.torres.lucas.objectboxworkshop.persistence.InstanceManager
import mezzari.torres.lucas.objectboxworkshop.persistence.Wrapper
import java.lang.RuntimeException
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
abstract class BaseActivity: AppCompatActivity() {

    //Declares and lazy initialize the rootView
    protected val rootView: View by lazy {
        window.decorView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get the LayoutReference annotation
        val layoutReference: LayoutReference? = javaClass.annotations.find { it.annotationClass == LayoutReference::class } as? LayoutReference
        //Unwrap the annotation, and throw an exception if the annotation is null
        layoutReference?.let {
            //Set the content view
            setContentView(layoutReference.value)
        } ?: throw RuntimeException("The activity should contain a LayoutReference annotation")

        //Call the abstract method
        onInitValues()
    }

    /**
     * Abstract method to be implemented
     */
    protected abstract fun onInitValues()

    /**
     * Start Activity with a Kotlin class and a Wrapper
     *
     * @param activity The Activity Kotlin Class
     * @param wrapper The Wrapper to save
     */
    protected fun startActivity(activity: KClass<*>, wrapper: Wrapper) {
        //Passes the Java Class and the Wrapper
        startActivity(activity.java, wrapper)
    }

    /**
     * Start Activity with a Java class and a Wrapper
     *
     * @param activity The Activity Java Class
     * @param wrapper The Wrapper to save
     */
    protected fun startActivity(activity: Class<*>, wrapper: Wrapper) {
        //Save the wrapper
        InstanceManager.saveInstances(wrapper)
        //Call the super start activity
        super.startActivity(Intent(this, activity))
    }

    /**
     * Start Activity For Result with a Kotlin class, a Wrapper, and a request code
     *
     * @param activity The Activity Kotlin Class
     * @param requestCode The request code
     * @param wrapper The Wrapper to save
     */
    protected fun startActivityForResult(activity: KClass<*>, requestCode: Int, wrapper: Wrapper) {
        //Passes the Java Class, the request code, and the wrapper
        startActivityForResult(activity.java, requestCode, wrapper)
    }

    /**
     * Start Activity For Result with a Kotlin class, and a request code
     *
     * @param activity The Activity Kotlin Class
     * @param requestCode The request code
     */
    protected fun startActivityForResult(activity: KClass<*>, requestCode: Int) {
        //Passes the Java Class, and the request code
        startActivityForResult(activity.java, requestCode)
    }

    /**
     * Start Activity For Result with a Java class, a Wrapper, and a request code
     *
     * @param activity The Activity Kotlin Class
     * @param requestCode The request code
     * @param wrapper The Wrapper to save
     */
    protected fun startActivityForResult(activity: Class<*>, requestCode: Int, wrapper: Wrapper) {
        //Save the wrapper
        InstanceManager.saveInstances(wrapper)
        //Call the next method
        startActivityForResult(activity, requestCode)
    }

    /**
     * Start Activity For Result with a Java class, and a request code
     *
     * @param activity The Activity Kotlin Class
     * @param requestCode The request code
     */
    protected fun startActivityForResult(activity: Class<*>, requestCode: Int) {
        //Call the super start activity for result
        super.startActivityForResult(Intent(this, activity), requestCode)
    }

    /**
     * Set Result with a result code, and a Wrapper
     *
     * @param resultCode The result code
     * @param wrapper The Wrapper to save
     */
    protected fun setResult(resultCode: Int, wrapper: Wrapper) {
        //Save the Wrapper
        InstanceManager.saveInstances(wrapper)
        //Call the super method
        super.setResult(resultCode)
    }

    /**
     * Return the casted instance from the InstanceManager
     *
     * @param key The instance key
     * @param shouldClearInstance A boolean that represents if the instace should be removed after retrieving
     * @return The nullable casted instance
     */
    protected fun <T>getInstance(key: String, shouldClearInstance: Boolean = false): T? {
        //Return the instance from the InstanceManager
        return InstanceManager.getInstance(key, shouldClearInstance)
    }
}