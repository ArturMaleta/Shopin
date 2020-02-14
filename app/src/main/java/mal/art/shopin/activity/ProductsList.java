package mal.art.shopin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.adapter.ProductListAdapter;
import mal.art.shopin.model.Product;
import mal.art.shopin.R;
import mal.art.shopin.viewModel.ProductViewModel;

public class ProductsList extends AppCompatActivity implements ProductListAdapter.OnAddProductListener {

  public static final int PRODUCT_ADD_REQUEST_CODE = 1;

  private RecyclerView recyclerView;

  private List<Product> productsList = new ArrayList<>();

  private ImageView opacityToBackgroundImage;

  private ProductViewModel productViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);
    recyclerView = findViewById(R.id.product_list_recycler_view);

    // should be done during graphic design
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    final ProductListAdapter adapter = new ProductListAdapter(this, this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    productViewModel.getAllProducts().observe(this, products -> {
      adapter.setProducts(products);
      for (Product currProduct : products) {
        productsList.add(currProduct);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.product_list_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void goToProductsList(View view) {
    Intent productsListIntent = new Intent(view.getContext(), ProductsList.class);
    view.getContext().startActivity(productsListIntent);
  }

  @Override
  public void onProductClick(int position) {
    String productName = productsList.get(position).getProductName();
    Intent intent = new Intent(ProductsList.this, AddProductToShoppingList.class);
    intent.putExtra("productNameToAdd", productName);
    startActivityForResult(intent, PRODUCT_ADD_REQUEST_CODE);
//    Toast.makeText(ProductsList.this, "click " + productName, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PRODUCT_ADD_REQUEST_CODE && resultCode == RESULT_OK) {
      String productName = data.getStringExtra("productName");
      String productQuantity = data.getStringExtra("productQuantity");
      String productUnit = data.getStringExtra("productUnit");

      Log.d("PRODUKT ZAPISANY DO FB", productName + " " + productQuantity + " " + productUnit);
    }
  }
}
