
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class BankTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void test() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("h2").shouldBe(visible);
        $("h2").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void test_2() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=\"login\"] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(visible);
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void test_3() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=\"login\"] input").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(blockedUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(visible);
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void test_4() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id=\"login\"] input").setValue(wrongLogin);
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(visible);
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void test_5() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongPassword);
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(visible);
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
