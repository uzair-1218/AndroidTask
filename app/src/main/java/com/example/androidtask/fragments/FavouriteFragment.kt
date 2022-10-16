package com.example.androidtask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.adapter.FavouriteAdapter
import com.example.androidtask.databinding.FragmentFavouriteBinding
import com.example.androidtask.fragments.viewmodel.UniversalViewModel
import com.example.androidtask.room.DatabaseClass
import com.example.androidtask.room.Entity


class FavouriteFragment : Fragment() {


    lateinit var binding:FragmentFavouriteBinding
    lateinit var databaseClass: DatabaseClass
    var listOfDrinks: MutableList<Entity> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        databaseClass = DatabaseClass(requireContext())
        val viewModel = ViewModelProvider(this).get(UniversalViewModel::class.java)

        binding.favRecycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavouriteAdapter()
        binding.favRecycler.adapter = adapter
       viewModel.getAllData.observe(viewLifecycleOwner, Observer {

           adapter.setData(it)
       })

        return binding.root
    }


}