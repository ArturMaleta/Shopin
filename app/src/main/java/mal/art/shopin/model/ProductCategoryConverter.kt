package mal.art.shopin.model

import androidx.room.TypeConverter

class ProductCategoryConverter {
  @TypeConverter
  fun fromEnumToString(productCategoryEnum: ProductCategoryEnum): String {
    return productCategoryEnum.toString()
  }

  @TypeConverter
  fun fromStringToEnum(productCategoryEnumString: String): ProductCategoryEnum {
    return ProductCategoryEnum.valueOf(productCategoryEnumString)
  }
}