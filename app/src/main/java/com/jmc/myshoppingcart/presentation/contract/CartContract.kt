package com.jmc.myshoppingcart.presentation.contract

import com.jmc.core.utilities.base.composeBase.ViewEvent
import com.jmc.core.utilities.base.composeBase.ViewState
import com.jmc.myshoppingcart.data.model.ProductItem

class CartContract {
    sealed class Event : ViewEvent {
        data object GetProducts : Event()
        data class AddToCart(val id: Int?) : Event()
        data class RemoveToCart(val id: Int?) : Event()
    }

    data class State(
        override val isLoading: Boolean = false,
        val products : List<ProductItem> = emptyList(),
        val cart: Map<Int, Int> = emptyMap(),

    ) : ViewState {
        override fun clearErrors(): State {
            return copy()
        }
    }
}