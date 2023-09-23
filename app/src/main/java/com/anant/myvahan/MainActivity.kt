package com.anant.myvahan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.anant.myvahan.adapters.UniversityItemClicked
import com.anant.myvahan.adapters.UniversityListAdapter
import com.anant.myvahan.data.MySingleton
import com.anant.myvahan.data.UniversityData
import com.anant.myvahan.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest

class MainActivity : AppCompatActivity(), UniversityItemClicked {
    lateinit var binding: ActivityMainBinding
    lateinit var mUniversityListAdapter: UniversityListAdapter
    val TAG = "homepage"

    companion object {
        var currentInstance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentInstance = this

        val serviceIntent = Intent(this, RefreshService::class.java)
        startService(serviceIntent)

        fetchUniversityData()
        setupUniversityList()

    }

    fun setupUniversityList() {
        mUniversityListAdapter = UniversityListAdapter(this)
        binding.rvUnivesityList.adapter = mUniversityListAdapter
        binding.rvUnivesityList.layoutManager = LinearLayoutManager(binding.root.context)
    }

    fun fetchUniversityData() {
        binding.universityLoader.visibility = View.VISIBLE
        val url = "http://universities.hipolabs.com/search"


        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                binding.universityLoader.visibility = View.INVISIBLE
                val universities = mutableListOf<UniversityData>()
                for (i in 0 until it.length()) {
                    val jsonObject = it.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val webPages = jsonObject.getJSONArray("web_pages")[0]
                    val country = jsonObject.getString("country")

                    val university = UniversityData(
                        name,
                        webPages.toString(),
                        country
                    )
                    universities.add(university)
                }
                mUniversityListAdapter.differ.submitList(universities)
            },
            { error ->
                Log.d(TAG, error.toString())
                error.printStackTrace()
            }
        )

        MySingleton.getInstance(binding.root.context).addToRequestQueue(jsonArrayRequest)
    }

    override fun onItemClick(item: UniversityData) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        val url = item.universityWebsite
        customTabsIntent.launchUrl(binding.root.context, Uri.parse(url))
    }
}