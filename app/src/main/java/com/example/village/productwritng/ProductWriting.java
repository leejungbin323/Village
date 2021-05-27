package com.example.village.productwritng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.village.MainActivity;
import com.example.village.R;
import com.example.village.databinding.ActivityProductWritingBinding;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductWriting extends AppCompatActivity {

    private ActivityProductWritingBinding binding;
    private final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_writing);
        binding.setActivity(this);

        String price;
        final String[] strAmount = {""};

        binding.goHomeBtn.setOnClickListener(v -> { finish(); });

        binding.priceEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount[0])) {
                    strAmount[0] = toComma(s.toString().replace(",", ""));
                    binding.priceEtv.setText(strAmount[0]);
                    Editable editable = binding.priceEtv.getText();
                    Selection.setSelection(editable, strAmount[0].length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void callGallery (View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
        Log.e("a","79line");
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProductAdapter adapter;
        ArrayList<ProductData> arrayList = new ArrayList<>();
        ProductData productData;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.imageRecycleView.setLayoutManager(layoutManager);
        Log.e("a","79line");
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if(data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                if (count > 9) {
                    Toast.makeText(getApplicationContext(),"최대 9개까지 업로드 가능합니다.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                for (int i=0; i<count; i++) {
                    productData = new ProductData(data.getClipData().getItemAt(i).getUri());
                    arrayList.add(productData);
                }
            }
        } else {
            try {
                productData = new ProductData(data.getData());
                arrayList.add(productData);
            } catch (RuntimeException ignored) {}
        }
        adapter = new ProductAdapter(arrayList);
        binding.imageRecycleView.setAdapter(adapter);
    }

    protected String toComma(String str) {
        Double value  = Double.parseDouble(str);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value);
    }
}