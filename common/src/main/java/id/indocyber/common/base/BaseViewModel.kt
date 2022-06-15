package id.indocyber.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import id.indocyber.common.ext.SingleLiveEvent
import id.indocyber.common.ui.DialogData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationEvent = SingleLiveEvent<NavDirections>()
    val showDialogEvent = SingleLiveEvent<DialogData>()
    val popBackStackEvent = SingleLiveEvent<Any>()
    var parent: BaseViewModel? = null

}