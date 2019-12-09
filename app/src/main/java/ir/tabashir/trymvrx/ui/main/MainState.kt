package ir.tabashir.trymvrx.ui.main

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import ir.tabashir.trymvrx.network.model.Category
import ir.tabashir.trymvrx.utils.PagedList

data class MainState(
    val categories: PagedList<Category> = PagedList.empty(5),
    val request: Async<List<Category>> = Uninitialized
) : MvRxState