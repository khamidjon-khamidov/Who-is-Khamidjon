package com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.PersonalInfo.phoneNumber
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.state.ContactMeViewState.Companion.TYPE_SEND_EMAIL
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.state.ContactMeViewState.Companion.TYPE_SEND_MESSAGE
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners
import kotlinx.android.synthetic.main.fragment_contact_type.*

class ContactTypeFragment : BaseContactMeFragment() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "ContactTypeFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_type, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle("Contact Me")

        mainStateChangeListener.lockDrawer(false, R.id.menu_item_contact)
        setLeftDrawerListeners()

        setLeftDrawerListeners()

        initializeButtons()
    }

    fun initializeButtons(){
        btn_send_message_contact_type.setOnClickListener{
            viewModel.setContactType(TYPE_SEND_MESSAGE)
            findNavController().navigate(R.id.action_contactTypeFragment_to_contactFragment)
        }

        btn_send_email_contact_type.setOnClickListener{
            viewModel.setContactType(TYPE_SEND_EMAIL)
            findNavController().navigate(R.id.action_contactTypeFragment_to_contactFragment)
        }

        btn_call_me_contact_type.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_DIAL)
            val uri = "tel:$phoneNumber"
            myIntent.data = Uri.parse(uri)
            startActivity(myIntent)

        }
    }

    override fun onResume() {
        super.onResume()
        MainNavigation.setSelected(activity!! as MainActivity, R.id.menu_item_contact)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ContactTypeFragment: onDestroy: ")
    }
}





















