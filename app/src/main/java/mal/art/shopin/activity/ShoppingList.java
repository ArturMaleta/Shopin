package mal.art.shopin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.R;
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter;
import mal.art.shopin.viewModel.ShoppingListsViewModel;

public class ShoppingList extends AppCompatActivity {

  ImageView opacityToBackgroundImage;

  List<String> listOfShoppingLists = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);
    RecyclerView recyclerView = findViewById(R.id.list_of_shopping_list_recycler_view);

    // should be done during graphic design
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    final ListOfShoppingListsViewAdapter adapter = new ListOfShoppingListsViewAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    ShoppingListsViewModel listOfShoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListsViewModel.class);
    List<String> datesAsKey = new ArrayList<>();
    listOfShoppingListViewModel.getAllShoppingLists().observe(this, shoppingLists -> {
      adapter.setShoppingLists(shoppingLists);

      for (DataSnapshot date : shoppingLists.getChildren()) {
        datesAsKey.add(date.getKey());
      }

//      potrzebne do onclicklistenera
//      listOfShoppingLists.addAll(datesAsKey);
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.shopping_list_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void goToShoppingList(View view) {
    Intent shoppingListIntent = new Intent(view.getContext(), ShoppingList.class);
    view.getContext().startActivity(shoppingListIntent);
  }
}
