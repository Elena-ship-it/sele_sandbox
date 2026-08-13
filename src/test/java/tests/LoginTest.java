package tests;

import base.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void authAndProjectIsOpen() {
        loginPage
                .openPage()
                .login(getProperty("user.email"), getProperty("user.password"));
        emailPage
                .openEmailModule();
    }

    @Test(priority = 2, dependsOnMethods = "authAndProjectIsOpen")
    public void testC299_StandardTextInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Добрый день! Прошу вас настроить наше предложение. Контактный телефон: 8-900-123-45-67")
                .clickGenerate();
    }

    @Test(priority = 3, dependsOnMethods = "authAndProjectIsOpen")
    public void testC303_EmojiInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Привет! Нам нужно обсудить проект. Свяжитесь со мной, пожалуйста. ")
                .clickGenerate();
    }

    @Test(priority = 4, dependsOnMethods = "authAndProjectIsOpen")
    public void testC305_CyrillicAndLatinInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Привет! Добрый день! Пожалуйста, ответьте как можно скорее.")
                .clickGenerate();
    }

    @Test(priority = 5, dependsOnMethods = "authAndProjectIsOpen")
    public void testC306_HtmlTagsInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("<b>Привет!</b><i>Нужно обсудить проект.</i>")
                .clickGenerate();
    }

    @Test(priority = 6, dependsOnMethods = "authAndProjectIsOpen")
    public void testC308_LongUrlInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("https://example.com")
                .clickGenerate();
    }

    @Test(priority = 7, dependsOnMethods = "authAndProjectIsOpen")
    public void testC310_TabsAndIndentsInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Привет! \n Это абзац с отступом. \n А это ещё больший отступ")
                .clickGenerate();
    }

    @Test(priority = 8, dependsOnMethods = "authAndProjectIsOpen")
    public void testC311_EmptyLinesInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Привет! \n \n Это письмо с пустыми строками. \n \n Пожалуйста, ответьте")
                .clickGenerate();
    }

    @Test(priority = 9, dependsOnMethods = "authAndProjectIsOpen")
    public void testC312_SpecialSymbolsInMessage() {
        emailPage.openEmailModule()
                .fillMessageToReply("Кафе, наивное, резюме, π (пи), √ (корень), © (копирайт)")
                .clickGenerate();
    }

    @Test(priority = 10, dependsOnMethods = "authAndProjectIsOpen")
    public void testC313_HotkeyPasteInMessage() {
        emailPage.openEmailModule()
                .PasteWithHotkeys("Это тестовый текст для проверки самых горячих клавиш");
    }

    @Test(priority = 11, dependsOnMethods = "authAndProjectIsOpen")
    public void testC315_VerticalScrollInMessage() {
        emailPage.openEmailModule()
                .checkMessageToReplyScroll("Привет! Пожалуйста, ответьте.");
    }

    @Test(priority = 12, dependsOnMethods = "authAndProjectIsOpen")
    public void testC317_FieldClearingAndValidation() {
        emailPage.openEmailModule()
                .clearMessageAndVerifyValidation();
    }

    @Test(priority = 13, dependsOnMethods = "authAndProjectIsOpen")
    public void testC321_StandardTextInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Добрый день! Прошу вас изменить наше предложение. Контактный телефон: 8-900-123-45-67")
                .clickGenerate();
    }

    @Test(priority = 14, dependsOnMethods = "authAndProjectIsOpen")
    public void testC322_EmojiInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Привет! Нам нужно обсудить проект. Свяжитесь со мной, пожалуйста. ")
                .clickGenerate();
    }

    @Test(priority = 15, dependsOnMethods = "authAndProjectIsOpen")
    public void testC323_CyrillicAndLatinInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Привет! Добрый день! Пожалуйста, ответьте как можно скорее.")
                .clickGenerate();
    }

    @Test(priority = 16, dependsOnMethods = "authAndProjectIsOpen")
    public void testC324_HtmlTagsInContext() {
        emailPage.openEmailModule()
                .fillContextInput("<b>Привет!</b><i>Нужно обсудить проект.</i>")
                .clickGenerate();
    }

    @Test(priority = 17, dependsOnMethods = "authAndProjectIsOpen")
    public void testC325_LongUrlInContext() {
        emailPage.openEmailModule()
                .fillContextInput("https://example.com")
                .clickGenerate();
    }

    @Test(priority = 18, dependsOnMethods = "authAndProjectIsOpen")
    public void testC326_TabsAndIndentsInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Привет! \n Это абзац с отступом. \n А это еще больший отступ")
                .clickGenerate();
    }

    @Test(priority = 19, dependsOnMethods = "authAndProjectIsOpen")
    public void testC327_EmptyLinesInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Привет! \n \n Это письмо с пустыми строками. \n \n Пожалуйста, ответьте")
                .clickGenerate();
    }

    @Test(priority = 20, dependsOnMethods = "authAndProjectIsOpen")
    public void testC328_SpecialSymbolsInContext() {
        emailPage.openEmailModule()
                .fillContextInput("Кафе, наивное, резюме, π (пи), √ (корень), © (копирайт)")
                .clickGenerate();
    }

    @Test(priority = 21, dependsOnMethods = "authAndProjectIsOpen")
    public void testC329_VerticalScrollInContext() {
        emailPage.openEmailModule()
                .checkContextInputScroll("Привет! Пожалуйста, ответьте.");
    }
}
