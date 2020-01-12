package mal.art.shopin.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;
import mal.art.shopin.ViewModel.ProductViewModel;

public class MainScreen extends AppCompatActivity {

  public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

  ImageView opacityToBackgroundImage;

  Button goToShoppingList;

  Button goToProductsList;

  Button addNewProduct;

  private ProductViewModel mProductViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_screen);

    // później graficznie ogarnę tę przezroczystość, żeby tylko zdjęcie ładować, a nie jeszcze ustawiać mu opacity
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    goToShoppingList = findViewById(R.id.goToShoppingList_btn);
    goToShoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.goToShoppingList(v);
      }
    });

    goToProductsList = findViewById(R.id.createShoppingList_btn);
    goToProductsList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ProductsList productsList = new ProductsList();
        productsList.goToProductsList(v);
      }
    });

    addNewProduct = findViewById(R.id.addProduct_btn);
    addNewProduct.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent goToAddNewProductIntent = new Intent(MainScreen.this, AddNewProduct.class);
        startActivityForResult(goToAddNewProductIntent, NEW_WORD_ACTIVITY_REQUEST_CODE);
      }
    });

        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Product product = data.getParcelableExtra(AddNewProduct.SAVE_PRODUCT_REPLY);
      mProductViewModel.insert(product);

      Log.d("PRODUKT ZAPISANY", product.getProductName() + " " + product.getProductCategory());
    }
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      hideSystemUI();
    }
  }

  private void hideSystemUI() {
    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_IMMERSIVE
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // Hide the nav bar and status bar
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }
}

// change text color in button