package ir.tabashir.trymvrx.ui.main

import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import ir.tabashir.trymvrx.core.BaseFragment
import ir.tabashir.trymvrx.core.appComponent
import ir.tabashir.trymvrx.core.simpleController
import ir.tabashir.trymvrx.view.basicRow
import ir.tabashir.trymvrx.view.loadingRow
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private val viewModel: MainViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun epoxyController() = simpleController(viewModel) { state: MainState ->

        state.categories.forEachIndexed { index, category ->
            basicRow {
                id("cat-$index")
                title(category.title)
                subtitle("Category")
            }
        }

        if (state.categories.hasMore)
            loadingRow {
                id("loading-${state.categories.size}")
                onBind { _, _, _ -> viewModel.fetchNextPage() }
            }

    }

}