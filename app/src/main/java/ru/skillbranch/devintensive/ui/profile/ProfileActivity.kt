package ru.skillbranch.devintensive.ui.profile

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import ru.skillbranch.devintensive.R

/*
models
—— Profile
ui
—— custom
———— AspectRatioImageView (optional)
———— CircleImageView (optional)
—— profile
———— ProfileActivity
utils
—— Utils
viewmodels
—— ProfileViewModel*/
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState, persistentState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
        super.onSaveInstanceState(outState)
    }

}





