package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class historyActivity : AppCompatActivity() {
    var binding: ActivityHistoryBinding? = null
    lateinit var dao : HistoryDao
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        dao = (application as HistoryDatabaseApp).db.historyDao()

        setSupportActionBar(binding?.toolbarExcersise)
        if(supportActionBar !=  null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExcersise?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        lifecycleScope.launch {
            dao.fetchAllHistory().collect {
                setUpRecyclerView(it as ArrayList<HistoryEntity>,dao)
            }
        }




    }

    private fun setUpRecyclerView(items: ArrayList<HistoryEntity>,dao : HistoryDao){
        val itemAdapter = HistoryAdapter(items) { id -> deleteDatafromDatabase(dao, id) }
        binding?.recyclerView?.adapter = itemAdapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)

    }

    private fun deleteDatafromDatabase(dao: HistoryDao, id : Int){
        lifecycleScope.launch {
            dao.delete(HistoryEntity(id = id))
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}