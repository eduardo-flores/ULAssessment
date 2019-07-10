package com.mobile.eflores.ulassessment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_POST
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_USER
import com.mobile.eflores.ulassessment.data.User
import kotlinx.android.synthetic.main.recyclerview_user.view.*
import java.io.Serializable


class RecyclerAdapter(private val listMessage: List<User>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recyclerview_user, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMessage(listMessage[position])
    }

    override fun getItemCount(): Int = listMessage.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var user: User? = null

        fun bindMessage(user: User) {
            this.user = user
            view.userName.text = user.name
            view.userStreet.text = user.address.street
            view.userSuite.text = user.address.suite
            view.userZipcode.text = user.address.zipcode
            view.userCity.text = user.address.city
            view.btGallery.setOnClickListener { view ->  if (view != null) {
                val context = view.context
                val intent = Intent(context, GalleryActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_USER, user as Serializable)
                }
                context.startActivity(intent)
            }}
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
//            Toast.makeText(view?.context, "User " + user, Toast.LENGTH_SHORT).show()
            if (view != null) {
                val context = view.context
                val intent = Intent(context, UserActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_USER, user as Serializable)
                }
                context.startActivity(intent)
            }
        }

    }
}