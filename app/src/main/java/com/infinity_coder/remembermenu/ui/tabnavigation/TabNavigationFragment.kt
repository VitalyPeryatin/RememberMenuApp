package com.infinity_coder.remembermenu.ui.tabnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.infinity_coder.remembermenu.R
import kotlinx.android.synthetic.main.fragment_tab_navigation.*

class TabNavigationFragment : Fragment(R.layout.fragment_tab_navigation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.frame_main) as NavHostFragment?

        if (navHostFragment != null)
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }
}