package com.infinity_coder.remembermenu.ui.learn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.infinity_coder.remembermenu.R
import com.infinity_coder.remembermenu.ui.common.IView
import com.infinity_coder.remembermenu.ui.dishes.DishesViewModel
import kotlinx.android.synthetic.main.fragment_learn.*

class LearnFragment : Fragment(R.layout.fragment_learn), IView<LearnState, LearnEffect> {

    private val viewModel: LearnViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer(this::render))
        viewModel.effects.observe(viewLifecycleOwner, Observer(this::react))

        viewModel.sendIntent(LearnIntent.FetchRandomDish)

        cardViewLayout.setOnClickListener {
            viewModel.sendIntent(LearnIntent.ChangeCardMode)
        }

        nextButton.setOnClickListener {
            viewModel.sendIntent(LearnIntent.NextCard)
        }
    }

    override fun render(state: LearnState) {
        val dish = state.dish ?: return
        titleTextView.text = dish.title
        descriptionTextView.text = dish.description
        imageView.setImageBitmap(dish.image)

        if (state.isShowAnswer) {
            titleTextView.visibility = View.VISIBLE
            descriptionTextView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            titleTextView.visibility = View.GONE
            descriptionTextView.visibility = View.GONE
            imageView.visibility = View.VISIBLE
        }
    }

    override fun react(effect: LearnEffect?) {

    }
}