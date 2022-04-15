package com.example.dailyqoutesmotivational

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.dailyqoutesmotivational.databinding.ActivityMainBinding
import com.example.dailyqoutesmotivational.ui.CategoryFragment
import com.example.dailyqoutesmotivational.ui.DownloadFragment
import com.example.dailyqoutesmotivational.ui.HomeFragment
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.google.android.material.navigation.NavigationView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBinding()
        setDrawer()
        // setDefaultFragment(savedInstanceState)
        setDrawerToggle()

        adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(CategoryFragment(), "Category")
        adapter.addFragment(DownloadFragment(), "Download")
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                binding.bottomNavigationViewLinear.setCurrentActiveItem(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })

        binding.bottomNavigationViewLinear.setNavigationChangeListener { view, position ->
            binding.viewPager.setCurrentItem(
                position,
                true
            )
        }
    }

    private fun setDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.toolbar.setTitleTextColor(Color.WHITE)
        binding.toolbar.post {
            val d = ResourcesCompat.getDrawable(resources, R.drawable.ic_navigationicon, null)
            binding.toolbar.navigationIcon = d
        }
        binding.toolbar.overflowIcon!!.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setDrawerToggle() {
        drawerToggle = setupDrawerToggle()
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        binding.drawerLayout.addDrawerListener(drawerToggle)
        setupDrawerContent(binding.navView)
    }

    /*  private fun setDefaultFragment(savedInstanceState: Bundle?) {
          if (savedInstanceState == null) {
              val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
              fragmentTransaction.add(
                  R.id.flContent,
                  HomeFragment()
              )
              fragmentTransaction.addToBackStack(null)
              fragmentTransaction.commit()
          }
      }*/

    private fun setFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) { //fragment not in back stack, create it.
            val ft = manager.beginTransaction()
            ft.replace(R.id.flContent, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }
    /*  private fun setFragment(fragment: Fragment) {
          val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
          fragmentTransaction.add(
              R.id.flContent,
              fragment
          )
          fragmentTransaction.addToBackStack(null)
          fragmentTransaction.commit()

      }*/

    // to use three dots
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //  Inflate the menu; this adds items to the action bar if it is present.
        //  menuInflater.inflate(R.menu.toolbar_menu, menu)
        //  setMenuIcons(menu)
        return true
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            false
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                closeDrawer()
                binding.viewPager.setCurrentItem(
                    0,
                    true
                )
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_categories -> {
                if (isNetworkAvailable(applicationContext!!)) {
                    binding.viewPager.setCurrentItem(
                        1,
                        true
                    )
                    Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please connect to  Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeDrawer()
                }
            }
            R.id.nav_download -> {
                if (isNetworkAvailable(applicationContext!!)) {
                    binding.viewPager.setCurrentItem(
                        2,
                        true
                    )
                    Toast.makeText(this, "Download", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please connect to   Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeDrawer()
                }
            }
            R.id.nav_youtube -> {
                if (isNetworkAvailable(applicationContext!!)) {
                    Toast.makeText(this, "Youtube", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please connect to  Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeDrawer()
                }
            }
            R.id.nav_instagram -> {
                if (isNetworkAvailable(applicationContext!!)) {
                    Toast.makeText(this, "Instagram", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please connect to  Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeDrawer()
                }
            }
            R.id.nav_facebook -> {
                if (isNetworkAvailable(applicationContext!!)) {
                    Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please connect to  Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeDrawer()
                }
            }
            else ->
                closeDrawer()
        }

        closeDrawer()
    }

    private fun goToVideos() {
        //  startActivity(Intent(applicationContext, VideoListingActivity::class.java))
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            exitAlert()
        }
    }

    private fun exitAlert() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null)
        val dialog: Dialog
        val positiveBtn = view.findViewById<Button>(R.id.positiveBtn)
        val negativeBtn = view.findViewById<Button>(R.id.negativeBtn)
        val subTitleText = view.findViewById<TextView>(R.id.subTitleText)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(view)
        dialog = builder.create()
        negativeBtn.setOnClickListener {
            dialog.dismiss()
        }
        positiveBtn.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setDimAmount(0.5f)
        subTitleText.setText(R.string.exit_dialog)
        dialog.show()
    }

    class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}