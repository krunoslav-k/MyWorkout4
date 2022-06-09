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
    var age: Int = 0,
    var gender: Gender = Gender.MALE,
    var calorieIntake: Int = 0,
    var timestamp: Date = Date(1990, 1, 1)
)

data class Workout(
    val muscleGroup: MuscleGroup,
    val duration: Int,
    val intensity: Int
)