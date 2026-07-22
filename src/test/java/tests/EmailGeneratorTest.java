package tests;

import base.BaseTest;
import org.testng.annotations.Test;

public class EmailGeneratorTest extends BaseTest {

    @Test(priority = 1)
    public void setupBeforeGeneratorTests() {
        loginPage
                .openPage()
                .login(getProperty("user.email"), getProperty("user.password"));
    }

    @Test(priority = 2, dependsOnMethods = "setupBeforeGeneratorTests")
    public void testEmailGenerationFlow() {
        emailPage
                .openEmailModule()
                .fillMessageToReply("Здравствуйте, отправляю вам документы.")
                .fillContextInput("Согласиться на условия и предложить встречу.")
                .clickGenerate();
    }

    @Test(priority = 3, dependsOnMethods = "setupBeforeGeneratorTests")
    public void testEmptyFieldsValidation() {
        emailPage
                .openEmailModule()
                .clearMessageAndVerifyValidation();
    }
}
