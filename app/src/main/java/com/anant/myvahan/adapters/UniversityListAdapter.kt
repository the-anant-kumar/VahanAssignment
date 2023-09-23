package com.anant.myvahan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anant.myvahan.MainActivity
import com.anant.myvahan.R
import com.anant.myvahan.data.UniversityData
import com.bumptech.glide.Glide

class UniversityListAdapter(private val listener: MainActivity) :
    RecyclerView.Adapter<UniversityListAdapter.UniversityListViewHolder>() {

    class UniversityListViewHolder(itemView: View)  :RecyclerView.ViewHolder(itemView) {
        val universityName : TextView = itemView.findViewById(R.id.tvUniversityName)
        val universityCountry : TextView = itemView.findViewById(R.id.tvUniversityCountry)
        val universityWebsite : TextView = itemView.findViewById(R.id.tvUniversityWebsite)
        val universityIcon : ImageView = itemView.findViewById(R.id.ivUniversity)
    }

    private val differCallback = object : DiffUtil.ItemCallback<UniversityData>() {
        override fun areItemsTheSame(oldItem: UniversityData, newItem: UniversityData): Boolean {
            return oldItem.universityName == newItem.universityName
        }

        override fun areContentsTheSame(oldItem: UniversityData, newItem: UniversityData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return UniversityListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UniversityListViewHolder, position: Int) {
        val university = differ.currentList[position]
        holder.universityName.text = university.universityName
        holder.universityCountry.text = university.universityCountry
        holder.universityWebsite.text = university.universityWebsite
        holder.apply {
            Glide.with(itemView).load(R.drawable.logo_university).into(universityIcon)
        }
        holder.universityWebsite.setOnClickListener {
            listener.onItemClick(university)
        }
    }
}

interface UniversityItemClicked {
    fun onItemClick(item : UniversityData)
}