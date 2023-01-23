package hr.ferit.nancymatijas.androidproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment(),
    PlanetRecyclerAdapter.ContentListener
{
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: PlanetRecyclerAdapter
    private val planetList = ArrayList<Planet>()
    private lateinit var searchView : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


        val recyclerView = view.findViewById<RecyclerView>(R.id.planetsList)
        db.collection("planets").get()
            .addOnSuccessListener {
                for (data in it.documents) {
                    val planet = data.toObject(Planet :: class.java)
                    if(planet != null) {
                        planet.id = data.id
                        planetList.add(planet)
                    }
                }

                recyclerAdapter = PlanetRecyclerAdapter(planetList, this@MainFragment)

                recyclerView.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }

            }
            .addOnFailureListener {
                Log.e("Main activity", it.message.toString())
            }



        val addButton : ImageButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener{

            val bundle = Bundle()
            val fragment = AddFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_container, fragment)
            fragmentTransaction?.commit()
        }

        return view
    }



    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = java.util.ArrayList<Planet>()
            for (i in planetList) {
                if (i.name!!.contains(query) ||
                    i.name!!.lowercase(Locale.ROOT).contains(query) ||
                        i.name!!.uppercase(Locale.ROOT).contains(query)
                ) {
                    filteredList.add(i)
                }
            }
            recyclerAdapter.setFilteredList(filteredList)

        }
    }

    override fun onItemButtonClick(index: Int, planet: Planet, clickType: ItemClickType) {
        Log.d("Main activity", clickType.toString())

        if (clickType == ItemClickType.REMOVE) {
            recyclerAdapter.removeItem(index)
            db.collection("planets")
                .document(planet.id)
                .delete()
        }

    }

}