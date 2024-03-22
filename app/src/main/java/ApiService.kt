import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/key/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superheroName:String): Response<SuperHeroDataResponse>

    @GET("api/key/{id}")
    suspend fun getSuperHeroById(@Path("id") superHeroId: String):Response<SuperHeroDetailResponseById>
}