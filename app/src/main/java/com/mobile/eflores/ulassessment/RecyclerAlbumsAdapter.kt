package com.mobile.eflores.ulassessment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_ALBUM
import com.mobile.eflores.ulassessment.data.Album
import kotlinx.android.synthetic.main.recyclerview_album.view.*
import java.io.Serializable


class RecyclerAlbumsAdapter(private val listAlbum: List<Album>) :
    RecyclerView.Adapter<RecyclerAlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recyclerview_album, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMessage(listAlbum[position])
    }

    override fun getItemCount(): Int = listAlbum.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var album: Album? = null

        fun bindMessage(album: Album) {
            this.album = album
            view.albumTitle.text = album.title
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
//            Toast.makeText(view?.context, "Post " + post, Toast.LENGTH_SHORT).show()
            if (view != null) {
                val context = view.context
                val intent = Intent(context, AlbumActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_ALBUM, album as Serializable)
                }
                context.startActivity(intent)
            }
        }

    }
}