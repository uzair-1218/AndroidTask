package com.example.androidtask.fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.adapter.DrinksAdapter
import com.example.androidtask.databinding.FragmentRecipesBinding
import com.example.androidtask.fragments.viewmodel.UniversalViewModel
import com.example.androidtask.room.DatabaseClass
import com.example.androidtask.room.Entity
import com.example.androidtask.utils.AppUtils
import com.example.androidtask.utils.OnItemClicked
import com.pixplicity.easyprefs.library.Prefs
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class RecipesFragment : Fragment() {


    lateinit var binding:FragmentRecipesBinding
    lateinit var database:DatabaseClass

    var isByname:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(layoutInflater,container,false)

        database = DatabaseClass(requireContext())
        val viewModel = ViewModelProvider(this).get(UniversalViewModel::class.java)

       if (isByname){
           if (Prefs.getString(AppUtils.BY_NAME_KEY,"").isNotEmpty()){
               binding.searchEdt.text = Editable.Factory.getInstance().newEditable(Prefs.getString(AppUtils.BY_NAME_KEY,""))
           }
       }
       else {
           if (Prefs.getString(AppUtils.By_ALPHABET,"").isNotEmpty()){
               binding.searchEdt.text = Editable.Factory.getInstance().newEditable(Prefs.getString(AppUtils.By_ALPHABET,""))
           }
       }

        binding.recipeRecycler.layoutManager =  LinearLayoutManager(requireContext())

        binding.typeFieldSelector.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.by_name_radio -> {

                    isByname=true
                    binding.searchEdt.text = Editable.Factory.getInstance().newEditable(Prefs.getString(AppUtils.BY_NAME_KEY,""))

                }
                R.id.by_alphabet -> {

                   isByname=false

                    binding.searchEdt.text = Editable.Factory.getInstance().newEditable(Prefs.getString(AppUtils.By_ALPHABET,""))

                }

            }
        })



        binding.searchImg.setOnClickListener {

            if (isByname){
                Prefs.putString(AppUtils.BY_NAME_KEY,binding.searchEdt.text.toString())
                viewModel.getAllDrinksRecipes(binding.searchEdt.text.toString())
                viewModel.drinks_get_by_name.observe(requireActivity(), Observer {
                    if (it.drinks!=null){
                        val adapter:DrinksAdapter = DrinksAdapter(it.drinks,object:OnItemClicked{
                            override fun onClick(position: Int) {

                                val SDK_INT = Build.VERSION.SDK_INT
                                if (SDK_INT > 8) {
                                    val policy = ThreadPolicy.Builder()
                                        .permitAll().build()
                                    StrictMode.setThreadPolicy(policy)
                                    val bitmap = getBitmap(it.drinks.get(position).strDrinkThumb!!)

                                    if (it.drinks.get(position).strInstructionsDE!=null){
                                        val entity = Entity(it.drinks.get(position).strDrink!!,
                                            it.drinks.get(position).strInstructionsDE!!,it.drinks.get(position).strAlcoholic!!,bitmap!!)

                                        viewModel.addDrinks(entity)
//                                        database.drinkdao().insertDrinks(entity)
                                    }else{
                                        val entity = Entity(it.drinks.get(position).strDrink!!,
                                          "",it.drinks.get(position).strAlcoholic!!,bitmap!!)
                                        viewModel.addDrinks(entity)
  //                                      database.drinkdao().insertDrinks(entity)
                                    }

                                }




                            }

                        })

                        binding.recipeRecycler.adapter = adapter

                        adapter.notifyDataSetChanged()
                    }
                })

            }
            else{
                Prefs.putString(AppUtils.By_ALPHABET,binding.searchEdt.text.toString())

                viewModel.getDrinksByAlphabet(binding.searchEdt.text.toString())
                viewModel.drinks_get_by_alphabet.observe(requireActivity(), Observer {
                    if (it.drinks!=null){
                        val adapter:DrinksAdapter = DrinksAdapter(it.drinks,object:OnItemClicked{
                            override fun onClick(position: Int) {
                                val SDK_INT = Build.VERSION.SDK_INT
                                if (SDK_INT > 8) {
                                    val policy = ThreadPolicy.Builder()
                                        .permitAll().build()
                                    StrictMode.setThreadPolicy(policy)
                                    val bitmap = getBitmap(it.drinks.get(position).strDrinkThumb!!)

                                    if (it.drinks.get(position).strInstructionsDE!=null){
                                        val entity = Entity(it.drinks.get(position).strDrink!!,
                                            it.drinks.get(position).strInstructionsDE!!,it.drinks.get(position).strAlcoholic!!,bitmap!!)

                                        viewModel.addDrinks(entity)
//                                        database.drinkdao().insertDrinks(entity)
                                    }else{
                                        val entity = Entity(it.drinks.get(position).strDrink!!,
                                            "",it.drinks.get(position).strAlcoholic!!,bitmap!!)
                                        viewModel.addDrinks(entity)
                                        //                                      database.drinkdao().insertDrinks(entity)
                                    }

                                }
                            }

                        })

                        binding.recipeRecycler.adapter = adapter

                        adapter.notifyDataSetChanged()
                    }
                })
            }

        }
        return binding.root
    }




    fun getBitmap(src: String?): Bitmap? {
        return try {

            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
          null
        }
    }
}