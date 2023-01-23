module dev.carter.tradingbot {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires binance.connector.java;
    requires com.google.gson;
    requires java.mail;
    requires activation;

    opens dev.carter.tradingbot to javafx.fxml;
    exports dev.carter.tradingbot.userdata;
    opens dev.carter.tradingbot.userdata to javafx.fxml;
    exports dev.carter.tradingbot.bot;
    opens dev.carter.tradingbot.bot to javafx.fxml;
    exports dev.carter.tradingbot.tradingalgorithm;
    opens dev.carter.tradingbot.tradingalgorithm to javafx.fxml;
    exports dev.carter.tradingbot.marketdata;
    opens dev.carter.tradingbot.marketdata to javafx.fxml;
    exports dev.carter.tradingbot.userinterface;
    opens dev.carter.tradingbot.userinterface to javafx.fxml;
    exports dev.carter.tradingbot.privatedata;
    opens dev.carter.tradingbot.privatedata to javafx.fxml;
}