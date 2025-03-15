package com.almax.giphy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.almax.giphy.databinding.ActivityMainBinding
import com.almax.giphy.ui.favourites.FavouritesFragment
import com.almax.giphy.ui.gif.GifFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNavigationView: BottomNavigationView = binding.navView
        val gifFragment = GifFragment.newInstance()
        val favouritesFragment = FavouritesFragment.newInstance()

        bottomNavigationView.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }

        setCurrentFragment(gifFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navGif -> {
                    setCurrentFragment(gifFragment)
                }

                R.id.navFav -> {
                    setCurrentFragment(favouritesFragment)
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, fragment)
                .commit()
        }
    }
}