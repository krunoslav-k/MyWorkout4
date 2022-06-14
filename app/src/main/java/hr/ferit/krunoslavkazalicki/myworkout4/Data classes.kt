package hr.ferit.krunoslavkazalicki.myworkout4

import java.sql.Timestamp
import java.util.*

enum class Gender {
    MALE, FEMALE
}

enum class MuscleGroup {
    ARMS, TORSO, CORE, LEGS
}

data class Profile(
    var height: Int? = null,
    var weight: Int? = null,
    var age: Int? = null,
    var gender: Gender? = null,
    var calorieIntake: Int? = null,
    var timestamp: Date? = null
)

data class Workout(
    var muscleGroup: MuscleGroup? = null,
    var duration: Int? = null,
    var intensity: Int? = null,
    var load: Int? = null,
    var timestamp: Date? = null
)