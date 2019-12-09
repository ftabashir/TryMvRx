package ir.tabashir.trymvrx.ui.main

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import ir.tabashir.trymvrx.core.MvRxViewModel
import ir.tabashir.trymvrx.network.ApiService
import ir.tabashir.trymvrx.network.result.Result
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: MainState,
    private val apiService: ApiService
) : MvRxViewModel<MainState>(initialState) {

    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState

        viewModelScope.launch {

            val result = apiService.categories(
                page = state.categories.nextPage(),
                size = state.categories.pageSize
            )
            if (result is Result.Success) {
                setState {
                    val newCategories = categories.withNextData(result.data ?: emptyList())
                    state.copy(categories = newCategories)
                }
            }

        }

    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: MainState): MainViewModel
    }

    /**
     * If you implement MvRxViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with MvRx.
     *
     * @see MvRxViewModelFactory
     */
    companion object : MvRxViewModelFactory<MainViewModel, MainState> {

        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel {
            val f: MainFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return f.viewModelFactory.create(state)
        }
    }
}