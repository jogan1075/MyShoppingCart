package com.jmc.myshoppingcart.presentation.contract

import com.jmc.core.utilities.base.composeBase.ViewEvent
import com.jmc.core.utilities.base.composeBase.ViewState
import com.jmc.myshoppingcart.data.model.ProductItem

class SearchContract {
    sealed class Event : ViewEvent {
        data class SearchProductByCategory(val category: String) : Event()
    }

    data class State(
        override val isLoading: Boolean = false,
        val categories : List<String> = emptyList(),
        val products : List<ProductItem> = emptyList(),
        val cart: Map<Int, Int> = emptyMap(),

    ) : ViewState {
        val cartCount: Int get() = cart.values.sum()
        override fun clearErrors(): State {
            return copy()
        }
    }
}