package hr.ferit.nancymatijas.androidproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddFragment : Fragment(),
    PlanetRecyclerAdapter.ContentListener{


    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: PlanetRecyclerAdapter
    val recyclerView = view?.findViewById<RecyclerView>(R.id.planetsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view=inflater.inflate(R.layout.fragment_add, container, false)
        // Inflate the layout for this fragment

        val inputButton = view.findViewById<Button>(R.id.saveButton)
        val inName = view.findViewById<TextView>(R.id.inputName)
        val inGravity = view.findViewById<TextView>(R.id.inputGravity)
        val inGalaxy = view.findViewById<TextView>(R.id.inputGalaxy)
        val inDistance = view.findViewById<TextView>(R.id.inputDistance)
        val inText = view.findViewById<TextView>(R.id.inputText)
        val inImg = view.findViewById<TextView>(R.id.inputImgUrl)

            db.collection("planets").get()
                .addOnSuccessListener {
                    var planetList = ArrayList<Planet>()
                    for (data in it.documents) {
                        val person = data.toObject(Planet :: class.java)
                        if(person != null) {
                            person.id = data.id
                            planetList.add(person)
                        }
                    }
                    recyclerAdapter = PlanetRecyclerAdapter(planetList, this@AddFragment)

                    recyclerView?.apply{
                        layoutManager = LinearLayoutManager(context)
                        adapter = recyclerAdapter
                    }
                }
                .addOnFailureListener {
                    Log.e("Main activity", it.message.toString())
                }


        inputButton.setOnClickListener{
            val planet = Planet()
            planet.name = inName.text.toString()
            planet.galaxy = inGalaxy.text.toString()
            planet.gravity = inGravity.text.toString()
            planet.distance = inDistance.text.toString()
            planet.text = inText.text.toString()
            planet.imageUrl = inImg.text.toString()

            db.collection("planets")
                .add(planet)
                .addOnSuccessListener {
                    planet.id = it.id
                    recyclerAdapter.addItem(planet)
                }

            inName.text = ""
            inGalaxy.text = ""
            inDistance.text = ""
            inGravity.text = ""
            inText.text = ""
            inImg.text = ""
            }


        val backwardButton : ImageButton = view.findViewById(R.id.backwardButton3)
        backwardButton.setOnClickListener{
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_container, fragment)
            fragmentTransaction?.commit()
        }

        return view
    }

    override fun onItemButtonClick(index: Int, planet: Planet, clickType: ItemClickType) {
        Log.d("Main activity", clickType.toString())

    }

}