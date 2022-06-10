package com.example.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import com.example.common.R
import com.example.common.ext.SingleLiveEvent
import com.example.common.ui.DialogData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationEvent = SingleLiveEvent<NavDirections>()
    val showDialogEvent = SingleLiveEvent<DialogData>()
    val popBackStackEvent = SingleLiveEvent<Any>()
    var parent: BaseViewModel? = null
    fun navigate(navigator: NavDirections) {
        navigationEvent.postValue(navigator)
    }
    fun showDialog(
        title: String,
        message: String,
        positiveButton: Pair<String, () -> Unit>? =
            getStrings(R.string.app_common_error_positive_button_text) to {

        },
        negativeButton: Pair<String, () -> Unit>? =
            getStrings(R.string.app_common_error_negative_button_text) to {

        }
    ) {
        showDialogEvent.postValue(
            DialogData(
                title, message, positiveButton, negativeButton
            )
        )
    }
    fun <T> observeResponseData(
        response: BaseResponse<T>,
        success: ((T) -> Unit)?,
        error: ((Throwable) -> Unit)? = {
            showDialog(
                getStrings(R.string.app_common_error_dialog_title),
                it.message.orEmpty()
            )
        },
        loading: (() -> Unit)? = {}
    ){
        when(response){
            is BaseResponse.BaseResponseSuccess -> {
                response.data?.let { success?.invoke(it) }
            }
            is BaseResponse.BaseResponseError -> {
                response.error?.let {
                    error?.invoke(it)
                }
            }
            is BaseResponse.BaseResponseLoading ->{
                loading?.invoke()
            }
        }
    }
    fun getStrings(id: Int) = getApplication<Application>().getString(id)

    fun popBackStack() {
        popBackStackEvent.postValue(Any())
    }

}