package com.mobile.eflores.ulassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_PHOTO
import com.mobile.eflores.ulassessment.data.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photo = intent.getSerializableExtra(INTENT_EXTRA_PHOTO) as Photo
        Picasso.with(this).load(photo.url).into(photoImageView)

        tvTitle.text = photo.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
