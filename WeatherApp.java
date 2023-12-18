WeatherApp.java (Main Class):

java

import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the city: ");
        String city = scanner.nextLine();

        WeatherFetcher weatherFetcher = new WeatherFetcher();
        try {
            WeatherData weatherData = weatherFetcher.fetchWeather(city);
            System.out.println("Weather Details for " + city + ":");
            System.out.println(weatherData);
        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

WeatherFetcher.java:

java

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {
    private static final String API_KEY = "your_api_key_here"; // Replace with a valid API key

    public WeatherData fetchWeather(String city) throws IOException {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up connection properties
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Get response code
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherData weatherData = objectMapper.readValue(connection.getInputStream(), WeatherData.class);
            return weatherData;
        } else {
            throw new IOException("Failed to fetch weather data. Response code: " + responseCode);
        }
    }
}

WeatherData.java:

java

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    private Main main;
    private Wind wind;
    private String name;
    private String weatherDescription;

    // Getter and setter methods

    @Override
    public String toString() {
        return "Temperature: " + main.getTemp() + "°C\n" +
               "Humidity: " + main.getHumidity() + "%\n" +
               "Wind Speed: " + wind.getSpeed() + " m/s\n" +
               "Weather: " + weatherDescription;
    }
}

Main.java:

java

public class Main {
    private double temp;
    private int humidity;

    // Getter and setter methods
}

### Wind.java:

```java
public class Wind {
    private double speed;
