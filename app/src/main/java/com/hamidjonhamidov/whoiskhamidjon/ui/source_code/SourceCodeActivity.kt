package com.hamidjonhamidov.whoiskhamidjon.ui.source_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.*
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.FILE_TYPE_BLUE_FILE
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.FILE_TYPE_LEAF
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.FILE_TYPE_PACKAGE
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.RES_ID_EXTRA
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.STATE_CLOSED_IN_VIEW
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.Constants.STATE_OPENED_IN_VIEW
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.CreateViews.connectMainToParent
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.CreateViews.connectCl1OnBottomCl2InsideTopCl3
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.CreateViews.createConstraintLayout
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.CreateViews.createImageViewForFileType
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.custom_treeview.CreateViews.createTextViewForFileName
import kotlinx.android.synthetic.main.activity_source_code.*
import java.util.*

class SourceCodeActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "AppDebug"

    val hashMap = TreeMap<Int, FileView>()

    var utilFuncs = UtilFunctions()

    lateinit var root: FileView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_code)

        initialize()
    }

    private fun initialize() {
        root = FileView(
            type = FILE_TYPE_BLUE_FILE,
            name = "root",
            parent = null,
            containerCl = cl_root_file_container,
            childClMain = cl_file_view,
            expandIv = iv_expand,
            fileTypeIv = iv_file_type,
            fileTextTv = tv_file_name
        )

        cl_file_view.setOnClickListener(this)
        hashMap[R.id.cl_file_view] = root

        utilFuncs.createHierarchyFromStringList(root, StaticData.leafFileList)
    }

    override fun onClick(v: View?) {
        if (v == null) return

        Log.d(TAG, "SourceCodeActivity: onClick: called")

        var clickedItem: FileView? = null
        if (hashMap.containsKey(v.id))
            clickedItem = hashMap[v.id]

        if (clickedItem == null) return

        workWithClickedItem(clickedItem)
    }

    private fun workWithClickedItem(mFileView: FileView) {

        Log.d(TAG, "SourceCodeActivity: workWithClickedItem: clicked")

        // if file is leaf open it
        if (mFileView.type == FILE_TYPE_LEAF) {

            mFileView.rawId?.let {
                val mIntent = Intent(this@SourceCodeActivity, CodeViewActivity::class.java)
                mIntent.putExtra(RES_ID_EXTRA, it)
                startActivity(mIntent)
            }

            return
        }

        // if file is open close it
        if (mFileView.openCloseState == STATE_OPENED_IN_VIEW) {
            Log.d(TAG, "SourceCodeActivity: workWithClickedItem: trying to close file: ${mFileView.name}")

            mFileView.childClSec!!.visibility =
                View.GONE

            mFileView.openCloseState = STATE_CLOSED_IN_VIEW
            mFileView.expandIv?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.expand_default))

            return
        }

        // if file is closed, open it
        if (mFileView.openCloseState == STATE_CLOSED_IN_VIEW) {

            // if child container second hasn't been created, create it with its children
            if (mFileView.childClSec == null) {

                // create container second, and connect it to parent and childContainerMain
                mFileView.childClSec = createConstraintLayout(this, 30, 0, 0, 0)

                // connect child sec to parent and child main
                connectCl1OnBottomCl2InsideTopCl3(
                    mFileView.childClSec!!,
                    mFileView.childClMain!!,
                    mFileView.containerCl!!
                )

                // child container created now time to add children
                var itemTopCl = mFileView.childClMain!!

                for (childFile in mFileView.children) {

                    Log.d(TAG, "SourceCodeActivity: workWithClickedItem: childName=${childFile.name}, state=${childFile.children.size}")

                    // main cl for child file
                    childFile.childClMain = createChildMainWithChildren(childFile)

                    // root cl for child simple file
                    childFile.containerCl = createConstraintLayout(this, 20, 0, 0, 0)

                    // connect childMain To child root container
                    connectMainToParent(
                        childFile.childClMain!!,
                        childFile.containerCl!!
                    )

                    // connect child root to mFile root and top container
                    connectCl1OnBottomCl2InsideTopCl3(
                        childFile.containerCl!!,
                        itemTopCl,
                        mFileView.childClSec!!
                    )


                    // save item so that next item is placed on bottom of previous item
                    itemTopCl = childFile.containerCl!!

                    // save parent to object
                    childFile.parent = mFileView

                    // set  listener
                    childFile.childClMain!!.setOnClickListener(this)
                    // save this object for future use
                    hashMap[childFile.childClMain!!.id] = childFile
                }

            }

            // show the container
            mFileView.childClSec!!.visibility = View.VISIBLE
            mFileView.openCloseState = STATE_OPENED_IN_VIEW
            mFileView.expandIv?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.expand_down))
        }


    }

    private fun createChildMainWithChildren(childFile: FileView): ConstraintLayout{
        // create children inside childMainContainer
        childFile.expandIv =
            CreateViews.createImageViewForExpand(this, R.drawable.expand_default, 30)

        if(childFile.type == FILE_TYPE_LEAF){
            childFile.expandIv?.setImageDrawable(resources.getDrawable(R.drawable.dot))
        }

        childFile.fileTypeIv = createImageViewForFileType(
            this,
            when (childFile.type) {
                FILE_TYPE_BLUE_FILE -> R.drawable.file_type_blue_folder
                FILE_TYPE_PACKAGE -> R.drawable.file_type_package
                else -> R.drawable.file_type_leaf
            }
        )

        childFile.fileTextTv = createTextViewForFileName(
            this,
            childFile.name,
            if (childFile.type == FILE_TYPE_LEAF) R.color.blue else R.color.black,
            10
        )

        // put children views to new childContainerMain
        return CreateViews.createContainerMain(
            this,
            childFile.expandIv!!,
            childFile.fileTypeIv!!,
            childFile.fileTextTv!!
        )
    }
}














