package mal.art.shopin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import mal.art.shopin.R;
import mal.art.shopin.model.Product;
import mal.art.shopin.model.ProductCategoryEnum;

public class AddNewProduct extends AppCompatActivity {

  public static final String SAVE_PRODUCT_REPLY = "mal.art.shopin.save_product.REPLY";

  private EditText productName;

  private Spinner productCategory;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_product);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

    int width = dm.widthPixels;
    int height = dm.heightPixels;

    getWindow().setLayout(
      (int) (width * .8),
      (int) (height * .35)
      //      (int) (width * getBaseContext().getResources().getDimension(R.dimen.popup_window_width_multiplier)),
      //      (int) (height * getBaseContext().getResources().getDimension(R.dimen.popup_window_height_multiplier))
    );

    productName = findViewById(R.id.product_name_to_save);
    productCategory = findViewById(R.id.add_new_product_spinner);
    Button saveProductBtn = findViewById(R.id.save_button);

    productCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ProductCategoryEnum.values()));

    saveProductBtn.setOnClickListener(v -> {
      Intent replyIntent = new Intent(AddNewProduct.this, MainScreen.class);
      if (TextUtils.isEmpty(productName.getText()) && TextUtils.isEmpty(productCategory.getSelectedItem().toString())) {
        setResult(RESULT_CANCELED, replyIntent);
      } else {
        Product productToPut = new Product(productName.getText().toString(), productCategory.getSelectedItem().toString());
        replyIntent.putExtra(SAVE_PRODUCT_REPLY, productToPut);
        setResult(RESULT_OK, replyIntent);
      }
      finish();
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.add_new_product_layout);
    overlay.setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
        View.SYSTEM_UI_FLAG_FULLSCREEN
    );
  }
}
