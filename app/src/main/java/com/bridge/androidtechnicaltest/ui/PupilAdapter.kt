package com.bridge.androidtechnicaltest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.ItemPupilBinding
import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.network.Utils
import com.bridge.androidtechnicaltest.ui.PupilAdapter.ViewHolder
import com.bumptech.glide.Glide

class PupilAdapter(private val callback: Callback) : RecyclerView.Adapter<ViewHolder>() {
	
	private val pupils: ArrayList<Pupil> = arrayListOf()
	
	override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
	): ViewHolder {
		val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pupil, parent, false))
		holder.binding.root.setOnClickListener {
			holder.pupil?.let {callback.onItemClick(it)}
		}
		holder.binding.deleteBtn.setOnClickListener {
			holder.pupil?.let {callback.onDelete(it)}
		}
		return holder
	}
	
	override fun onBindViewHolder(
			holder: ViewHolder,
			position: Int
	) {
		holder.setData(pupils[position])
	}
	
	override fun getItemCount(): Int = pupils.size
	
	fun setItems(items: List<Pupil>) {
		pupils.clear()
		pupils.addAll(items)
		notifyDataSetChanged()
	}
	
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		val binding: ItemPupilBinding = ItemPupilBinding.bind(itemView)
		var pupil: Pupil? = null
		
		fun setData(pupil: Pupil) {
			this.pupil = pupil
			Glide.with(itemView).load(pupil.image).centerCrop().placeholder(R.drawable.placeholder_image).error(R.drawable.placeholder_image)
					.into(binding.image)
			binding.nameText.text = pupil.name
			binding.idText.text = Utils.getString(R.string.id_value).format(pupil.pupilId)
			binding.countryText.text = Utils.getString(R.string.country_value).format(pupil.country)
		}
	}
	
	interface Callback {
		
		fun onItemClick(pupil: Pupil)
		
		fun onDelete(pupil: Pupil)
	}
}