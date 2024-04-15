package login;

import api.BaseURL;
import api.UserAPI;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pagestellarburgers.*;
import registration.BaseTest;
import user.RandomUser;
import user.User;

public class TestLogin extends BaseTest {
    private UserAPI userAPI;
    private User user;
    private String accessToken;

    @Before
    public void createTestData(){
        userAPI = new UserAPI();
        user = RandomUser.userRandom();
        driver.get(BaseURL.URL_REGISTER);
        userAPI.createUser(user);
    }

    @Test
    @Description("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginViaBurgerPageTest(){
        BurgerMainPage burgerMainPage = new BurgerMainPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logotipClick();
        burgerMainPage.clickButtonPersonalAcc();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        Assert.assertTrue(loginPage.checkLabelInput());
    }

    @Test
    @Description("Вход через кнопку «Личный кабинет»")
    public void loginViaPersonaAccountTest(){
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        registerPage.personalAccoutnRPageClick();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        Assert.assertTrue(loginPage.checkLabelInput());
    }

    @Test
    @Description("Вход через кнопку Войти в форме восстановления пароля")
    public void loginViaFormRestorePageTest(){
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ResorePage resorePage = new ResorePage(driver);
        registerPage.click();
        loginPage.restorePasswordClick();
        resorePage.clickEnterButton().loginUser(user.getEmail(), user.getPassword());
        Assert.assertTrue(loginPage.checkLabelInput());
    }

    @Test
    @Description("Вход через кнопку Войти в форме регистрации")
    public void loginViaRegisterPageTest(){
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        registerPage.click();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        Assert.assertTrue(loginPage.checkLabelInput());
    }


    @After
    public void tearDown(){
        ValidatableResponse response = userAPI.loginUser(user);
        boolean success = response.extract().path("success");

        if (success) {
            accessToken = response.extract().path("accessToken");
            userAPI.deleteUser(userAPI.tockenConversion(accessToken)).statusCode(202);
        }
        driver.quit();
    }
}
