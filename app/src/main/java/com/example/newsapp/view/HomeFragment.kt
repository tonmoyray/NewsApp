package com.example.newsapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.component
import com.example.newsapp.data.local.ArticleDatabaseModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.utils.CommonMethods
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass


class HomeFragment : Fragment() , GenericAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private var TAG : String  = HomeFragment::class.java.simpleName

    @Inject
    lateinit var commonMethods: CommonMethods

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : FragmentHomeBinding
    private lateinit var genericAdapter: GenericAdapter<Any>
    private lateinit var activity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        commonMethods.printLog(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false)
        activity = getActivity() as MainActivity

        binding.homeFragment.setOnRefreshListener {
            binding.errorText.visibility = View.GONE
            viewModel.fetchLatestTopHeadlines(Constants.API_KEY_VALUE, Constants.COUNTRY_VALUE)
        }

        genericAdapter = GenericAdapter(R.layout.listitem_article, ArticleDatabaseModel::class as KClass<Any>,  this)
        binding.newsList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.newsList.adapter = genericAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.mutableArticleList.observe(viewLifecycleOwner, Observer {
            binding.homeFragment.isRefreshing = false
            binding.loading.visibility = View.GONE
            if(it.isEmpty()){
                binding.errorText.visibility = View.VISIBLE
            }else{
                genericAdapter.addDataList(it)
                binding.newsList.visibility = View.VISIBLE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            binding.homeFragment.isRefreshing = false

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                binding.errorText.visibility = View.GONE
                binding.homeFragment.isRefreshing = true
                viewModel.fetchLatestTopHeadlines(Constants.API_KEY_VALUE, Constants.COUNTRY_VALUE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun <T> onItemClick(item: T) {
        if(findNavController().currentDestination?.id == R.id.homeFragment){
            val bundle = Bundle()
            bundle.putString(Constants.HTML_DATA_TAG, (item as ArticleDatabaseModel).webUrl)
            findNavController().navigate(R.id.showNewsDetailsFragment, bundle)
        }
    }
}
