package com.cvc.vis.javatrigger.poc;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HTTPPostDBTrigger {

    //  TODO Pull some of the constants out into a config file!

    public static void publish(int id, float price, String title) throws SQLException {

        //  First update a shadow table just to keep an "audit trail"
        //  This step is not really necessary, especially in Java, but it helps
        // confirm the Java code is running

        System.out.println("Entered the publish() method.");
        System.out.println("Performing row copy..");

        Connection conn = DriverManager.getConnection("jdbc:default:connection:");
        String sql = "INSERT INTO MOVIE_SHADOW VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.setDouble(2, price);
            pstmt.setString(3, title + " (Shadow)");
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Row copy complete.");

        System.out.println("Starting HTTP Post...");

        //  Next POST the update to an HTTP URL
        final String USER_AGENT = "Mozilla/5.0";

//        String url = "http://167.206.9.83:8080/javatrigger/poc/movie/add";
//        String url = "http://localhost:8888/javatrigger/poc/movie/add";
        String url = "http://puma-a.sdg.cv.net:9989/javatrigger/poc/movie/add";

        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            // add header
            post.setHeader("User-Agent", USER_AGENT);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept", "application/json");

            String jsonString = "{\"id\": " + id + ", \"price\":" + price + ", \"title\": \"" + title + "\"}";
            post.setEntity(new StringEntity(jsonString));

            System.out.println("\nSending 'POST' request to URL : " + url);

            HttpResponse response = client.execute(post);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("HTTP Post Complete");
        System.out.println("Returning from publish() method.");

    }

    public static void main(String args[]) {
        try {
            HTTPPostDBTrigger.publish(99, (float)5.95, "Fast and Furious");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
