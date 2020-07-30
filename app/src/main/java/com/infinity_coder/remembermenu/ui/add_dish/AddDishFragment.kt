package com.infinity_coder.remembermenu.ui.add_dish

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.infinity_coder.remembermenu.R
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IView
import com.infinity_coder.remembermenu.ui.common.setActionBar
import kotlinx.android.synthetic.main.fragment_add_dish.*

class AddDishFragment: Fragment(R.layout.fragment_add_dish), IView<AddDishState, AddDishEffect> {

    companion object {
        private const val PICK_PHOTO_FROM_GALLERY_CODE = 1
    }

    private val viewModel: AddDishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer(this::render))
        viewModel.effects.observe(viewLifecycleOwner, Observer(this::react))

        addImageButton.setOnClickListener {
            viewModel.sendIntent(AddDishIntent.PickPhotoFromGallery)
        }

        addNewDishButton.setOnClickListener {
            viewModel.sendIntent(AddDishIntent.AddNewDish(provideDishEntity()))
        }

        titleEditText.addTextChangedListener { updateScreenState() }
        descriptionEditText.addTextChangedListener { updateScreenState() }

        setActionBar(addDishToolbar, hasBackNavigation = true)
        addDishToolbar.title = getString(R.string.adding_dish)
    }

    private fun updateScreenState() {
        val dish = provideDishEntity()
        viewModel.sendIntent(AddDishIntent.UpdateState(dish))
    }

    private fun provideDishEntity(): DishEntity {
        return DishEntity(
            title = titleEditText.text.toString(),
            description = descriptionEditText.text.toString(),
            image = photoImageView.drawable?.toBitmap()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.apply, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.apply -> viewModel.sendIntent(AddDishIntent.AddLastDish(provideDishEntity()))
            android.R.id.home -> finishAddDishScreen()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun render(state: AddDishState) {
        Glide.with(this)
            .load(state.image)
            .centerCrop()
            .into(photoImageView)

        val hasImage = state.image != null
        photoImageView.visibility = if (hasImage) View.VISIBLE else View.GONE
        addImageButton.text = if (hasImage) getString(R.string.change_image) else getString(R.string.add_image)

        if (titleEditText.text.toString() != state.title) {
            titleEditText.setText(state.title)
        }
        if (descriptionEditText.text.toString() != state.description) {
            descriptionEditText.setText(state.description)
        }
    }

    override fun react(effect: AddDishEffect?) {
        when(effect) {
            AddDishEffect.OpenPhotoGalleryScreen -> {
                choosePhotoFromGallery()
            }
            AddDishEffect.FinishAddDishScreen -> {
                finishAddDishScreen()
            }
        }
    }

    private fun choosePhotoFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, PICK_PHOTO_FROM_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED) return

        when (requestCode) {
            PICK_PHOTO_FROM_GALLERY_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    viewModel.sendIntent(AddDishIntent.ExtractBitmapFromIntent(data))
                }
            }
        }
    }

    private fun finishAddDishScreen() {
        findNavController().popBackStack()
    }

}