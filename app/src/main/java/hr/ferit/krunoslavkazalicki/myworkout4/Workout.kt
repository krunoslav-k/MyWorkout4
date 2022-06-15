package hr.ferit.krunoslavkazalicki.myworkout4

import java.util.*

data class Workout(
    var muscleGroup: MuscleGroup? = null,
    var duration: Int? = null,
    var intensity: Int? = null,
    var load: Int? = null,
    var timestamp: Date? = null
)