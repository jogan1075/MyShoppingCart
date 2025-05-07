package com.jmc.myshoppingcart.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jmc.core.utilities.base.composeBase.BaseVM
import com.jmc.core.utilities.base.errors.DomainError
import com.jmc.core.utilities.base.exceptions.RepositoryCoroutineHandler
import com.jmc.myshoppingcart.data.DataRepository
import com.jmc.myshoppingcart.presentation.contract.SearchContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DataRepository,
    stateHandle: SavedStateHandle
) : BaseVM<SearchContract.Event, SearchContract.State>(stateHandle) {


    init {
        setState {
            copy(isLoading = true)
        }
    }

    public override fun handleEvents(intent: SearchContract.Event) {
        when(intent){
            is SearchContract.Event.SearchProductByCategory -> getSearchProductsByCategory(intent.category)
        }
    }

    private fun getSearchProductsByCategory(categories:String) {
        launch {
            val resp = repository.getProductsByCategory(categories).first()
            setState {
                copy(isLoading = false, products = resp)
            }
        }
    }

    override fun setInitialState(): SearchContract.State {
        return SearchContract.State()
    }

    private fun handleError(domainError: DomainError) {}

    private fun launch(invoke: suspend () -> Unit) {
        viewModelScope.launch(RepositoryCoroutineHandler(::handleError)) {
            invoke()
        }
    }
}