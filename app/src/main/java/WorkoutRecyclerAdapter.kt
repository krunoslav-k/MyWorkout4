import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.krunoslavkazalicki.myworkout4.R
import hr.ferit.krunoslavkazalicki.myworkout4.Workout

class WorkoutRecyclerAdapter(val workouts: ArrayList<Workout>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WorkoutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is WorkoutViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    class WorkoutViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val workoutImage: ImageView =  itemView.findViewById(R.id.workout_img)
        private val durationTextView: TextView =  itemView.findViewById(R.id.duration_tv)
        private val intensityTextView: TextView =  itemView.findViewById(R.id.intensity_tv)
        private val muscleGroupTextView: TextView = itemView.findViewById(R.id.muscleGroup_tv)
        private val loadTextView: TextView = itemView.findViewById(R.id.load_tv)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_tv)

        fun bind(workouts: Workout) {
            Glide.with
        }
    }

   // class WorkoutViewHolder(inflate: Any?) : RecyclerView.ViewHolder() {}

}