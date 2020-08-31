package mal.art.shopin.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mal.art.shopin.R
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel
import mal.art.shopin.viewModel.SharedViewModel

class AddNewShoppingListFragment : DialogFragment() {
  private var screenWidth: Int = 0
  private var screeHeight: Int = 0
  private var shoppingListsViewModel: ListOfShoppingListsViewModel? = null
  private lateinit var sharedViewModel: SharedViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    getScreenMetrics()
    initializeViewModel()

    return LayoutInflater.from(activity).inflate(R.layout.add_new_shopping_list_fragment_layout, null)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val shoppingListName: EditText = view.findViewById(R.id.shopping_list_name_to_save)

    val saveShoppingListBtn: Button = view.findViewById(R.id.save_button)
    saveShoppingListBtn.setOnClickListener {
      val listName = shoppingListName.text.toString()

      shoppingListsViewModel?.insertShoppingList(listName)

      sharedViewModel.shoppingListName.value = listName

      this.dismiss()
    }
  }

  private fun initializeViewModel() {
    shoppingListsViewModel = ViewModelProviders.of(this).get(ListOfShoppingListsViewModel::class.java)
    sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
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