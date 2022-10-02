package com.demoqa.test;

import com.codeborne.selenide.Configuration;
import com.demoqa.testData.RadioButton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";

    }

    @ValueSource(strings = {"Elements", "Forms", "Alerts, Frame & Windows",
            "Widgets", "Interactions", "Book Store Application"})
    @ParameterizedTest(name = "Check {0}-card in body")
    void checkCardInBodyTest(String tabWebsite) {
        open("/");
        $$(".card-body h5").find(text(tabWebsite))
                .shouldHave(visible, text(tabWebsite));
    }

    @EnumSource(RadioButton.class)
    @ParameterizedTest(name = "Check click {0}-button")
    void clickRadioButtonTest(RadioButton radioButton) {
        open("/radio-button");
        $x("//label[text()='" + radioButton.button + "']").click();
    }

    @MethodSource("textAccordian")
    @ParameterizedTest(name = "Check accordian - {0}")
    void textAccordianTest(String cardHeader, int count, String cardBody) {
        open("/accordian");
        $x("//div[text()='" + cardHeader + "']").click();
        $("#section" + count + "Content p").shouldHave(ownText(cardBody));
    }

    static Stream<Arguments> textAccordian() {
        return Stream.of(
                Arguments.of("Where does it come from?", 2, "Contrary to popular belief"),
                Arguments.of("Why do we use it?", 3, "It is a long established fact")
        );
    }
}
