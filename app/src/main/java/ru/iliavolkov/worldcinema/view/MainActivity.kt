package ru.iliavolkov.worldcinema.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.ActivityMainBinding
import ru.iliavolkov.worldcinema.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //убрали верхнуюю строку состояния
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    MainFragment.newInstance()
            ).commit()
        }
    }
}