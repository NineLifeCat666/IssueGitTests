package com.git;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AnnotationStepsTest {
    final Random random = new Random();
    String issueTitle = "Issue № " + random;
    static final String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
    static final String password = Files.readAllLines(Paths.get("src/test/resources/password")).get(0);
    final String issueBody = Files.readAllLines(Path.of("src/test/resources/issueBody")).toString();

    public AnnotationStepsTest() throws IOException {
    }

    static void setUp() {
        Configuration.startMaximized = true;
    }

    public static class BaseSteps{
        @Step("Открываем страницу с логином на git.com")
        public void openLoginPage(){
            open("https://github.com/login");
        }
        @Step("Вводим логин и пароль от учетной записи")
        public void setLoginAndPassword(){
            $(byName("login")).setValue(login);
            $(byName("password")).setValue(password).pressEnter();
            $(".shelf-title").shouldHave(Condition.text("Learn Git and GitHub without any code!"));
        }
        @Step("Ищем и выбираем репозиторий IssueGitTests")
        public void searchRepository(){
            $(byText("Show more")).click();
            $(byTitle("IssueGitTests")).shouldHave(Condition.text("IssueGitTests"));
            $(byTitle("IssueGitTests")).click();
            $("[data-content='Issues']").shouldHave(Condition.text("Issues"));
        }
        @Step("переходм на вкладку Issue")
        public void redirectToIssue(){
            $("[data-content='Issues']").click();
            $(".col-9").shouldHave(Condition.text("Label issues and pull requests for new contributors"));
        }
        @Step("Заполняем Issue Title")
        public void setValueIssueTitle(){
            $("[data-hotkey='c']").click();
            $(byName("issue[title]")).setValue(issueTitle);
            $(byName("issue[title]")).shouldHave(Condition.value(issueTitle));
        }
        @Step("Заполняем Assignees")
        public void setValueAssignees(){
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldHave(Condition.text("NineLifeCat666"));
            $("[class='select-menu-item-heading']").click();
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldNotBe(Condition.visible);
        }
        @Step("Заполняем Labels")
        public void setValueLabels(){
            $("[data-hotkey='l']").click();
            $("[data-name='2559498458']").shouldHave(Condition.text("Bug"));
            $("[data-name='2559498458']").click();
            $("[data-name='2559498463']").shouldHave(Condition.text("enhancement"));
            $("[data-name='2559498463']").click();
            $("[data-name='2559498471']").shouldHave(Condition.text("question"));
            $("[data-name='2559498471']").click();
            $("[data-hotkey='l']").click();
            $("[data-filterable-type='substring']").shouldNotBe(Condition.visible);
        }
        @Step("Оставляем коммент")
        public void leaveComment(){
            $(byName("issue[body]")).click();
            $(byName("issue[body]")).setValue(issueBody);
            $(byName("issue[body]")).shouldHave(Condition.value(issueBody));
        }
        @Step("Нажимаем Submit new Issue")
        public void pressSubmitNewIssue(){
            $(byText("Submit new issue")).click();
            $("[data-hovercard-type='user']").shouldHave(Condition.text("NineLifeCat666"));
        }
    }
}
