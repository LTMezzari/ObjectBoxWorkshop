package mezzari.torres.lucas.objectboxworkshop

import android.app.Application
import mezzari.torres.lucas.objectboxworkshop.util.BoxUtils

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        BoxUtils.initialize(this)
    }
}