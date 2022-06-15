package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Types.TIMESTAMP
import java.text.SimpleDateFormat


class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val db = Firebase.firestore
        db.collection("workouts")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val workouts: MutableList<Workout> = ArrayList<Workout>()
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    var muscleGroup: MuscleGroup = MuscleGroup.TORSO
                    if (document.data.get("muscleGroup").toString() == "ARMS") {
                        muscleGroup = MuscleGroup.ARMS
                    } else if (document.data.get("muscleGroup").toString() == "TORSO") {
                        muscleGroup = MuscleGroup.TORSO
                    } else if (document.data.get("muscleGroup").toString() == "CORE") {
                        muscleGroup = MuscleGroup.CORE
                    } else if (document.data.get("muscleGroup").toString() == "LEGS") {
                        muscleGroup = MuscleGroup.LEGS
                    }

                    workouts.add(Workout(muscleGroup,
                        document.data.get("duration").toString().toInt(),
                        document.data.get("intensity").toString().toInt(),
                        document.data.get("load").toString().toInt(),
                        (document.data.get("timestamp") as Timestamp).toDate()))
                }
                view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                    layoutManager = LinearLayoutManager(this@HistoryFragment.context)
                    adapter = WorkoutRecyclerAdapter(workouts)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }


        return view
    }



}