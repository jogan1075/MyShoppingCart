package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jmc.core.utilities.base.composeBase.BaseVM
import com.jmc.core.utilities.base.errors.DomainError
import com.jmc.core.utilities.base.exceptions.RepositoryCoroutineHandler
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.presentation.contract.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val repository: DataRepository,
    stateHandle: SavedStateHandle
) : BaseVM<HomeContract.Event, HomeContract.State>(stateHandle) {

    init {
        launch {
            val cartByCache = repository.getValueByCache().first()
            setState {
                copy(isLoading = true, cart = cartByCache)
            }
        }
    }

    public override fun handleEvents(intent: HomeContract.Event) {
        when (intent) {
            HomeContract.Event.InitValues -> initValues()
            is HomeContract.Event.AddToCart -> addToCart(intent.id)
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



    private fun initValues() {
        launch {
            val categories = repository.getCategories().first()

            val products = repository.getProducts().first()


           setState{
               copy(isLoading = false, categories = categories, products = products)
           }

        }
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    private fun handleError(domainError: DomainError) {}

    private fun launch(invoke: suspend () -> Unit) {
        viewModelScope.launch(RepositoryCoroutineHandler(::handleError)) {
            invoke()
        }
    }
}