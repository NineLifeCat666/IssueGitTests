package com.git;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class BaseSteps {
        final Random random = new Random();
        String issueTitle = "Issue № " + random;
        final String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
        final String password = Files.readAllLines(Paths.get("src/test/resources/password")).get(0);
        final String issueBody = Files.readAllLines(Path.of("src/test/resources/issueBody")).toString();

        public BaseSteps() throws IOException {
        }

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
            $(byText("IssueGitTests")).click();
        }
        @Step("переходм на вкладку Issue")
        public void redirectToIssue(){
            $("[data-content='Issues']").click();
            $(".repository-content").shouldHave(text("Label issues and pull requests for new contributors"));
        }
        @Step("Заполняем Issue Title")
        public void setValueIssueTitle(){
            $("[data-hotkey='c']").click();
            $(byName("issue[title]")).setValue(issueTitle);
        }
        @Step("Заполняем Assignees")
        public void setValueAssignees(){
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldHave(text("NineLifeCat666"));
            $("[class='select-menu-item-heading']").click();
            $("[data-hotkey='a']").click();
            $("[class='select-menu-item-heading']").shouldNotBe(Condition.visible);
        }
        @Step("Заполняем Labels")
        public void setValueLabels(){
            $("[data-hotkey='l']").click();
            $$(".name").find(text("bug")).click();
            $$(".name").find(text("enhancement")).click();
            $$(".name").find(text("question")).click();
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
            $(".js-issue-title").shouldHave(Condition.text(issueTitle));
        }
    }
