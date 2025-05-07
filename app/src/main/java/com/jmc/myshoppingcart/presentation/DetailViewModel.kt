package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jmc.core.utilities.base.composeBase.BaseVM
import com.jmc.core.utilities.base.errors.DomainError
import com.jmc.core.utilities.base.exceptions.RepositoryCoroutineHandler
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.presentation.contract.DetailContract
import com.jmc.myshoppingcart.presentation.contract.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DataRepository,
    stateHandle: SavedStateHandle
) : BaseVM<DetailContract.Event, DetailContract.State>(stateHandle) {


    init {
        setState {
            copy(isLoading = true)
        }
    }

    public override fun handleEvents(intent: DetailContract.Event) {
        when(intent){
            is DetailContract.Event.AddToCart -> addToCart(intent.id)
            is DetailContract.Event.GetDetailProduct -> getDetailProduct(intent.productId)
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

    private fun getDetailProduct(productId: Int) {
        launch {

            val resp = repository.getProduct(productId).first()
            setState {
                copy(isLoading = false, productItem = resp)
            }
        }
    }

    override fun setInitialState(): DetailContract.State {
        return DetailContract.State()
    }

    private fun handleError(domainError: DomainError) {}

    private fun launch(invoke: suspend () -> Unit) {
        viewModelScope.launch(RepositoryCoroutineHandler(::handleError)) {
            invoke()
        }
    }
}