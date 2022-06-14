package hr.ferit.krunoslavkazalicki.myworkout4

import WorkoutRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HistoryFragment : Fragment() {

    val db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var workoutRecyclerAdapter: WorkoutRecyclerAdapter
    private lateinit var workoutArrayList: ArrayList<Workout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        workoutArrayList = ArrayList()

        workoutRecyclerAdapter = WorkoutRecyclerAdapter(workoutArrayList)
        recyclerView.adapter = workoutRecyclerAdapter



        return view
    }


}