package mal.art.shopin.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import mal.art.shopin.R
import mal.art.shopin.fragment.AddNewProductFragment
import mal.art.shopin.fragment.FirebaseTestFragment
import mal.art.shopin.fragment.ListOfShoppingListFragment
import mal.art.shopin.fragment.helper.changeFragment

class MainScreen : AppCompatActivity(R.layout.activity_main_screen) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val addNewProduct: Button = findViewById(R.id.addProduct_btn)
    val firebaseButton: Button = findViewById(R.id.firebase_btn)
    val list_of_shopping_lists: Button = findViewById(R.id.main_screen_list_of_shopping_lists_btn)

    list_of_shopping_lists.setOnClickListener {
      val listOfShoppingListsFragment = ListOfShoppingListFragment()
      listOfShoppingListsFragment.changeFragment(
        supportFragmentManager,
        R.id.list_of_shopping_list_fragment_container,
        listOfShoppingListsFragment,
        ListOfShoppingListFragment.TAG
      )
    }

    addNewProduct.setOnClickListener {
      val addNewProductFragment = AddNewProductFragment()
      addNewProductFragment.show(supportFragmentManager, AddNewProductFragment.TAG)
    }

    firebaseButton.setOnClickListener {
      val testFragment = FirebaseTestFragment()
      testFragment.changeFragment(
        supportFragmentManager,
        R.id.firebase_test_fragment_container,
        testFragment,
        FirebaseTestFragment.TAG
      )
    }
  }
}