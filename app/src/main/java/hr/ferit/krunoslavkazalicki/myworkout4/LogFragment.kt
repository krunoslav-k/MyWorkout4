package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.nfc.Tag
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

        var currentProfile = Profile()
        db.collection("profiles")
            .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    editor.apply { this?.putString("weightProfileSP", "${document.data.get("weight").toString().toInt()}") }
                    editor.apply { this?.putString("heightProfileSP", "${document.data.get("height").toString().toInt()}") }
                    editor.apply { this?.putString("ageProfileSP", "${document.data.get("age").toString().toInt()}") }
                    editor.apply { this?.putString("calorieIntakeProfileSP", "${document.data.get("calorieIntake").toString().toInt()}") }
                    editor.apply { this?.putString("weightProfileSP", "${document.data.get("weight").toString().toInt()}") }
                    editor.apply { this?.putString("genderProfileSP", "${document.data.get("gender").toString().toInt()}") }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        var profile = Profile(
            sharedPreferences?.getString("heightProfileSP", "80")?.toInt(),
            sharedPreferences?.getString("weightProfileSP", "80")?.toInt(),
            sharedPreferences?.getString("ageProfileSP", "80")?.toInt(),
            Gender.MALE,
            sharedPreferences?.getString("calorieIntakeProfileSP", "1800")?.toInt(),
            null)

        Log.d(TAG, "SAD JE PFRL ${profile.calorieIntake}")

        logWorkoutButton.setOnClickListener {

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

            var currentWorkout = Workout(muscleGroup, duration, intensity, 50, getCurrentDateTime())

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

        return view
    }

    private fun calculateLoad(profile: Profile): Int? {
        TODO("Not yet implemented")
        return 50
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    fun updateCurrentProfile(currentProfile: Profile, data: MutableMap<String, Any>){
        if (!data.isEmpty()){
            currentProfile.weight=data.get("weight").toString().toInt()
            currentProfile.height=data.get("height").toString().toInt()
            currentProfile.age=data.get("age").toString().toInt()
            currentProfile.calorieIntake=data.get("calorieIntake").toString().toInt()

            if (data.get("gender").toString().equals("male")) {
                currentProfile.gender = Gender.MALE
            } else if (data.get("gender").toString().equals("female")){
                currentProfile.gender = Gender.FEMALE
            }

            //currentProfile.timestamp = LocalDateTime.parse(data.get("timestamp").toCharArray())
        }
        }


}