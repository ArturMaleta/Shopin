package mal.art.shopin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.Adapter.ProductListAdapter;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;
import mal.art.shopin.ViewModel.ProductViewModel;

public class ProductsList extends AppCompatActivity implements ProductListAdapter.OnAddProductListener {

  public static final int REQUEST_CODE = 1;

  private RecyclerView recyclerView;

  private List<Product> mProductsList = new ArrayList<>();

  private ImageView opacityToBackgroundImage;

  private ProductViewModel mProductViewModel;

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

    mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    mProductViewModel.getAllProducts().observe(this, products -> {
      adapter.setProducts(products);
    });
  }

  public void goToProductsList(View view) {
    Intent productsListIntent = new Intent(view.getContext(), ProductsList.class);
    view.getContext().startActivity(productsListIntent);
  }

  @Override
  public void onProductClick(int position) {
    // Dokończ to wywoływanie okna do dodawania produktu do listy zakupów. REQUEST_CODE jest placeholderem
    // jak sprawdzić który produkt jest klikalny - czyli do czego robić odowłanie .get(position)
    //    mProductsList.get(position);
    //    Intent intent = new Intent(ProductsList.this, AddProductToShoppingList.class);
    //    startActivityForResult(intent, REQUEST_CODE);

    Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
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
