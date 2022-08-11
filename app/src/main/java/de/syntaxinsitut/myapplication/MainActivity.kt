package de.syntaxinsitut.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import de.syntaxinsitut.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        toolbar = binding.toolbar
        bottomNavigation = binding.bottomNavigation
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


//        spricht die NavigationsView an

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_share -> {
                    var detailText = ""
                    println("Teilen Fragment clicked")
                    val intent = Intent.createChooser(
                        Intent()
                            .apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Ich möchte die Notiz mit Dir  $detailText Teilen :)"
                                )
                                type = "text/plain"
                            }, null
                    )
                    startActivity(intent)
                    true
                }

            }
            true
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_holder) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigation.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {

            navController.navigateUp()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                println("CLICK:!" + item)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


}
