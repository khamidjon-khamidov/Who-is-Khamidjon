package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.SkillsListAdapter.SkillClickListener
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsStateEvent.GetSkillsListStateEvent
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.TopSpacingItemDecoration
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners
import kotlinx.android.synthetic.main.fragment_skills_list.*

class SkillsListFragment : BaseSkillsFragment(), SkillClickListener{

    private val TAG = "AppDebug"

    private lateinit var recyclerAdapter: SkillsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SkillsListFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_skills_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setActionBarTitle("My Skills")
        mainStateChangeListener.lockDrawer(false, R.id.menu_item_skills)
        setLeftDrawerListeners()

        Log.d(TAG, "SkillsListFragment: onViewCreated: ")

        setLeftDrawerListeners()
        initRecyclerView()

        if(viewModel.viewState.value?.skillsListFields?.skillsList==null){
            subscribeObservers()
            viewModel.setStateEvent(GetSkillsListStateEvent())
        } else {
            updateView(viewModel.viewState.value!!.skillsListFields.skillsList)
        }
    }

    private fun updateView(skillsList: List<SkillModel>?) {
        skillsList?.let { recyclerAdapter.submitList(it) }
    }

    private fun subscribeObservers() {
        Log.d(TAG, "SkillsListFragment: subscribeObservers: 1")

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            Log.d(TAG, "SkillsListFragment: subscribeObservers: 2")
            if(dataState!=null){
                dataState.data?.dataReceived?.getContentIfNotHandled()?.skillsListFields?.let {
                    Log.d(TAG,
                        "SkillsListFragment: subscribeObservers: skillsListFields: ${it.skillsList}")

                    viewModel.setSkillsListFields(it)
                    stateChangeListener.onDataStateChange(dataState)
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState->
            if(viewState != null){
                Log.d(TAG, "SkillsListFragment: subscribeObservers: 3")

                viewState.skillsListFields.skillsList?.let { skillsList ->
                    Log.d(TAG, "SkillsListFragment: subscribeObservers: size: ${skillsList.size} ")
                    updateView(skillsList)
                    recyclerAdapter.preloadGlideImages(skillsList)
                }
            }
        })
    }

    private fun initRecyclerView() {
        rv_skills_list.apply {
            layoutManager = LinearLayoutManager(this@SkillsListFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)

            recyclerAdapter = SkillsListAdapter(
                dependencyProvider.getGlideRequestManager(),
                this@SkillsListFragment
            )

            adapter = recyclerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        MainNavigation.setSelected(activity!! as MainActivity, R.id.menu_item_skills)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SkillsListFragment: onDestroy: ")
    }

    override fun onSkillClick(pos: Int, skill: SkillModel) {
        viewModel.setCurrentSelectedItemPosition(pos)
        findNavController().navigate(R.id.action_skillsListFragment_to_skillsDetailsFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_refresh, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_item_refresh -> {
                viewModel.setStateEvent(GetSkillsListStateEvent())
                return true
            }
        }

        return false
    }

}




















