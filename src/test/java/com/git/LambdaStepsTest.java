package com.git;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class LambdaStepsTest {
    final Random random = new Random();
    String issueTitle = "Issue № " + random;
    final String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
    final String password = Files.readAllLines(Paths.get("src/test/resources/password")).get(0);
    final String issueBody = Files.readAllLines(Path.of("src/test/resources/issueBody")).toString();

    public LambdaStepsTest() throws IOException {
    }

    static void setUp() {
        Configuration.startMaximized = true;
    }

    @Test
    void LambdaStepTest() {
        step("Открываем страницу с логином на git.com", () -> {
            open("https://github.com/login");
        });
        step("Вводим логин и пароль от учетной записи", () -> {
            $(byName("login")).setValue(login);
            $(byName("password")).setValue(password).pressEnter();
            $(".shelf-title").shouldHave(Condition.text("Learn Git and GitHub without any code!"));
        });
        step("Ищем и выбираем репозиторий IssueGitTests", () -> {
            $(byText("Show more")).click();
            $(byTitle("IssueGitTests")).shouldHave(Condition.text("IssueGitTests"));
            $(byTitle("IssueGitTests")).click();
            $("[data-content='Issues']").shouldHave(Condition.text("Issues"));
        });
        step("Переходм на вкладку Issue", () -> {
            $("[data-content='Issues']").click();
            $(".col-9").shouldHave(Condition.text("Label issues and pull requests for new contributors"));
        });
        step("Заполняем Issue Title", () -> {
            $("[data-hotkey='c']").click();
            $(byName("issue[title]")).setValue(issueTitle);
            $(byName("issue[title]")).shouldHave(Condition.value(issueTitle));
        });
        step("Заполняем Assignees", ()-> {
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldHave(Condition.text("NineLifeCat666"));
            $("[class='select-menu-item-heading']").click();
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldNotBe(Condition.visible);
        });
        step("Заполняем Labels", () -> {
            $("[data-hotkey='l']").click();
            $("[data-name='2559498458']").shouldHave(Condition.text("Bug"));
            $("[data-name='2559498458']").click();
            $("[data-name='2559498463']").shouldHave(Condition.text("enhancement"));
            $("[data-name='2559498463']").click();
            $("[data-name='2559498471']").shouldHave(Condition.text("question"));
            $("[data-name='2559498471']").click();
            $("[data-hotkey='l']").click();
            $("[data-filterable-type='substring']").shouldNotBe(Condition.visible);
        });
        step("Оставляем коммент", () -> {
            $(byName("issue[body]")).click();
            $(byName("issue[body]")).setValue(issueBody);
            $(byName("issue[body]")).shouldHave(Condition.value(issueBody));
        });
        step("Нажимаем Submit new Issue", () ->{
            $(byText("Submit new issue")).click();
            $("[data-hovercard-type='user']").shouldHave(Condition.text("NineLifeCat666"));
        });
    }
}



