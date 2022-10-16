package com.example.androidtask.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtask.fragments.FavouriteFragment
import com.example.androidtask.fragments.RecipesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifeCycle : Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifeCycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when(position){
            0 -> return  RecipesFragment()
            1 -> return FavouriteFragment()
        }
        return RecipesFragment()
    }


}