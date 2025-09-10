package ru.iteco.fmhandroid.ui.test;

import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.data.TestDataInfo;
import ru.iteco.fmhandroid.ui.page.AuthorizationPage;
import ru.iteco.fmhandroid.ui.page.AboutApplicationPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AboutApplicationTest extends AboutApplicationPage {

    AboutApplicationPage aboutApplicationPage = new AboutApplicationPage();

    @Before
    public void setUp() {
        // авторизация перед переходом
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.inputValidLoginInFieldLogin(TestDataInfo.getValidLogin());
        authorizationPage.inputValidPasswordInFieldPassword(TestDataInfo.getValidPassword());
        authorizationPage.clickInEnterButton();

        // открываем раздел "О приложении"
        aboutApplicationPage.clickNavButton();
        aboutApplicationPage.clickButtonAbout();
    }

    @Test
    @DisplayName("ТС-005. Переход по ссылке «Политика конфиденциальности»")
    @io.qameta.allure.kotlin.Description("Проверка открытия интента при переходе по ссылке «Политика конфиденциальности»")
    public void test_011_intentPrivacyPolicy() {
        aboutApplicationPage.clickLinkPrivacyPolicy();
    }

    @Test
    @DisplayName("ТС-048. Переход по ссылке «Пользовательское соглашение»")
    @io.qameta.allure.kotlin.Description("Проверка открытия интента при переходе по ссылке «Пользовательское соглашение»")
    public void test_012_intentUserAgreement() {
        aboutApplicationPage.clickLinkUserAgreement();
    }

    @Test
    @DisplayName("ТС-014. Отображение текущей версии приложения на странице")
    @io.qameta.allure.kotlin.Description("Проверка корректного отображения актуальной версии приложения в разделе «О приложении»")
    public void test_013_actualVersion() {
        aboutApplicationPage.viewActualVersion();
    }
}
