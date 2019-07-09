package com.mobile.eflores.ulassessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobile.eflores.ulassessment.data.Comment
import kotlinx.android.synthetic.main.recyclerview_comment.view.*


class RecyclerCommentsAdapter(private val listComment: List<Comment>) :
    RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recyclerview_comment, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMessage(listComment[position])
    }

    override fun getItemCount(): Int = listComment.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var comment: Comment? = null

        fun bindMessage(comment: Comment) {
            this.comment = comment
            view.commentName.text = comment.name
            view.commentEmail.text = comment.email
            view.commentBody.text = comment.body
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            //Toast.makeText(view?.context, "Comment " + comment, Toast.LENGTH_SHORT).show()
//            if (view != null) {
//                val context = view.context
//                val intent = Intent(context, UserActivity::class.java).apply {
//                    putExtra(EXTRA_MESSAGE, user as Serializable)
//                }
//                context.startActivity(intent)
//            }
        }

    }
}