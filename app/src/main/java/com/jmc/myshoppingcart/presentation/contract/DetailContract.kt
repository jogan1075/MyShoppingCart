package com.jmc.myshoppingcart.presentation.contract

import com.jmc.core.utilities.base.composeBase.ViewEvent
import com.jmc.core.utilities.base.composeBase.ViewState
import com.jmc.myshoppingcart.data.model.ProductItem

class DetailContract {
    sealed class Event : ViewEvent {
        data class GetDetailProduct(val productId: Int) : Event()
        data class AddToCart(val id: Int) : Event()
    }

    data class State(
        override val isLoading: Boolean = false,
        val productItem: ProductItem? = null,
        val cart: Map<Int, Int> = emptyMap(),

    ) : ViewState {
        val cartCount: Int get() = cart.values.sum()
        override fun clearErrors(): State {
            return copy()
        }
    }
}