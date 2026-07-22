package pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    // НАДЕЖНО: Ищем любую ссылку, кнопку или span внутри шапки сайта, содержащую текст "Вход"
    private final SelenideElement headerLoginButton = $$("header a, header button, header span, header [role='button']").findBy(text("Вход"));

    // Ссылка «Вход по паролю» на первом экране виджета
    private final SelenideElement initialLoginByPasswordButton = $x("//a[contains(., 'Вход по паролю')]");

    // Поле ввода "Эл. почта" на первом экране
    private final SelenideElement firstStepEmailInput = $("#email_request");

    // Кнопка-строка «Войти по паролю» в самом низу формы
    private final SelenideElement lowerLoginByPasswordLink = $x("//button[contains(@class, 'btn-link') and contains(., 'Войти по паролю')]");

    // Поле ввода "Эл. почта" на второй странице формы
    private final SelenideElement secondStepEmailInput = $("#email");

    // Поле ввода "Пароль" на второй странице
    private final SelenideElement passwordInput = $("#password");

    // Финальная синяя кнопка «Войти» на второй странице
    private final SelenideElement signInButton = $x("//form[@name='loginForm']//button[@type='submit']");

    // Локатор профиля для проверки, что мы уже авторизованы
    private final SelenideElement userProfileAvatar = $x("//*[contains(@class, 'avatar') or contains(@class, 'user') or contains(text(), 'Radostu')]");

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public LoginPage openPage() {
        open("/");
        // Принудительно убираем любые всплывающие Cookie-баннеры перед кликом
        try {
            executeJavaScript("var el = document.querySelector(\"div[class*='cookie']\"); if(el) el.remove();");
        } catch (Exception ignored) {
        }
        return this;
    }

    public EmailPage login(String email, String password) {
        // Шаг 1: Сразу проверяем, авторизованы ли мы. Без ожидания кнопки "Вход"!
        if (userProfileAvatar.exists()) {
            System.out.println("Пользователь уже авторизован, пропускаем шаг логина.");
            return new EmailPage();
        }

        // Шаг 2: Если аватара нет — значит мы разлогинены. Ищем кнопку и входим.
        headerLoginButton.shouldBe(visible, TIMEOUT);
        executeJavaScript("arguments[0].click();", headerLoginButton);
        sleep(1000);

        initialLoginByPasswordButton.shouldBe(visible, TIMEOUT);
        executeJavaScript("arguments[0].click();", initialLoginByPasswordButton);

        // Заполнение email на первом экране посимвольно для триггера Angular-валидации
        firstStepEmailInput.shouldBe(visible, TIMEOUT).clear();
        for (char ch : email.toCharArray()) {
            firstStepEmailInput.sendKeys(String.valueOf(ch));
            sleep(50);
        }

        executeJavaScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", firstStepEmailInput);
        executeJavaScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", firstStepEmailInput);

        lowerLoginByPasswordLink.shouldBe(interactable, TIMEOUT);
        executeJavaScript("arguments[0].click();", lowerLoginByPasswordLink);

        // Заполнение полей авторизации на втором экране
        secondStepEmailInput.shouldBe(visible, TIMEOUT).setValue(email);
        passwordInput.shouldBe(visible, TIMEOUT).setValue(password);

        // Финальный сабмит формы
        signInButton.shouldBe(visible, TIMEOUT);
        executeJavaScript("arguments[0].click();", signInButton);

        return new EmailPage();
    }
}
