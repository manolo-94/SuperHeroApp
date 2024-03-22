import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.levp.superheroapp.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroAdapter(
    var superHeroList: List<SuperHeroItemResponse> = emptyList(),

    /*
    * De esta manera enviamos un bloque de codigo como parametros a la clase adapter cuando se implemente en SuperHeroActivity.
    * Creamos una funcion lambda que servira para obtener el id de cada uno de los items dentro del Recycler view y poder ver su detalle
    */
    private val onItemSelected: (String) -> Unit

) : RecyclerView.Adapter<SuperHeroViewHolder>() {

    fun updateList(superHeroList: List<SuperHeroItemResponse>) {
        this.superHeroList = superHeroList
        notifyDataSetChanged()
    }

    /*
    Otra manera de hacerlo
    fun updateList(list: List<SuperHeroItemResponse>){
        superHeroList = list
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        )
        /*
        Manera Larga
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperHeroViewHolder(layoutInflater.inflate(R.layout.item_superhero, parent, false))
        */
    }

    override fun getItemCount() = superHeroList.size


    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(
            superHeroList[position],

            /*
            * Le pasamos la funcion lambda o el bloque de codigo a cada uno de los items pero sin los parentesis para que no se ejecute.
            * Nota: al pasar la funcion con parentesis se ejecutara cada vez que se llame
            * */
            onItemSelected
        )
        /*
        Mannera larga
        val item = superHeroList[position]
        holder.bind(item)
        */
    }

}