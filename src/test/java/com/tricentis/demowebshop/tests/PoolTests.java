package com.tricentis.demowebshop.tests;

import com.tricentis.demowebshop.base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.options;
import static org.hamcrest.Matchers.is;

public class PoolTests extends TestBase {

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

    //проголосовать (API) + результаты голосования чекнуть на (UI)
    @Test
    void successPool() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("pollAnswerId=2")
                .cookie("NOPCOMMERCE.AUTH", "57D213AA45D1D9653B1CE9F3575AD616997360E9F3068A0135F72B393A9E84F07DBDEEA884FD34EB79D5E7F85A0AD834C50DC1CFC9131A7C514CD9C78951686FC539F19950E0CB09ECE06EE333A04678D513EC1F4BCC49B3CD1A208547F7B7AD27B50C5E686A6533C3F42EDE1A30394A3B6850FA69A6A102C80251BD73B4CB22")
                .when()
                .post("/poll/vote")
                .then()
                .log().all()
                .statusCode(200);

        open("/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", "57D213AA45D1D9653B1CE9F3575AD616997360E9F3068A0135F72B393A9E84F07DBDEEA884FD34EB79D5E7F85A0AD834C50DC1CFC9131A7C514CD9C78951686FC539F19950E0CB09ECE06EE333A04678D513EC1F4BCC49B3CD1A208547F7B7AD27B50C5E686A6533C3F42EDE1A30394A3B6850FA69A6A102C80251BD73B4CB22"));

        open("");
        $(".poll-results").$$(".answer").findBy(text("Very bad (1708 vote(s) - 33.5%)")).shouldBe(visible);
    }
}
