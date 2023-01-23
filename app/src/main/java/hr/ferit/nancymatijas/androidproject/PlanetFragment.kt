package hr.ferit.nancymatijas.androidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class PlanetFragment : Fragment()
{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_planet, container, false)

        val textView_name : TextView = view.findViewById(R.id.title_info)
        textView_name.text = arguments?.getString("name_id").toString()

        val textView_galaxy : TextView = view.findViewById(R.id.galaxy_info)
        textView_galaxy.text = arguments?.getString("galaxy_id").toString()

        val textView_distance : TextView = view.findViewById(R.id.distance_info)
        textView_distance.text = arguments?.getString("distance_id").toString()

        val textView_gravity : TextView = view.findViewById(R.id.gravity_info)
        textView_gravity.text = arguments?.getString("gravity_id").toString()

        val textView_text : TextView = view.findViewById(R.id.overview_info)
        textView_text.text = arguments?.getString("text_id").toString()



        val forwardButton : ImageButton = view.findViewById(R.id.astronautButton)
        forwardButton.setOnClickListener{
            val bundle = Bundle()
            val fragment = LastFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_container, fragment)
            fragmentTransaction?.commit()
        }

        val backwardButton : ImageButton = view.findViewById(R.id.backwardButton1)
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

}
