package com.infinity_coder.remembermenu.ui.dishes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.infinity_coder.remembermenu.R
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import com.infinity_coder.remembermenu.ui.common.IView
import com.infinity_coder.remembermenu.ui.common.parentNavController
import com.infinity_coder.remembermenu.ui.dishes.adapters.DishesRecyclerAdapter
import com.infinity_coder.remembermenu.ui.tabnavigation.TabNavigationFragment
import com.infinity_coder.remembermenu.ui.tabnavigation.TabNavigationFragmentDirections
import kotlinx.android.synthetic.main.fragment_dishes.*


class DishesFragment : Fragment(R.layout.fragment_dishes), IView<DishesState, DishesEffect> {

    private val viewModel: DishesViewModel by viewModels()
    private val dishesAdapter: DishesRecyclerAdapter
        get() = dishesRecyclerView.adapter as DishesRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dishesRecyclerView.adapter = DishesRecyclerAdapter(this::onItemClicked)
        dishesRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.state.observe(viewLifecycleOwner, Observer(this::render))
        viewModel.effects.observe(viewLifecycleOwner, Observer(this::react))

        viewModel.sendIntent(DishesIntent.FetchDishes)

        addDishButton.setOnClickListener {
            viewModel.sendIntent(DishesIntent.AddDish)
        }
    }

    private fun onItemClicked(dish: DishEntity) {
        viewModel.sendIntent(DishesIntent.OpenOptionsDialog(dish))
    }

    override fun render(state: DishesState) {
        val dishListWithSeparator = mutableListOf<DishEntity?>()

        state.dishes.forEach {
            dishListWithSeparator.add(it)
            if (state.dishes.last() != it) {
                dishListWithSeparator.add(null)
            }
        }

        dishesAdapter.updateDishes(dishListWithSeparator)
    }

    override fun react(effect: DishesEffect?) {
        when(effect) {
            DishesEffect.OpenAddDishScreen -> openAddDishScreen()
        }
    }

    private fun openAddDishScreen() {
        val actionToAddDishFragment = TabNavigationFragmentDirections.actionTabNavigationFragment2ToAddDishFragment()
        parentNavController(TabNavigationFragment::class.java).navigate(actionToAddDishFragment)
    }
}