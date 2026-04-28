package com.kadircetin.retrofitdatafetcher.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CryptoModel {
    @SerializedName("currency")
    public String currency;
    @SerializedName("price")
    public String price;

    public class CmcResponse {
        public List<CryptoData> data;
    }

    public class CryptoData {
        public String name;
        public String symbol;
        public Quote quote;
    }

    public class Quote {
        @SerializedName("USD")
        public Usd usd;
    }

    public class Usd {
        public double price;
    }
}
