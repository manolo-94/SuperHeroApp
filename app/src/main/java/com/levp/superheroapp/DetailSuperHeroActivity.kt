package com.levp.superheroapp

import ApiService
import SuperHeroDetailResponseById
import SuperHeroImageDetailResponse
import SuperHeroPowerStats
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.levp.superheroapp.databinding.ActivityDetailSuperHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {


    companion object {
        const val KEY_EXTRA_ID = "key_extra_id"
    }

    private lateinit var binding: ActivityDetailSuperHeroBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListener()
    }


    private fun initListener() {
        retrofit = getRetrofit()
        val id: String = intent.getStringExtra(KEY_EXTRA_ID).orEmpty()
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {
        binding.pbSuperHeroDetail.isVisible = true
        Log.i("MANOLODEV", id)
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperHeroDetailResponseById> = retrofit.create(ApiService::class.java).getSuperHeroById(id)
            if (myResponse.isSuccessful){

                if (myResponse.body() != null){
                    runOnUiThread{
                        /*Log.i("MANOLODEV", response.toString())*/
                        creteUI(myResponse.body()!!)
                        binding.pbSuperHeroDetail.isVisible = false
                    }
                }
            }else{
                Log.i("MANOLODEV", "NOT SUCCESSFUL")
            }
        }
    }

    private fun creteUI(superHero: SuperHeroDetailResponseById) {
        Picasso.get().load(superHero.image.url).into(binding.ivSuperHeroDetail)
        binding.tvSuperHeroDetailName.text = superHero.name
        binding.tvSuperHeroDetailFullName.text = superHero.biography.full_name
        binding.tvSuperHeroDetailPublisher.text = superHero.biography.publisher
        prepareStats(superHero.powerstats)
    }

    private fun prepareStats(powerstats: SuperHeroPowerStats) {
        /*intelligence
        strength
        speed
        durability
        power
        combat*/
        updateHeight(binding.vIntelligence, powerstats.intelligence)
        updateHeight(binding.vStrength, powerstats.strength)
        updateHeight(binding.vSpeed, powerstats.speed)
        updateHeight(binding.vDurability, powerstats.durability)
        updateHeight(binding.vPower, powerstats.power)
        updateHeight(binding.vCombat, powerstats.combat)
    }

    private fun updateHeight(view: View, stat:String){
        val params = view.layoutParams
        params.height = pixelToDp(stat.toFloat())
        view.layoutParams = params
    }

    private fun pixelToDp(px:Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}