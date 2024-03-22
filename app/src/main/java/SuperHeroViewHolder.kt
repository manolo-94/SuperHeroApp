import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.levp.superheroapp.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)
    fun bind(superHeroItemResponse: SuperHeroItemResponse,
             onItemSelected: (String) -> Unit
    ) {

        binding.tvSuperheroName.text = superHeroItemResponse.name
        Picasso.get().load(superHeroItemResponse.image.url).into(binding.ivSuperHero)

        /*
        * Cada vez que que se haga click en un item se llamara el evento setOnClickListener el cual se encargara se llamar a onItemSelected el cual contiene un metodo
        */
        binding.root.setOnClickListener { onItemSelected(superHeroItemResponse.id) }

    }
}