package com.hamidjonhamidov.whoiskhamidjon.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import kotlinx.android.synthetic.main.blog_post_item.view.*


// this is for different viewholder
class BlogPostsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<BlogPostModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.blog_post_item, parent, false)
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){

            is BlogViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(items: List<BlogPostModel>){
        this.items = items
        notifyDataSetChanged()
    }


    class BlogViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){

        val idTv = itemView.tv_id_post_item
        val userIdTv = itemView.tv_user_id_post_item
        val titleTv = itemView.tv_title_post_item
        val bodyTv = itemView.tv_body_post_item

        fun bind(blogPostModel: BlogPostModel){
            idTv.setText(blogPostModel.id.toString())
            userIdTv.setText(blogPostModel.userId.toString())
            titleTv.setText(blogPostModel.title)
            bodyTv.setText(blogPostModel.body)
        }
    }
}

























