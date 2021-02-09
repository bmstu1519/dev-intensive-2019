package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()

        Log.d("M_ProfileViewModel","onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it)})
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileViewModel","updateTheme")
        delegate.localNightMode = mode
    }

    private fun updateUI(profile : Profile) {
        profile.toMap().also {
            for ((k,v) in viewFields) v.text = it[k].toString()
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
                "nickName" to tv_nick_name,
                "rank" to tv_rank,
                "firstName" to et_first_name,
                "lastName" to et_last_name,
                "about" to et_about,
                "repository" to et_repository,
                "rating" to tv_rating,
                "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE,false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener(View.OnClickListener {
            if(isEditMode) {
                if (validateRepo(et_repository.text.toString()) == false) et_repository.text.clear()
                saveProfileInfo()
            }
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)

        })

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName","lastName","about","repository").contains(it.key) }
        for((_,v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if(isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit
        wr_repository.isErrorEnabled = isEdit
        et_repository.addTextChangedListener(TextFieldValidation(et_repository))

        with(btn_edit){
            val filter : ColorFilter? = if (isEdit){
                PorterDuffColorFilter(
                        resources.getColor(R.color.color_accent, theme),
                        PorterDuff.Mode.SRC_IN
                )
            } else null

            val icon = if (isEdit){
                resources.getDrawable(R.drawable.ic_baseline_save_24,theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp,theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)


        }
    }

    private fun saveProfileInfo(){
        Profile(
                firstName = et_first_name.text.toString(),
                lastName = et_last_name.text.toString(),
                about = et_about.text.toString(),
                repository = et_repository.text.toString()

        ).apply {
            viewModel.saveProfileData(this)
        }
    }
    fun validateRepo(inputRepo: String) : Boolean{
        val domain = listOf("https://github.com/","https://www.github.com/","www.github.com/","github.com/")
        return if (domain.contains(inputRepo.substring(0, inputRepo.lastIndexOf('/') + 1)) && inputRepo.substring(inputRepo.lastIndexOf('/')).matches(Regex("^/[a-z0-9_]{5,39}$")) || inputRepo == ""){
            true
        }
        else {
            wr_repository.error = "Невалидный адрес репозитория"
            false
        }
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            when(view.id){
                R.id.et_repository -> validateRepo(et_repository.text.toString())
            }
        }
        }
}
