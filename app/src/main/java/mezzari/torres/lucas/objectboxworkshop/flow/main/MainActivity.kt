package mezzari.torres.lucas.objectboxworkshop.flow.main

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.adapter.PersonsAdapter
import mezzari.torres.lucas.objectboxworkshop.annotation.LayoutReference
import mezzari.torres.lucas.objectboxworkshop.generic.BaseActivity

@LayoutReference(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val adapter: PersonsAdapter by lazy {
        PersonsAdapter(this)
    }

    override fun onInitValues() {
        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = adapter

        btnSave.setOnClickListener {

        }
    }
}
