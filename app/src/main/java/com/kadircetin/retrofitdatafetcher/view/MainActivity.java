package com.kadircetin.retrofitdatafetcher.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kadircetin.retrofitdatafetcher.R;
import com.kadircetin.retrofitdatafetcher.adapter.RecyclerViewAdapter;
import com.kadircetin.retrofitdatafetcher.databinding.ActivityMainBinding;
import com.kadircetin.retrofitdatafetcher.model.CryptoModel;
import com.kadircetin.retrofitdatafetcher.service.CryptoAPI;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://pro-api.coinmarketcap.com/v1/";
    private Retrofit retrofit;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();

        settings();
    }

    public void loadData() {
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                cryptoAPI.getLatestCryptos(
                                "31e05670233541aa9d76ef9a8afc6b20",  // API KEY
                                1,
                                100,
                                "USD"
                        ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            cryptoModels = new ArrayList<>();
                            for (CryptoModel.CryptoData data : response.data) {
                                CryptoModel model = new CryptoModel();
                                model.currency = data.symbol;
                                model.price = String.valueOf(data.quote.usd.price);
                                cryptoModels.add(model);
                            }

                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                            binding.recyclerView.setAdapter(recyclerViewAdapter);
                        }, Throwable::printStackTrace));
    }

    public void settings() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}