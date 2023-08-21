package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {
    var binding: ActivityBmiBinding? = null
    var currentUnit = "IMPERIAL"
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarExcersise)
        if(supportActionBar !=  null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExcersise?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        // calculating the bmi when you click the calculate button
        binding?.btnCalculateInBmi?.setOnClickListener {
            var bmi = 0
            val  weight = binding?.txtWeight?.text.toString()
            val heightFeet = binding?.txtHeightFeet?.text.toString()
            val heightInches = binding?.txtHeightInches?.text.toString()
            val height = binding?.txtHeight?.text.toString()
            var bmiOutput = binding?.txtBmi
            var commentOutput = binding?.txtBmiComment
            var gradeOutput = binding?.txtBmiGrade
            // check validity of input
            if(currentUnit == "IMPERIAL"){
                if(weight.isEmpty() || heightFeet.isEmpty() || heightInches.isEmpty()){
                    Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }else if(currentUnit == "METRIC"){
                if(weight.isEmpty() || height.isEmpty()){
                    Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            // check if the unit is imperial or metric
            if(currentUnit == "IMPERIAL"){
                bmi = calcBmi(weight, heightFeet, heightInches)
            }else if (currentUnit == "METRIC"){
                bmi = calcBmiMetric(weight, height)
            }


            when (bmi){
                in 0..18 -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.setTextColor( getResources().getColor(R.color.red))
                    gradeOutput?.text = "Underweight"
                    commentOutput?.text = "You need to eat more you are underweight"
                }
                in 19..24 -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.setTextColor( getResources().getColor(R.color.green))
                    gradeOutput?.text = "NORMAL"
                    commentOutput?.text = "You are in a good shape continue"
                }
                in 25..29 -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.setTextColor( getResources().getColor(R.color.yellow))
                    gradeOutput?.text = "OVERWEIGHT"
                    commentOutput?.text = "You need to eat less you are overweight"
                }
                in 30..39 -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.setTextColor( getResources().getColor(R.color.orange))
                    gradeOutput?.text = "OBESE"
                    commentOutput?.text = "You need to eat less you are obese i mean come on"
                }
                in 40..70 -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.setTextColor( getResources().getColor(R.color.red))
                    gradeOutput?.text = "EXTREMELY OBESE"
                    commentOutput?.text = "You need to eat less you are extremely obese i mean come on"
                }
                else -> {
                    bmiOutput?.text = bmi.toString()
                    gradeOutput?.text = "greater than expected you are not human bro you are a whale "
                    commentOutput?.text = "Error"
                }
            }
        }

        // changing appareance of the activity when you click on the radio button for units
        binding?.rgUnits?.setOnCheckedChangeListener{ _, _ ->
            changeUnit()
        }
    }

    // functions

    fun changeUnit(){
        if(binding?.rbMetric?.isChecked == true){
            binding?.txtIptHeightInches?.visibility = View.GONE
            binding?.txtIptHeightFeet?.visibility = View.GONE
            binding?.txtinptHeight?.visibility = View.VISIBLE
            binding?.txtWeight?.hint = "Weight in KG"
            currentUnit = "METRIC"
        }else if (binding?.rbImperial?.isChecked == true){
            binding?.txtIptHeightInches?.visibility = View.VISIBLE
            binding?.txtIptHeightFeet?.visibility = View.VISIBLE
            binding?.txtinptHeight?.visibility = View.GONE
            binding?.txtWeight?.hint = "Weight in lbs"
            currentUnit = "IMPERIAL"
        }
    }
    fun calcBmi(weight: String, feet: String, inches: String): Int {
        val newWieght = weight.toFloat()
        val newFeet = feet.toFloat()
        val newInches = inches.toFloat()
        var height = (newFeet * 12) + newInches
        val bmi =  (newWieght / height.pow(2) )* 703
        return BigDecimal(bmi.toDouble()).setScale(0, RoundingMode.HALF_EVEN).toInt()
    }

    fun calcBmiMetric(weight: String, height: String): Int {
        val newWieght = weight.toFloat()
        val newHeight = height.toFloat()
        val bmi =  (newWieght / newHeight.pow(2) )
        return BigDecimal(bmi.toDouble()).setScale(0, RoundingMode.HALF_EVEN).toInt()
    }


}