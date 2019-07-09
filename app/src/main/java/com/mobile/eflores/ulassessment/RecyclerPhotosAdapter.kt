package com.mobile.eflores.ulassessment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_PHOTO
import com.mobile.eflores.ulassessment.data.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_photo.view.*


class RecyclerPhotosAdapter(private val listPhoto: List<Photo>) :
    RecyclerView.Adapter<RecyclerPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recyclerview_photo, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMessage(listPhoto[position])
    }

    override fun getItemCount(): Int = listPhoto.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var photo: Photo? = null

        fun bindMessage(photo: Photo) {
            this.photo = photo
            Picasso.with(view.context).load(photo.thumbnailUrl).into(view.photoImage)
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
//            Toast.makeText(view?.context, "Photo " + photo, Toast.LENGTH_SHORT).show()
            if (view != null) {
                val context = view.context
                val intent = Intent(context, PhotoActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_PHOTO, photo)
                }
                context.startActivity(intent)
            }
        }

    }
}