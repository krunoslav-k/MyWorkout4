package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class profileFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val weightEditText = view.findViewById<EditText>(R.id.weight_etnum)
        val heightEditText = view.findViewById<EditText>(R.id.height_etnum)
        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.gender_rg)
        val ageEditText = view.findViewById<EditText>(R.id.age_etnum)
        val calorieIntakeEditText =  view.findViewById<EditText>(R.id.caloriesIntake_etnum)
        val saveChangesButton = view.findViewById<Button>(R.id.saveChanges_btn)

        var currentProfile = Profile(0, 30, 0, Gender.MALE, 0, Date(1990, 1, 1))
        db.collection("profiles")
            .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    updateCurrentProfile(currentProfile, document.data)

                    weightEditText.text.append(currentProfile.weight.toString())
                    heightEditText.text.append(currentProfile.height.toString())
                    ageEditText.text.append(currentProfile.age.toString())
                    calorieIntakeEditText.text.append(currentProfile.calorieIntake.toString())

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        saveChangesButton.setOnClickListener {

                    var weight = weightEditText.text.toString().toInt()
                    var height = heightEditText.text.toString().toInt()
                    var gender = "male"
                    var age = ageEditText.text.toString().toInt()
                    val calorieIntake = calorieIntakeEditText.text.toString().toInt()

                    val profile = hashMapOf(
                        "weight" to weight,
                        "height" to height,
                        "gender" to gender,
                        "age" to age,
                        "calorieIntake" to calorieIntake,
                        "timestamp" to getCurrentDateTime()

                    )

                    db.collection("profiles")
                        .add(profile)
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
            currentProfile.gender= if (data.get("gender").toString().equals("male")) Gender.MALE else Gender.FEMALE
            //currentProfile.timestamp = LocalDateTime.parse(data.get("timestamp").toCharArray())
        }
    }

}


