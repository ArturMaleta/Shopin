package mal.art.shopin.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import mal.art.shopin.R
import mal.art.shopin.extensions.lazyActivityViewModels
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel

class AddNewShoppingListDialogFragment : DialogFragment() {
  private var screenWidth: Int = 0
  private var screeHeight: Int = 0
  private val shoppingListsViewModel: ListOfShoppingListsViewModel by lazyActivityViewModels {
    ListOfShoppingListsViewModel(activity!!.application)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    getScreenMetrics()

    return LayoutInflater.from(activity).inflate(R.layout.add_new_shopping_list_fragment_layout, null)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val shoppingListName: EditText = view.findViewById(R.id.shopping_list_name_to_save)

    val saveShoppingListBtn: Button = view.findViewById(R.id.save_button)
    saveShoppingListBtn.setOnClickListener {
      val listName = shoppingListName.text.toString()

      shoppingListsViewModel.insertShoppingList(listName)

      this.dismiss()
    }
  }

  override fun onResume() {
    super.onResume()
    setDialogParams()
  }

  private fun setDialogParams() {
    dialog!!.window?.let {
      val dm = DisplayMetrics()
      activity!!.windowManager.defaultDisplay.getMetrics(dm)
      val params = it.attributes
      params.width = (screenWidth * 0.85).toInt()
      params.height = (screeHeight * 0.4).toInt()
      it.attributes = params
    }
  }

  private fun getScreenMetrics() {
    val dm = DisplayMetrics()
    activity!!.windowManager.defaultDisplay.getMetrics(dm)
    screenWidth = dm.widthPixels
    screeHeight = dm.heightPixels
  }

  companion object {
    const val TAG = "ADD_NEW_SHOPPING_LIST_FRAGMENT_TAG"
  }
}