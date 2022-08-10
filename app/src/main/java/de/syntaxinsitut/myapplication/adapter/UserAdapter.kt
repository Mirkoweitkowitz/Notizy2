package de.syntaxinsitut.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.User

class UserAdapter (
    private val dataset: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val userText: TextView = view.findViewById(R.id.user_text)
        val mailText: TextView = view.findViewById(R.id.mail_text)
    }

    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return UserViewHolder(adapterLayout)
    }

    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset[position]
        holder.userText.text = item.name
        holder.mailText.text = item.email
    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }

}