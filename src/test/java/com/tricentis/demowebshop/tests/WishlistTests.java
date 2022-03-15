package com.tricentis.demowebshop.tests;
import com.tricentis.demowebshop.base.TestBase;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class WishlistTests extends TestBase {
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


}
