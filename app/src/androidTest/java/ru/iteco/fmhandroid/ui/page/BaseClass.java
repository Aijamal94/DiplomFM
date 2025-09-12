package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.utils.LoadingPage.waitDisplayed;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

public class BaseClass {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private final int mainPageID = R.id.container_list_news_include_on_fragment_main;
    private final int loginButton = R.id.enter_button;
    private final int newsBox = R.id.all_news_text_view;

    private final String validLogin = "login2";
    private final String validPassword = "password2";

    @Before
    public void authorizationUserOrNot() {
        Allure.step("Шаг 1: Запустить приложение. Проверка: авторизован ли пользователь?");
        try {
            // если уже авторизованы → проверяем главную
            onView(isRoot()).perform(waitDisplayed(mainPageID, 15000));
            onView(withId(mainPageID)).check(matches(isDisplayed()));

        } catch (PerformException e) {
            // иначе → авторизация
            Allure.step("Шаг 2: Авторизация на экране логина");

            onView(isRoot()).perform(waitDisplayed(loginButton, 15000));

            // вводим логин
            onView(withHint("Login"))
                    .check(matches(isDisplayed()))
                    .perform(typeText(validLogin), closeSoftKeyboard());

            // вводим пароль
            onView(withHint("Password"))
                    .check(matches(isDisplayed()))
                    .perform(typeText(validPassword), closeSoftKeyboard());

            // нажимаем "Войти"
            onView(withId(loginButton)).check(matches(isDisplayed()));
            onView(withId(loginButton)).perform(click());

            // проверяем главную страницу
            onView(isRoot()).perform(waitDisplayed(newsBox, 15000));
            onView(withId(newsBox)).check(matches(isDisplayed()));
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}