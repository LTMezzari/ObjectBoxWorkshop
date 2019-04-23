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

    protected val rootView: View by lazy {
        window.decorView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutReference: LayoutReference? = javaClass.annotations.find { it.annotationClass == LayoutReference::class } as? LayoutReference
        layoutReference?.let {
            setContentView(layoutReference.value)
        } ?: throw RuntimeException("The activity should contain a LayoutReference annotation")

        onInitValues()
    }

    protected abstract fun onInitValues()

    protected fun startActivity(activity: KClass<*>, wrapper: Wrapper) {
        startActivity(activity.java, wrapper)
    }

    protected fun startActivity(activity: Class<*>, wrapper: Wrapper) {
        InstanceManager.saveInstances(wrapper::class, wrapper)
        super.startActivity(Intent(this, activity))
    }

    protected fun startActivityForResult(activity: KClass<*>, requestCode: Int, wrapper: Wrapper) {
        startActivityForResult(activity.java, requestCode, wrapper)
    }

    protected fun startActivityForResult(activity: KClass<*>, requestCode: Int) {
        startActivityForResult(activity.java, requestCode)
    }

    protected fun startActivityForResult(activity: Class<*>, requestCode: Int, wrapper: Wrapper) {
        InstanceManager.saveInstances(wrapper::class, wrapper)
        startActivityForResult(activity, requestCode)
    }

    protected fun startActivityForResult(activity: Class<*>, requestCode: Int) {
        super.startActivityForResult(Intent(this, activity), requestCode)
    }

    protected fun setResult(resultCode: Int, wrapper: Wrapper) {
        InstanceManager.saveInstances(wrapper::class, wrapper)
        setResult(resultCode)
    }

    protected fun <T>getInstance(key: String, shouldClearInstance: Boolean = false): T? {
        return InstanceManager.getInstance(key, shouldClearInstance)
    }
}