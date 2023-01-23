package dev.carter.tradingbot.marketdata;

public class Coin {
    private String name;
    private float quantity;
    private float btcValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getBtcValue() {
        return btcValue;
    }

    public void setBtcValue(float btcValue) {
        this.btcValue = btcValue;
    }
}
