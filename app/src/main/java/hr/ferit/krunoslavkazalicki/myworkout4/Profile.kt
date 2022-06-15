package hr.ferit.krunoslavkazalicki.myworkout4

import java.util.*

data class Profile(
    var height: Int? = null,
    var weight: Int? = null,
    var age: Int? = null,
    var gender: Gender? = null,
    var calorieIntake: Int? = null,
    var timestamp: Date? = null
)