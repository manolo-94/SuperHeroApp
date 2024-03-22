package com.levp.superheroapp

import ApiService
import SuperHeroAdapter
import SuperHeroDataResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.levp.superheroapp.DetailSuperHeroActivity.Companion.KEY_EXTRA_ID
import com.levp.superheroapp.databinding.ActivitySuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperHeroBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperHeroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retrofit = getRetrofit()
        initUI()

    }

    private fun initUI() {
        binding.svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            /*Este metodo es llamado cuando se preciona el boton busca*/
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        /*
        * De esta manera encampsulamos el codigo de la funcion navigateToDetail y se lo mandamos a cada uno de los items dentro del RecyclerView
        * Nota: hay varias maneras de pasara el parametro
        * 1- adapter = SuperHeroAdapter{navigateToDetail(it)}
        * 2- adapter = SuperHeroAdapter{superHeroId -> navigateToDetail(superHeroId)}
        * Ambas maneras son correctas.
        */
        adapter = SuperHeroAdapter {superHeroId -> navigateToDetail(superHeroId)}
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.pbSuperHero.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperHeroes(query)

            if(myResponse.isSuccessful){
                Log.i("MANOLODEV","SUCCESSFUL")
                val response: SuperHeroDataResponse? = myResponse.body()
                if (response != null){
                    Log.i("MANOLODEV",response.toString())
                    runOnUiThread {
                        adapter.updateList(response.results)
                        binding.pbSuperHero.isVisible = false
                    }

                }
            }else{
                Log.i("MANOLODEV","NOT SUCCESSFUL")
            }
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id:String){
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(KEY_EXTRA_ID,id)
        startActivity(intent)
    }
}