package hr.ferit.krunoslavkazalicki.myworkout4

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.profileFragment, R.id.logFragment, R.id.historyFragment))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //linija komentirana jer sam u themes.xml promijenio u NoActionBar => https://stackoverflow.com/questions/47951491/app-crashes-after-chaning-theme-to-noactionbar

        bottomNavigationView.setupWithNavController(navController)
    }
}