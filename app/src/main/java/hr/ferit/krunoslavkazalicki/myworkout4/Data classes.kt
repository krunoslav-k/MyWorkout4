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
    val age: Int = 0,
    val gender: Gender = Gender.MALE,
    val calorieIntake: Int = 0,
    val timestamp: Date = Date(1990, 1, 1)
)

data class Workout(
    val muscleGroup: MuscleGroup,
    val duration: Int,
    val intensity: Int
)