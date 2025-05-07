package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jmc.core.utilities.base.composeBase.BaseVM
import com.jmc.core.utilities.base.errors.DomainError
import com.jmc.core.utilities.base.exceptions.RepositoryCoroutineHandler
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.presentation.contract.CartContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: DataRepository,
    stateHandle: SavedStateHandle
) : BaseVM<CartContract.Event, CartContract.State>(stateHandle) {


    init {
        launch {
            val cartByCache = repository.getValueByCache().first()
            setState {
                copy(isLoading = true, cart = cartByCache)
            }
            getDetailProduct()
        }

    }

    fun addToCart(productId: Int) {
        launch {
            val cartByCache = repository.getValueByCache().first()
            cartByCache.toString()
            if(cartByCache.isNotEmpty()){
                cartByCache[productId] = (cartByCache[productId] ?: 0) + 1
                repository.saveInCache(cartByCache)
                setState {
                    copy(cart = cartByCache)
                }
            }else{
                val newCart = viewState.value.cart.toMutableMap()
                newCart[productId] = (newCart[productId] ?: 0) + 1

                repository.saveInCache(newCart)
                setState {
                    copy(cart = newCart)
                }
            }

        }
    }

    fun removeFromCart(productId: Int) {
        launch {
            val currentCart = repository.getValueByCache().first()

            if (currentCart.containsKey(productId)) {
                val currentQuantity = currentCart[productId] ?: 0

                if (currentQuantity > 1) {
                    currentCart[productId] = currentQuantity - 1
                } else {
                    currentCart.remove(productId)
                }

                repository.saveInCache(currentCart)
                setState {
                    copy(cart = currentCart)
                }
            }
        }
    }


    public override fun handleEvents(intent: CartContract.Event) {
        when (intent) {
            is CartContract.Event.AddToCart -> addToCart(intent.id!!)

            is CartContract.Event.GetProducts -> getDetailProduct()
            is CartContract.Event.RemoveToCart -> removeFromCart(intent.id!!)
        }
    }

    private fun getDetailProduct() {
        launch {
            val products = repository.getProducts().first()
            setState {
                copy(isLoading = false, products = products)
            }
        }
    }

    override fun setInitialState(): CartContract.State {
        return CartContract.State()
    }

    private fun handleError(domainError: DomainError) {}

    private fun launch(invoke: suspend () -> Unit) {
        viewModelScope.launch(RepositoryCoroutineHandler(::handleError)) {
            invoke()
        }
    }
}