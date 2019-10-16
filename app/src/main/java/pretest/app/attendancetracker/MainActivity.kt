package pretest.app.attendancetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pretest.app.attendancetracker.fragments.ServicesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, ServicesFragment())
            .commit()
    }
}
