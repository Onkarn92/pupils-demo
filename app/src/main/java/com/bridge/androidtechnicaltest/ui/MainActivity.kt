package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.ActivityMainBinding
import com.bridge.androidtechnicaltest.di.DaggerPupilComponent
import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.network.Utils
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PupilAdapter.Callback, OnRefreshListener {
	
	private lateinit var binding: ActivityMainBinding
	@Inject lateinit var viewModel: PupilViewModel
	@Inject lateinit var adapter: PupilAdapter
	
	public override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		DaggerPupilComponent.builder().withCallback(this).withOwner(this).build().injectMainActivity(this)
		setupView()
		attachObservers()
	}
	
	override fun onItemClick(pupil: Pupil) {
		Toast.makeText(this, "Not yet implemented!", Toast.LENGTH_SHORT).show()
	}
	
	override fun onDelete(pupil: Pupil) {
		binding.swipeRefresh.isRefreshing = true
		viewModel.deletePupil(pupil)
	}
	
	override fun onRefresh() {
		binding.swipeRefresh.isRefreshing = true
		binding.pupilRecycler.visibility = View.GONE
		binding.errorLayout.root.visibility = View.GONE
		viewModel.getPupils()
	}
	
	private fun setupView() {
		supportActionBar?.setDisplayShowTitleEnabled(true)
		supportActionBar?.setTitle(R.string.pupils)
		binding.swipeRefresh.setOnRefreshListener(this@MainActivity)
		binding.swipeRefresh.setColorSchemeColors(Utils.getColor(R.color.colorPrimary), Utils.getColor(R.color.colorAccent))
		binding.pupilRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
		binding.pupilRecycler.adapter = adapter
		onRefresh()
	}
	
	private fun attachObservers() {
		viewModel.getPupilObservable().observe(this) {
			binding.swipeRefresh.isRefreshing = false
			binding.pupilRecycler.visibility = View.VISIBLE
			binding.errorLayout.root.visibility = View.GONE
			adapter.setItems(it)
		}
		viewModel.getErrorObservable().observe(this) {
			binding.swipeRefresh.isRefreshing = false
			binding.pupilRecycler.visibility = View.GONE
			binding.errorLayout.root.visibility = View.VISIBLE
			binding.errorLayout.errorTitleText.text = it.first
			binding.errorLayout.errorMessageText.text = it.second.localizedMessage
		}
	}
}