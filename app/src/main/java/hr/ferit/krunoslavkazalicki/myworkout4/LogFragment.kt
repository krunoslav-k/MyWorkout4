package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class logFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log, container, false)

        val muscleGroupRadioGroup = view.findViewById<RadioGroup>(R.id.muscleGroup_rg)
        val armsRadioButton = view.findViewById<RadioButton>(R.id.arms_rb)
        val torsoRadioButton = view.findViewById<RadioButton>(R.id.torso_rb)
        val coreRadioButton = view.findViewById<RadioButton>(R.id.core_rb)
        val legsRadioButton = view.findViewById<RadioButton>(R.id.legs_rb)
        val durationEditText = view.findViewById<EditText>(R.id.duration_etnum)
        val intensityEditText = view.findViewById<EditText>(R.id.intensity_etnum)
        val logWorkoutButton = view.findViewById<Button>(R.id.logWorkout_btn)
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

        logWorkoutButton.setOnClickListener {

            //učitavanje parametara workout-a
            var duration = durationEditText.text.toString().toInt()
            var intensity = intensityEditText.text.toString().toInt()
            var muscleGroup = MuscleGroup.TORSO

            muscleGroupRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                val radio = view.findViewById<RadioButton>(checkedId)

                when (radio) {
                    armsRadioButton -> {
                        editor.apply {
                            this?.putString("muscleGroupSP", "arms")
                        }?.apply()
                    }
                    torsoRadioButton -> {
                        editor.apply {
                            this?.putString("muscleGroupSP", "torso")
                        }?.apply()
                    }
                    coreRadioButton -> {
                        editor.apply {
                            this?.putString("muscleGroupSP", "core")
                        }?.apply()
                    }
                    legsRadioButton -> {
                        editor.apply {
                            this?.putString("muscleGroupSP", "legs")
                        }?.apply()
                    }
                }
            }

            val checkedMuscleGroup: String? = sharedPreferences?.getString("muscleGroupSP", "torso")
            if (checkedMuscleGroup.equals("arms")) {
                muscleGroup = MuscleGroup.ARMS
            } else if (checkedMuscleGroup.equals("torso")) {
                muscleGroup = MuscleGroup.TORSO
            } else if (checkedMuscleGroup.equals("core")) {
                muscleGroup = MuscleGroup.CORE
            } else if (checkedMuscleGroup.equals("legs")) {
                muscleGroup = MuscleGroup.LEGS
            }

            //učitavanje profila i izračun opterečenja workouta

            db.collection("profiles")
                    .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                            var gender : Gender = Gender.MALE
                            if (document.data.get("gender").toString() == "MALE") {
                                gender = Gender.MALE
                            } else if (document.data.get("gender").toString() == "FEMALE"){
                                gender = Gender.FEMALE
                            }

                            var profile = Profile(
                                    document.data.get("height").toString().toInt(),
                                    document.data.get("weight").toString().toInt(),
                                    document.data.get("age").toString().toInt(),
                                    gender,
                                    document.data.get("calorieIntake").toString().toInt(),
                                    null
                            )

                            var currentWorkout = Workout(muscleGroup, duration, intensity, calculateLoad(profile, muscleGroup, duration, intensity), getCurrentDateTime())

                            val workout = hashMapOf(
                                    "duration" to currentWorkout.duration,
                                    "intensity" to currentWorkout.intensity,
                                    "load" to currentWorkout.load,
                                    "muscleGroup" to currentWorkout.muscleGroup,
                                    "timestamp" to currentWorkout.timestamp
                            )

                            db.collection("workouts")
                                    .add(workout)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }

                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting documents.", exception)
                    }

        }

        return view
    }

    private fun calculateLoad(profile: Profile, muscleGroup: MuscleGroup, duration: Int, intensity: Int): Int? {

        var genderFactor: Double = 1.0
        var basalMetabolicRate: Double = 1500.0

        when (profile.gender) {
            Gender.MALE -> {
                genderFactor = 1.12
                basalMetabolicRate = 13.397 * profile.weight!! + 4.799 * profile.height!! - 5.677 * profile.age!! + 88.362
            }
            Gender.FEMALE -> {
                genderFactor = 0.94
                basalMetabolicRate = 9.247 * profile.weight!! + 3.098 * profile.height!! - 4.330 * profile.age!! + 447.593
            }
        }

        var muscleGroupFactor: Double = 1.0

        when (muscleGroup) {
            MuscleGroup.ARMS -> muscleGroupFactor = 0.22
            MuscleGroup.TORSO -> muscleGroupFactor = 0.427
            MuscleGroup.CORE -> muscleGroupFactor = 0.19
            MuscleGroup.LEGS -> muscleGroupFactor = 0.363
        }

        var calorieIntakeFactor: Double = 1.0

        if (profile.calorieIntake!! - basalMetabolicRate < 0) {
            calorieIntakeFactor = 0.95
        } else if (profile.calorieIntake!! - basalMetabolicRate < 500) {
            calorieIntakeFactor = 1.02
        } else if (profile.calorieIntake!! - basalMetabolicRate > 500) {
            calorieIntakeFactor = 1.05
        }

        return (muscleGroupFactor * profile.weight!! * genderFactor * (intensity/5) * duration * calorieIntakeFactor).toInt()
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }



}