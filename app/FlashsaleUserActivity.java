import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FlashssaleUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashsale_user);


        TextView originalPriceTextView = findViewById(R.id.product_discounted_price1);
        String originalPriceText = "49.000đ";

        // Tạo một SpannableString chứa văn bản giá gốc
        SpannableString spannableString = new SpannableString(originalPriceText);

        // Áp dụng gạch ngang và chỉnh màu đỏ cho gạch ngang
        spannableString.setSpan(new StrikethroughSpan(), 0, originalPriceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, originalPriceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Đặt SpannableString vào TextView
        originalPriceTextView.setText(spannableString);
    }
}
