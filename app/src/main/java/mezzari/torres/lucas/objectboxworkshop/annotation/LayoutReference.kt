package mezzari.torres.lucas.objectboxworkshop.annotation

import android.support.annotation.LayoutRes

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 *
 * Annotation that receives a Layout Resource for generic validations
 */
annotation class LayoutReference ( @LayoutRes val value: Int)