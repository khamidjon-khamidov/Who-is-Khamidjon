package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.fragment.findNavController
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import kotlinx.android.synthetic.main.fragment_skills_details.*

class SkillsDetailsFragment : BaseSkillsFragment() {

    private val TAG = "AppDebug"
    private var curItemId = -1
    private var currentItem: SkillModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SkillsDetailsFragment: onCreate: ")
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        mainStateChangeListener.closeLeftNavigationMenu()
        mainStateChangeListener.lockDrawer(true, R.id.menu_item_skills)

        curItemId = viewModel.viewState.value?.currentSelectedItemPosition ?: -1
        currentItem = viewModel.viewState.value?.skillsListFields?.skillsList?.get(curItemId)

        setActionBarTitle(currentItem?.name?:"Error")

        updateView()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun updateView(){
        tv_description_skill_details.setText(currentItem?.description ?: "Unknown Error")

        dependencyProvider.getGlideRequestManager()
            .load(currentItem?.image_url)
            .placeholder(R.drawable.default_image)
            .into(iv_main_skill_details)



//        val photoView = PhotoViewAttacher(iv_main_skill_details)
//        photoView.update()

    }

    override fun onDestroy() {
        super.onDestroy()
//        mainStateChangeListener.lockDrawer(false, R.id.menu_item_contact)
//        setLeftDrawerListeners()
        Log.d(TAG, "SkillsDetailsFragment: onDestroy: ")
    }
}
