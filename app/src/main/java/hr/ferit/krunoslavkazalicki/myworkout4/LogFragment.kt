package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

        var currentProfile = Profile(0, 30, 0, Gender.MALE, 0, Date(1990, 1, 1))
        db.collection("profiles")
            .orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    //funkcije za spremanje vrijednosti iz fieldova iz firebasea

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        logWorkoutButton.setOnClickListener {

        }

        return view
    }


}