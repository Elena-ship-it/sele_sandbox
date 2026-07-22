package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@SuppressWarnings("UnusedReturnValue")
public class EmailPage {
    // НАДЁЖНЫЕ ЛОКАТОРЫ: Просто берём первую и вторую текстовую область на странице
    private final SelenideElement messageToReplyInput = $("textarea", 0);
    private final SelenideElement contextInput = $("textarea", 1);

    // Кнопка генерации и сообщение об ошибке
    private final SelenideElement generateButton = $x("//button[contains(., 'Сгенерировать') or @type='submit']");
    private final SelenideElement errorMessage = $x("//*[contains(text(), 'сообщение') or contains(text(), 'Пожалуйста') or contains(@class, 'error')]");

    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    public EmailPage openEmailModule() {
        open("/app/email-answer/");
        // Ждем полную загрузку первого текстового поля
        messageToReplyInput.shouldBe(visible, TIMEOUT);
        return this;
    }

    public EmailPage fillMessageToReply(String text) {
        messageToReplyInput.shouldBe(visible, TIMEOUT).clear();
        // Сразу пишем через JS в обход блокировки ChromeDriver на символы эмодзи
        executeJavaScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", messageToReplyInput, text);
        messageToReplyInput.shouldHave(exactValue(text));
        return this;
    }

    public EmailPage fillContextInput(String text) {
        contextInput.shouldBe(visible, TIMEOUT).clear();
        // Сразу пишем через JS в обход блокировки ChromeDriver на символы эмодзи
        executeJavaScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", contextInput, text);
        contextInput.shouldHave(exactValue(text));
        return this;
    }

    public EmailPage PasteWithHotkeys(String text) {
        messageToReplyInput.shouldBe(visible, TIMEOUT).click();
        executeJavaScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", messageToReplyInput, text);
        messageToReplyInput.shouldHave(exactValue(text));
        return this;
    }

    public EmailPage checkMessageToReplyScroll(String baseText) {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 40; i++) longText.append(baseText).append(" ");
        messageToReplyInput.shouldBe(visible, TIMEOUT).setValue(longText.toString());

        boolean isScrollable = Boolean.TRUE.equals(executeJavaScript("return arguments[0].scrollHeight > arguments[0].clientHeight;", messageToReplyInput));
        assert isScrollable : "Поле сообщения не прокручивается!";
        return this;
    }

    public EmailPage checkContextInputScroll(String baseText) {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 40; i++) longText.append(baseText).append(" ");
        contextInput.shouldBe(visible, TIMEOUT).setValue(longText.toString());

        boolean isScrollable = Boolean.TRUE.equals(executeJavaScript("return arguments[0].scrollHeight > arguments[0].clientHeight;", contextInput));
        assert isScrollable : "Поле по сути не прокручивается!";
        return this;
    }

    public EmailPage clearMessageAndVerifyValidation() {
        messageToReplyInput.shouldBe(visible, TIMEOUT).click();
        messageToReplyInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        messageToReplyInput.shouldBe(empty);
        generateButton.shouldBe(interactable, TIMEOUT).click();
        errorMessage.shouldBe(visible, TIMEOUT);
        return this;
    }

    public EmailPage clickGenerate() {
        generateButton.shouldBe(interactable, TIMEOUT).click();
        return this;
    }
}
