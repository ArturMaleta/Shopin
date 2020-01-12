package mal.art.shopin.View;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;

public class AddNewProduct extends AppCompatActivity {

  public static final String SAVE_REPLY = "com.example.android.wordlistsql.REPLY";

  private Button saveProductBtn;

  private EditText mProductName;
  private EditText mProductCategory; // tutaj będzie enum w pickerze

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_product);

    mProductName = findViewById(R.id.product_name_to_save);
    mProductCategory = findViewById(R.id.product_category_to_save);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

    int width = dm.widthPixels;
    int height = dm.heightPixels;

    getWindow().setLayout((int)(width*.8), (int)(height*.35));

    //OGARNIJ ZAPISYWANIE PRODUKTÓW - DLACZEGO ZAPISUJE SIĘ DWA RAZY KATEGORIA

    saveProductBtn = findViewById(R.id.save_button);
    saveProductBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mProductName.getText()) && TextUtils.isEmpty(mProductCategory.getText())) {
          setResult(RESULT_CANCELED, replyIntent);
        } else {
          String savedProductName = mProductName.getText().toString();
          String savedProductCategory = mProductCategory.getText().toString();
          Product productToPut = new Product(savedProductName, savedProductCategory);
//          czemu zapisuje się dwa razy productCategory? :(
//          replyIntent.putExtra(SAVE_REPLY, productToPut);
          setResult(RESULT_OK, replyIntent);
        }
        finish();
      }
    });
  }
}
