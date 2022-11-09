package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {

    protected Response createBooking() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("firstname", "Ali");
        jsonBody.put("lastname", "Zengin");
        jsonBody.put("totalprice", 199);
        jsonBody.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2022-02-02");
        bookingDates.put("checkout", "2022-03-03");
        jsonBody.put("bookingdates", bookingDates);
        jsonBody.put("additionalneeds", "air conditioner");

        // response
        Response response = RestAssured.given().contentType(ContentType.JSON).
                body(jsonBody.toString()).post("https://restful-booker.herokuapp.com/booking");
        return response;
    }

}
