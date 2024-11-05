
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Gemini {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";  // Adjust endpoint if needed
    private static final String API_KEY = "";  // Replace with your actual API key

    public String aiOutput(String userInput) {
        String text = "Failed";

        String combinedInput = userInput + " (in few lines)";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + "?key=" + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"contents\":[{\"parts\":{\"text\":\"" + combinedInput + "\"}}]}"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            String jsonString = responseBody;
            String startDelimiter = "\"text\": \"";
            String endDelimiter = "\"role\"";
        
            int startIndex = jsonString.indexOf(startDelimiter) + startDelimiter.length();
            int endIndex = jsonString.indexOf(endDelimiter, startIndex) - 35;
        
            text = jsonString.substring(startIndex, endIndex);
            text = text.replace("\\\"", "\"").replace("\\n", " ").replace("**"," ").trim();   
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to connect to Gemini API.");
        }

        return text;
    }
}
