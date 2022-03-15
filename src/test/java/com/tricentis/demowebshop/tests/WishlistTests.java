package com.tricentis.demowebshop.tests;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WishlistTests {

    @BeforeAll
    static void configureBaseUrl() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
    }

    //Добавить в список желаний позицию (API) + Проверить что в списке желаний 1 товар (UI)
    @Test
    void addItemToWishlist() {
        step("Добавление позиции в список желаний и передача куки пользователя в браузер", () -> {
        String cookie =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("addtocart_14.EnteredQuantity=1")
                        .when()
                        .post("/addproducttocart/details/14/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .cookie("Nop.customer");
        open("/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookie));
        });

        step("Открытие главной страницы", () ->
            open(""));

        step("Проверка что в вишлисте уже есть позиция", () ->
            $(".wishlist-qty").shouldHave(text("(1)")));
    }

    // проголосовать в опросе для коммьюнити анонимом (API)
    @Test
    void errorInPoolAnonimus(){
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("pollAnswerId=2")
                .when()
                .post("/poll/vote")
                .then()
                .log().all()
                .statusCode(200)
                .body("error", is("Only registered users can vote."));
    }
}
