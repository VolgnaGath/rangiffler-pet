package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {


    public abstract T checkThatPageLoaded();
}
