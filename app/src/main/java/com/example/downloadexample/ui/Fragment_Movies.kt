package com.example.downloadexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.downloadexample.R
import com.example.downloadexample.adapter.FilesAdapter
import com.example.downloadexample.utils.FunctionUtils
import com.example.downloadexample.viewModels.FragmentMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment__movies.*

@AndroidEntryPoint
class Fragment_Movies : BaseFragment() {


    val viewModel : FragmentMoviesViewModel by lazy {
        ViewModelProvider(this).get (FragmentMoviesViewModel :: class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRV()
        getMovies()
     //getlocalmovies()


    }

    // used when Sever Is Down for testing only
    fun getlocalmovies () {
        viewModel.addfiles()
        moviesRV.adapter = activity?.let { it1 -> FilesAdapter (it1,requireContext(), viewModel.files) }
        ( moviesRV.adapter as FilesAdapter).notifyDataSetChanged()
        progress.visibility = View.GONE
    }

    fun getMovies () {
        if (FunctionUtils.isConnected(requireContext())) {
            noconnection.visibility = View.GONE
            progress.visibility = View.VISIBLE
            viewModel.getMovies()
            viewModel.filesLiveData.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    moviesRV.adapter = activity?.let { it1 -> FilesAdapter (it1,requireContext(), viewModel.files) }
                    ( moviesRV.adapter as FilesAdapter).notifyDataSetChanged()
                    progress.visibility = View.GONE

                }
            })
        }
        else {
            noconnection.visibility = View.VISIBLE
            progress.visibility = View.GONE

            val toast =
                Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT)
            toast.show()



        }
    }


    fun initRV () {

        val layoutManager = LinearLayoutManager(requireContext())
       moviesRV .layoutManager = layoutManager
    }





}