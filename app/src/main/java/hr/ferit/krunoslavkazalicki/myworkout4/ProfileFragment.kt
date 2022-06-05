package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
                "calorieIntake" to calorieIntake
            )

            // Add a new document with a generated ID
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


}