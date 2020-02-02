package mal.art.shopin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;

public class AddNewProduct extends AppCompatActivity {

  public static final String SAVE_PRODUCT_REPLY = "com.example.android.wordlistsql.REPLY";

  private Button saveProductBtn;

  private EditText productName;

  private EditText productCategory; // tutaj będzie enum w pickerze

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_product);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

    int width = dm.widthPixels;
    int height = dm.heightPixels;

    getWindow().setLayout((int) (width * .8), (int) (height * .35));

    productName = findViewById(R.id.product_name_to_save);
    productCategory = findViewById(R.id.product_category_to_save);
    saveProductBtn = findViewById(R.id.save_button);

    saveProductBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent replyIntent = new Intent(AddNewProduct.this, MainScreen.class);
        if (TextUtils.isEmpty(productName.getText()) && TextUtils.isEmpty(productCategory.getText())) {
          setResult(RESULT_CANCELED, replyIntent);
        } else {
          Product productToPut = new Product(productName.getText().toString(), productCategory.getText().toString());
          replyIntent.putExtra(SAVE_PRODUCT_REPLY, productToPut);
          setResult(RESULT_OK, replyIntent);
        }
        finish();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.add_new_product_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }
}
