package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTests extends BaseTest{

    @Test
    public void healthCheckTest() {
        given().
                spec(spec).
                when().
                    get("/ping").
                then().
                    assertThat().
                    statusCode(201);
    }

    @Test
    public void headersAndCookies() {
        Header header = new Header("Header i", "Header value i");
        spec.header(header);
        Cookie cookie = new Cookie.Builder("cookie ii","cookie ii").build();
        spec.cookie(cookie);

        Response response = RestAssured.given(spec).
                cookie("Test Cookie name i","cookie value i").
                header("MyHeader","HeaderValue1").
                log().all(). // to see the logs for headers and cookies that we added now.
                get("/ping");

        //Get hEaders
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);
        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        //This will just return the value of the Header, not all of it.
        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server --->" + serverHeader2);

        //Get Cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);
    }

}
