package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.ExitDiaolgBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class exerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding : ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restDuration = 2
    private var workoutDuration = 10
    private var workoutProgress = 0
    private var workoutTimer: CountDownTimer? = null
    private var excersiseList : ArrayList<Excersises>? = null
    private var currentExcersisePosition = -1
    private var textToSpeech: TextToSpeech? = null
    private var player :MediaPlayer? = null
    private var adapter : ExcersiseAdapter? = null
    private var completedSets: Int = 0
    private lateinit var excersisetimer: CountDownTimer
    private var excersiseTimerSeconds: Long = 0
    private lateinit var historyDao : HistoryDao
    private var date : String? = null
    private var time : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExcersise)
        excersiseList = com.example.a7minworkout.Constants.defultExcersiseList()
        if(supportActionBar !=  null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        historyDao = (application as HistoryDatabaseApp).db.historyDao()
        binding?.toolbarExcersise?.setNavigationOnClickListener{
            showBackDialog()
        }

        setUpRestView()
        textToSpeech = TextToSpeech(this,this@exerciseActivity,"")

      setUpExcersiseRecyclerView()
        //date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Add 1 to match the month number to the actual month (January = 1)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Output the current date in the format MM/DD/YYYY
        date = "$month/$dayOfMonth/$year"


        excersisetimer = object : CountDownTimer(Long.MAX_VALUE, 1000){
            override fun onTick(p0: Long) {
                excersiseTimerSeconds++

            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }

        excersisetimer.start()

    }












    // functions
    private fun addRecordtoHistory(dao : HistoryDao, iscompleted : Boolean, date: String, time: String, sets : String){

        lifecycleScope.launch {
            dao.insert(HistoryEntity(date = date, completed_time = time, completed_sets = sets, isCompleted = iscompleted ))
        }

    }
    private fun setUpExcersiseRecyclerView(){
        adapter = ExcersiseAdapter(excersiseList!!)
        binding?.recViewExcersiseStatus?.adapter = adapter
        binding?.recViewExcersiseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    private fun showBackDialog(){
        val dialog = Dialog(this)
        val dialogBinding: ExitDiaolgBinding = ExitDiaolgBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.negativeBttn.setOnClickListener(){
            dialog.dismiss()
        }
        dialogBinding.positiveBtton.setOnClickListener(){
            excersisetimer.cancel()
            val secs = excersiseTimerSeconds % 60
            val mins = excersiseTimerSeconds / 60
            time = "$mins:$secs"
            lifecycleScope.launch {
                addRecordtoHistory(historyDao,false,date!!,time!!,completedSets.toString())
            }
            this@exerciseActivity.finish()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setUpRestView(){
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.disUpcomingDisclaimer?.visibility = View.VISIBLE
        binding?.disUpcomingExcersise?.visibility = View.VISIBLE
        binding?.progressBar?.progress = restProgress
        binding?.disTimerText?.text = "get ready"
        binding?.disUpcomingExcersise?.text = "${excersiseList!![currentExcersisePosition +1].getName()}"
        binding?.progressBar?.max = restDuration
        binding?.disExcersiseImage?.visibility = View.GONE
        workoutTimer?.cancel()
        workoutProgress = 0

        restTimer = object : CountDownTimer((1000 * restDuration).toLong(), 1000){
            override fun onTick(p0: Long) {
                restProgress ++
                binding?.progressBar?.progress = restDuration- restProgress
                binding?.timer?.text = (restDuration -restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@exerciseActivity, "now we will start the exercise", Toast.LENGTH_SHORT).show()
                restProgress = 0
                setWorkoutProgressBar()
            }

        }.start()
    }

    private fun setWorkoutProgressBar(){
        currentExcersisePosition ++
        binding?.disExcersiseImage?.visibility = View.VISIBLE
        binding?.disUpcomingDisclaimer?.visibility = View.GONE
        binding?.disUpcomingExcersise?.visibility = View.GONE
        binding?.disExcersiseImage?.setImageResource(excersiseList!![currentExcersisePosition].getImage())
        binding?.progressBar?.progress = workoutProgress
        binding?.disTimerText?.text = excersiseList!![currentExcersisePosition].getName()
        binding?.progressBar?.max = workoutDuration
        speak(excersiseList!![currentExcersisePosition].getName().toString())
        excersiseList!![currentExcersisePosition].setIsSelected(true)
        adapter?.notifyDataSetChanged()




        workoutTimer = object : CountDownTimer((1000 * workoutDuration).toLong(), 1000){
            override fun onTick(p0: Long) {
                workoutProgress ++
                binding?.progressBar?.progress = workoutDuration- workoutProgress
                binding?.timer?.text = (workoutDuration -workoutProgress).toString()
            }

            override fun onFinish() {
                try {
                    val soundUri = Uri.parse("android.resource://com.example.a7minworkout/" + R.raw.zapsplat_multimedia_alert_chime_vibraphone_tremolo_positive_001_93390)
                    player = MediaPlayer.create(applicationContext, soundUri)
                    player?.isLooping = false
                    player?.start()
                }catch (e: Exception){
                    e.printStackTrace()
                }
                excersiseList!![currentExcersisePosition].setIsSelected(false)
                excersiseList!![currentExcersisePosition].setIsCompleted(true)
                adapter?.notifyDataSetChanged()
                if (currentExcersisePosition < excersiseList!!.size -1 ){
                    setRestProgressBar()
                    completedSets ++
                }else{
                    Toast.makeText(this@exerciseActivity, "congrats", Toast.LENGTH_SHORT).show()
                    excersisetimer.cancel()
                    val secs = excersiseTimerSeconds % 60
                    val mins = excersiseTimerSeconds / 60
                    time = "$mins:$secs"

                    lifecycleScope.launch {
                        addRecordtoHistory(historyDao,true,date!!,time!!,completedSets.toString())
                    }

                    val intent = Intent(this@exerciseActivity,finishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()


    }

    private fun speak(text:String){
        textToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (workoutTimer != null){
            workoutTimer?.cancel()
            workoutProgress = 0
        }
        if (player != null){
            player!!.stop()
        }
        binding = null

    }


    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS){
            var result = textToSpeech!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("tts","the language selected is not supported")
            }
        }else{
            Log.e("tts","error in initialization")
        }
    }
}