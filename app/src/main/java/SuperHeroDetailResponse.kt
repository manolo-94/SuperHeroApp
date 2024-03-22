import com.google.gson.annotations.SerializedName

data class SuperHeroDetailResponseById(
    @SerializedName("response") val response: String,
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: SuperHeroPowerStats,
    @SerializedName("image") val image: SuperHeroImageDetailResponse,
    @SerializedName("biography") val biography: SuperHeroBiography,
)

data class SuperHeroPowerStats(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String,
    )

data class SuperHeroImageDetailResponse(@SerializedName("url") val url:String)

data class SuperHeroBiography(
    @SerializedName("full-name") val full_name: String,
    @SerializedName("publisher") val publisher: String,
)