package com.git;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

/**
 * @since 2020-12-05
 * Тест на заведение git Issue на чистом селениде
 * @author NineLifeCat
 * @version 1.0
 */

public class CleanSelenideTest {
    final String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
    final String password = Files.readAllLines(Paths.get("src/test/resources/password")).get(0);

    public CleanSelenideTest() throws IOException {
    }

    static void setUp() {
        Configuration.startMaximized = true;
    }

    @Test
    void selenideTest() {
        open("https://github.com/login");
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();
        open("https://githubbb.com/");


    }
}
