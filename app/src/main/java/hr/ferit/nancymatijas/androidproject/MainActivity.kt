package hr.ferit.nancymatijas.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.nav_container, MainFragment()).commit()


    }

}