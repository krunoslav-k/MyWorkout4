package hr.ferit.krunoslavkazalicki.myworkout4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutRecyclerAdapter(private var workouts: List<Workout>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //lateinit var workouts: ArrayList<Workout>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WorkoutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is WorkoutViewHolder -> {
                holder.bind(workouts[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    fun updateWorkoutsList(newList: List<Workout>){
        workouts = newList
    }

    class WorkoutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val workoutImage: ImageView =  itemView.findViewById(R.id.workout_img)
        private val durationTextView: TextView =  itemView.findViewById(R.id.duration_tv)
        private val intensityTextView: TextView =  itemView.findViewById(R.id.intensity_tv)
        private val muscleGroupTextView: TextView = itemView.findViewById(R.id.muscleGroup_tv)
        private val loadTextView: TextView = itemView.findViewById(R.id.load_tv)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_tv)

        fun bind(workout: Workout) {
            durationTextView.text = workout.duration.toString()
            intensityTextView.text = workout.intensity.toString()
            muscleGroupTextView.text = workout.muscleGroup.toString()
            loadTextView.text = workout.load.toString()
            dateTextView.text = workout.timestamp.toString()
/*
            when (workout.muscleGroup) {
                MuscleGroup.ARMS -> workoutImage.setImageResource(R.drawable.arms)
                MuscleGroup.TORSO -> workoutImage.setImageResource(R.drawable.torso)
                MuscleGroup.CORE -> workoutImage.setImageResource(R.drawable.core)
                MuscleGroup.LEGS -> workoutImage.setImageResource(R.drawable.legs)
            }*/

        }
    }


}