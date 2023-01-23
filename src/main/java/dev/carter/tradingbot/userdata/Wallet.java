package dev.carter.tradingbot.userdata;

import com.binance.connector.client.impl.SpotClientImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.carter.tradingbot.marketdata.Coin;
import dev.carter.tradingbot.privatedata.PrivateConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Wallet {

    private ArrayList<Coin> balances;

    public Wallet(){
        balances = new ArrayList<>();
        updateBalances();
    }

    public void updateBalances(){
        ArrayList<Coin> balances = new ArrayList<>();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createWallet().getUserAsset(parameters);
        JsonArray coins = JsonParser.parseString(result).getAsJsonArray();
        for(int i = 0; i < coins.size(); i++){
            JsonObject balance = coins.get(i).getAsJsonObject();
            Coin crypto = new Coin();
            crypto.setName(balance.get("asset").getAsString());
            crypto.setQuantity(balance.get("free").getAsFloat());
            crypto.setBtcValue(balance.get("btcValuation").getAsFloat());
            balances.add(crypto);
        }
        setBalances(balances);
    }

    public ArrayList<Coin> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<Coin> balances) {
        this.balances = balances;
    }
}
