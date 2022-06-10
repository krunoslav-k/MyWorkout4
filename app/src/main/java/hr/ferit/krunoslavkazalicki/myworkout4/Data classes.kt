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
    var height: Int,
    var weight: Int,
    var age: Int,
    var gender: Gender,
    var calorieIntake: Int,
    var timestamp: Date
)

data class Workout(
    val muscleGroup: MuscleGroup,
    val duration: Int,
    val intensity: Int,
    var timestamp: Date
)