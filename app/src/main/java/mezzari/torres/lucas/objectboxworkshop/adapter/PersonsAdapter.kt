package mezzari.torres.lucas.objectboxworkshop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_empty_items.view.*
import kotlinx.android.synthetic.main.row_person.view.*
import mezzari.torres.lucas.objectboxworkshop.R
import mezzari.torres.lucas.objectboxworkshop.archives.*
import mezzari.torres.lucas.objectboxworkshop.model.Person

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 18/04/19
 *
 * Adapter for the MainActivity RecyclerView
 */
class PersonsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Declares the LayoutInflater
    private val inflater: LayoutInflater
    //Declares the list of persons
    private var persons: ArrayList<Person>

    //Declares a public variable with private set
    //It is used to show and hide the rows check boxes
    var isInSelectionMode: Boolean
        private set

    //Declares a nullable closure for onClick on persons
    var onPersonPersonClick: OnPersonClickListener? = null
    //Declares a nullable closure for onCheckedChange on persons
    var onPersonCheckedChangeListener: OnPersonCheckedChangeListener? = null

    /**
     * Constructor with context that initialize the variables
     */
    constructor(context: Context): super() {
        this.inflater = LayoutInflater.from(context)
        this.persons = arrayListOf()
        this.isInSelectionMode = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Switch the view type
        when (viewType) {
            EMPTY_ITEM_VIEW -> {
                //Creates a EmptyViewHolder
                return EmptyViewHolder(inflater.inflate(R.layout.row_empty_items, parent, false))
            }
            else -> {
                //Creates a PersonViewHolder
                return PersonViewHolder(inflater.inflate(R.layout.row_person, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        //Verify if the list is empty
        //Returns one item if it is
        //Returns the count if it is not
        if (persons.isEmpty())
            return 1
        else
            return persons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        //Get the adapter position
        val position: Int = holder.adapterPosition
        when (getItemViewType(i)) {
            EMPTY_ITEM_VIEW -> {
                //Set the text in the empty view holder
                (holder as EmptyViewHolder).itemView.tvEmptyMessage.setText(R.string.empty_list)
            }
            else -> {
                //Cast the view holder to a PersonViewHolder
                val viewHolder: PersonViewHolder = holder as PersonViewHolder
                //Get the person in the position
                val person: Person = persons[position]
                //Set the persons name in the row
                viewHolder.itemView.tvName.text = person.name
                //Set if the person is checked
                viewHolder.itemView.cbChecked.isChecked = person.isChecked
                //Set if the check box is visible
                viewHolder.itemView.cbChecked.visibility = if (isInSelectionMode) View.VISIBLE else View.GONE

                //Set the onClickListener on the view
                viewHolder.itemView.setOnClickListener {
                    //Verify if is in selection mode
                    if (isInSelectionMode) {
                        //Get the checked state from the check box
                        val isChecked: Boolean = viewHolder.itemView.cbChecked.isChecked
                        //Set the person checked state
                        person.isChecked = !isChecked
                        //Set the holder checked state
                        viewHolder.itemView.cbChecked.isChecked = !isChecked

                        //Invoke the closure
                        onPersonCheckedChangeListener?.invoke(isInSelectionMode)
                        //Notify the row changed
                        notifyItemChanged(position)
                    } else {
                        //Invoke the default closure
                        onPersonPersonClick?.invoke(person)
                    }
                }

                //Set the onLongClickListener
                viewHolder.itemView.setOnLongClickListener {
                    //Change the selection mode just when it is off
                    if (!isInSelectionMode) {
                        //Set the mode to true
                        isInSelectionMode = true
                        //Invoke the listener
                        onPersonCheckedChangeListener?.invoke(isInSelectionMode)
                        //Notify that everything changed
                        notifyDataSetChanged()
                    }
                    //return that the event was consumed
                    true
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        //Return the empty view type just when the persons is empty
        if (persons.isEmpty())
            return EMPTY_ITEM_VIEW
        else
            return PERSON_ITEM_VIEW
    }

    fun setItems(ps: List<Person>?) {
        //Set the items if the list is not null
        ps?.let { persons ->
            //Set the adapter list to the new items
            this.persons = ArrayList(persons)
            //Notify that everything changed
            notifyDataSetChanged()
        }
    }

    fun add(p: Person?) {
        //Add only if the person is not null
        p?.let { person ->
            //If the list is empty
            //Notify the adapter in a different way
            if (persons.isEmpty()) {
                //Add the person
                persons.add(person)
                //Notify everything changed
                notifyDataSetChanged()
                //Finish method execution
                return
            }

            //Get the last index
            val index = persons.size
            //Add person
            persons.add(person)
            //Notify item inserted in the last index
            notifyItemInserted(index)
        }
    }

    fun update(person: Person) {
        //Return the person index
        val position = persons.indexOfFirst { p1 -> p1.id == person.id}
        //Check if it found a position
        if (position != -1) {
            //Update the person
            persons[position] = person
            //Notify item changed
            notifyItemChanged(position)
        }
    }

    fun getMarkedPersons(): List<Person> {
        //Filter the persons for the checked state
        return persons.filter { it.isChecked  }
    }

    fun deletePersons(persons: List<Person>) {
        //Remove the persons from the list
        this.persons.removeAll(persons)
        //Notify everything changed
        notifyDataSetChanged()
    }

    fun turnSelectionModeOff() {
        //Turn the selection mode off
        isInSelectionMode = false
        //Loops through the persons
        for (person in persons) {
            //Set the person checked state to false
            person.isChecked = false
        }
        //Invoke the onCheckedChange closure
        onPersonCheckedChangeListener?.invoke(isInSelectionMode)
        //Notify everything changed
        notifyDataSetChanged()
    }

    /**
     * ViewHolder for Persons
     */
    private class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /**
     * ViewHolder for when the list is empty
     */
    private class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        const val EMPTY_ITEM_VIEW: Int = 0
        const val PERSON_ITEM_VIEW: Int = 1
    }
}