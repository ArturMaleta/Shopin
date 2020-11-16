package mal.art.shopin.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider

@MainThread
inline fun <reified VM : ViewModel> Fragment.lazyActivityViewModels(
  noinline viewModelInitializer: () -> VM
): Lazy<VM> =
  ViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { requireActivity().viewModelStore },
    factoryProducer = {
      object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
          @Suppress("UNCHECKED_CAST")
          return viewModelInitializer() as T
        }
      }
    }
  )