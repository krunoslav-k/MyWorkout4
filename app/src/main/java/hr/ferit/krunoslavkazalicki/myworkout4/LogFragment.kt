package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues
import android.content.ContentValues.TAG
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
        val durationEditText = view.findViewById<EditText>(R.id.duration_etnum)
        val intensityEditText = view.findViewById<EditText>(R.id.intensity_etnum)
        val logWorkoutButton = view.findViewById<Button>(R.id.logWorkout_btn)

        var currentProfile = Profile()
        db.collection("profiles")
            .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    updateCurrentProfile(currentProfile, document.data)
                    //funkcije za spremanje vrijednosti iz fieldova iz firebasea
                    Log.d(TAG, "UNUTAR ADDONSUCCESS LISTENERA: ${currentProfile.weight}")

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        Log.d(TAG, "IZVAN ADDONSUCCESS LISTENERA: ${currentProfile.weight}")

        logWorkoutButton.setOnClickListener {

            var duration = durationEditText.text.toString().toInt()
            var intensity = intensityEditText.text.toString().toInt()

  /*          muscleGroupRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                val radio = view.findViewById<RadioButton>(checkedId)

                when (radio) {
                    maleRadioButton -> {
                        editor.apply {
                            this?.putString("genderSP", "male")
                        }?.apply()
                    }
                    femaleRadioButton -> {
                        editor.apply {
                            this?.putString("genderSP", "female")
                        }?.apply()
                    }
                }

            }

            val checkedGender: String? = sharedPreferences?.getString("genderSP", "male")
            if (checkedGender.equals("male")) {
                gender = Gender.MALE
            } else if (checkedGender.equals("female")) {
                gender = Gender.FEMALE
            }
            */

            var currentWorkout = Workout(duration = duration, intensity = intensity, timestamp = getCurrentDateTime())
            currentWorkout.muscleGroup = MuscleGroup.ARMS
            currentWorkout.load = 560


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