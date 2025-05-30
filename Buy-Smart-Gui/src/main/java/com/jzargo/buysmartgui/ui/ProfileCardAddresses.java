package com.jzargo.buysmartgui.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ProfileCardAddresses {
    @FXML public VBox geo;
    @FXML private WebView webView;

    public void initialize(){
    WebEngine webEngine = webView.getEngine();

    String mapHTML = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="utf-8" />
              <title>Map</title>
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
              <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
            </head>
            <body style="margin:0">
            <div id="map" style="width: 100%; height: 100vh;"></div>
            <script>
              var map = L.map('map').setView([51.505, -0.09], 13);
              L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap'
              }).addTo(map);
            
              var marker = L.marker([51.505, -0.09]).addTo(map);
            
              map.on('click', function(e) {
                marker.setLatLng(e.latlng);
                // You can use alert or better: call Java methods via bridge
                alert("Coordinates: " + e.latlng.lat + ", " + e.latlng.lng);
              });
            </script>
            </body>
            </html>
            """;

            webEngine.loadContent(mapHTML);
            geo = new VBox(webView);
    }
}
