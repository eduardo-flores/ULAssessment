package com.mobile.eflores.ulassessment

import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_POST
import com.mobile.eflores.ulassessment.data.Post
import com.mobile.eflores.ulassessment.data.User
import kotlinx.android.synthetic.main.recyclerview_post.view.*
import kotlinx.android.synthetic.main.recyclerview_user.view.*
import java.io.Serializable


class RecyclerPostsAdapter(private val listPost: List<Post>) :
    RecyclerView.Adapter<RecyclerPostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recyclerview_post, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMessage(listPost[position])
    }

    override fun getItemCount(): Int = listPost.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var post: Post? = null

        fun bindMessage(post: Post) {
            this.post = post
            view.postTitle.text = post.title
            view.postMessage.text = post.body
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
//            Toast.makeText(view?.context, "Post " + post, Toast.LENGTH_SHORT).show()
            if (view != null) {
                val context = view.context
                val intent = Intent(context, PostActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_POST, post as Serializable)
                }
                context.startActivity(intent)
            }
        }

    }
}