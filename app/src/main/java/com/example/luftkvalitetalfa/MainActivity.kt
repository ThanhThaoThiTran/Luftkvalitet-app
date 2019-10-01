package com.example.luftkvalitetalfa

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.luftkvalitetalfa.favoritt.FavoritterFragment
import com.example.luftkvalitetalfa.info.InfoFragment
import com.example.luftkvalitetalfa.maalestasjon.MaalestasjonFragment
import com.example.luftkvalitetalfa.map.MapFragment
import com.example.luftkvalitetalfa.service.NetworkAPI
import kotlinx.android.synthetic.main.activity_bottom_navigation_bar.*
import com.example.luftkvalitetalfa.service.Stasjonsliste


class MainActivity : AppCompatActivity(){

    private val maalestasjonFragment = MaalestasjonFragment()

        private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_info-> {
                changeFragment(InfoFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_maalestasjon-> {
                changeFragment(maalestasjonFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_kart -> {
                changeFragment(MapFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favoritter -> {
                    changeFragment(FavoritterFragment())
                    return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_kart
        changeFragment(MapFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
     }

    fun changeEOIFragment(eoi: String) {
        val bundle = Bundle()
        bundle.putString("EOI", eoi)
        maalestasjonFragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, maalestasjonFragment)
            .addToBackStack(null)
            .commit()

        navigation.selectedItemId = R.id.navigation_maalestasjon
    }
}

