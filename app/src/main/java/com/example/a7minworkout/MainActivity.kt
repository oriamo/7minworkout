package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
     var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // body
        binding?.flStart?.setOnClickListener(){
                val intent = Intent(this, exerciseActivity::class.java)
                startActivity(intent)
        }
        binding?.flBmi?.setOnClickListener(){
            val intent = Intent(this,BmiActivity::class.java)
            startActivity(intent)
        }
        binding?.flHistory?.setOnClickListener(){
            val intent = Intent(this,historyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}