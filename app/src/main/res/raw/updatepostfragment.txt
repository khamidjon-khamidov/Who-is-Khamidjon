package com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.DialogButtonClickListener
import com.hamidjonhamidov.whoiskhamidjon.ui.displayDialog
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.state.BlogPostsStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.state.BlogPostsStateEvent.PostNewBlogStateEvent
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import kotlinx.android.synthetic.main.fragment_update_post.*

class UpdatePostFragment : BasePostsFragment(), View.OnClickListener {

    private val TAG = "AppDebug"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle("Create New Post")

        Log.d(TAG, "UpdatePostFragment: onViewCreated: stateEvent just gonna be set")

        btn_update_update_post.setOnClickListener(this)
    }

    fun triggerBtn(){

        btn_update_update_post.setOnClickListener {

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_update_update_post -> {

                try{
                    val k = Integer.parseInt(et_update_user_id_update_post.text.toString())
                    if(k < 101){
                        Toast.makeText(this@UpdatePostFragment.context, "Sorry this id already exists", Toast.LENGTH_SHORT).show()
                        return
                    }
                } catch (e: Exception){
                    Toast.makeText(this@UpdatePostFragment.context, "Numbers required for userId", Toast.LENGTH_SHORT).show()
                    return
                }


                if(et_update_user_id_update_post.text.isNullOrEmpty() ||
                    et_update_title_update_post.text.isNullOrEmpty() ||
                    et_update_body_update_post.text.isNullOrEmpty()){

                    Toast.makeText(this@UpdatePostFragment.context, "None of the fields can be empty!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@UpdatePostFragment.context, "Success!!! Post Created.", Toast.LENGTH_SHORT).show()

                    et_update_user_id_update_post.setText("")
                    et_update_title_update_post.setText("")
                    et_update_body_update_post.setText("")

                    activity?.displayDialog(
                        "Ok",
                        "Uhh, okkk!",
                        "Attenttion!!!",
                        getString(R.string.not_created),
                        false,
                        object : DialogButtonClickListener{
                            override fun onOkClick(dialog: Dialog) {
                                dialog.hide()
                            }

                            override fun onNoClick(dialog: Dialog) {
                                dialog.hide()
                            }
                        })
                }

            }
        }
    }

}





























