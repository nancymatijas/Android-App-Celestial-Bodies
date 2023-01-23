package hr.ferit.nancymatijas.androidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


enum class ItemClickType {
    REMOVE
}


class PlanetRecyclerAdapter(
    var items: ArrayList<Planet>,
    val listener: ContentListener,
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlanetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlanetViewHolder -> {
                holder.bind(position, items[position],listener)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }

    fun addItem(planet: Planet){
        items.add(0,planet)
        notifyItemInserted(0)
    }

    fun setFilteredList(items: ArrayList<Planet>) {
        this.items = items
        notifyDataSetChanged()
    }

    class PlanetViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private val planetImage = view.findViewById<ImageView>(R.id.planetImg)
        private val planetName = view.findViewById<TextView>(R.id.planetName)
        private val galaxyName = view.findViewById<TextView>(R.id.galaxyName)
        private val distance = view.findViewById<TextView>(R.id.distance)
        private val gravity = view.findViewById<TextView>(R.id.gravity)
        private val deleteBtn = view.findViewById<ImageButton>(R.id.deleteButton)
        private val detailBtn = view.findViewById<ImageButton>(R.id.touchButton)

        fun bind(
            index: Int,
            planet: Planet,
            listener: ContentListener,
        )
        {
            Glide.with(view.context).load(planet.imageUrl).into(planetImage)
            planetName.text = planet.name
            galaxyName.text = planet.galaxy
            distance.text = planet.distance
            gravity.text = planet.gravity

            deleteBtn.setOnClickListener {
                listener.onItemButtonClick(index, planet, ItemClickType.REMOVE)
            }


            detailBtn.setOnClickListener(object :View.OnClickListener {
                override fun onClick(v: View?) {
                    val activity = v!!.context as AppCompatActivity

                    val bundle = Bundle()
                    bundle.putString("name_id", planet.name)
                    bundle.putString("galaxy_id", planet.galaxy)
                    bundle.putString("gravity_id", planet.gravity)
                    bundle.putString("distance_id", planet.distance)
                    bundle.putString("text_id", planet.text)

                    val planetFragment = PlanetFragment()
                    planetFragment.arguments=bundle
                    activity.supportFragmentManager.beginTransaction().replace(R.id.nav_container, planetFragment).commit()

                }
            })

        }

    }

    interface ContentListener {
        fun onItemButtonClick(
            index: Int, planet: Planet, clickType: ItemClickType,
        )
    }

}



