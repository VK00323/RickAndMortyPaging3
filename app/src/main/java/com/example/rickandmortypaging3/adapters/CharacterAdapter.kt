package com.example.rickandmortypaging3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypaging3.R
import com.example.rickandmortypaging3.network.model.PojoResult
import com.squareup.picasso.Picasso

class CharacterAdapter :
    PagingDataAdapter<PojoResult, CharacterAdapter.CharacterViewHolder>(CharacterDiffItemCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = getItem(position)
        with(holder) {
            if (currentCharacter != null) {
                textViewName.text = currentCharacter.name
            textViewStatus.text = currentCharacter.status
            textViewSpecies.text = currentCharacter.species
            currentCharacter.gender.also { textViewGender.text = it }
            Picasso.get().load(currentCharacter.image).into(imageView)
            }
        }
    }


    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        val textViewSpecies: TextView = itemView.findViewById(R.id.textViewSpecies)
        val textViewGender: TextView = itemView.findViewById(R.id.textViewGender)
        val imageView: ImageView = itemView.findViewById (R.id.imageViewAvatar)
    }

    private object CharacterDiffItemCallBack : DiffUtil.ItemCallback<PojoResult>() {
        override fun areItemsTheSame(oldItem: PojoResult, newItem: PojoResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PojoResult, newItem: PojoResult): Boolean {
            return oldItem == newItem
        }
    }
}