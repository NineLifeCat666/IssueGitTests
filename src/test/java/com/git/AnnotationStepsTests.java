package com.git;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class AnnotationStepsTests {
    @BeforeAll
    static void setUp() {
        Configuration.startMaximized = true;
    }

        @Test
    public void annotationStepsTests() throws IOException {
            BaseSteps baseSteps = new BaseSteps();
            Configuration.startMaximized = true;
            baseSteps.openLoginPage();
            baseSteps.setLoginAndPassword();
            baseSteps.searchRepository();
            baseSteps.redirectToIssue();
            baseSteps.setValueIssueTitle();
            baseSteps.setValueAssignees();
            baseSteps.setValueLabels();
            baseSteps.leaveComment();
            baseSteps.pressSubmitNewIssue();
        }
}
