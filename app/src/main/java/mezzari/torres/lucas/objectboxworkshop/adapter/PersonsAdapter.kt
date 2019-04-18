package mezzari.torres.lucas.objectboxworkshop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_empty_items.view.*
import kotlinx.android.synthetic.main.row_person.view.*
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.model.Person

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 */
class PersonsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val inflater: LayoutInflater
    private val persons: ArrayList<Person>

    constructor(context: Context): super() {
        this.inflater = LayoutInflater.from(context)
        this.persons = arrayListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            EMPTY_ITEM_VIEW -> {
                return EmptyViewHolder(inflater.inflate(R.layout.row_empty_items, parent, false))
            }
            else -> {
                return PersonViewHolder(inflater.inflate(R.layout.row_person, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (persons.isEmpty()) 1 else persons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val position: Int = holder.adapterPosition
        when (getItemViewType(i)) {
            EMPTY_ITEM_VIEW -> {
                (holder as EmptyViewHolder).itemView.tvEmptyMessage.setText(R.string.empty_list)
            }
            else -> {
                val viewHolder: PersonViewHolder = holder as PersonViewHolder
                val person: Person = persons[position]
                viewHolder.itemView.tvName.text = person.name
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (persons.isEmpty()) EMPTY_ITEM_VIEW else PERSON_ITEM_VIEW
    }

    fun add(person: Person) {
        val index = persons.size
        persons.add(person)
        notifyItemInserted(index)
    }

    private class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        const val EMPTY_ITEM_VIEW: Int = 0
        const val PERSON_ITEM_VIEW: Int = 1
    }
}