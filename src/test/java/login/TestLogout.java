package login;


import api.BaseURL;
import api.UserAPI;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pagestellarburgers.BurgerMainPage;
import pagestellarburgers.LoginPage;
import pagestellarburgers.PersonalAccountPage;
import pagestellarburgers.RegisterPage;
import registration.BaseTest;
import user.RandomUser;
import user.User;

public class TestLogout extends BaseTest {
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
    @Description("Выход по кнопке «Выйти» в личном кабинете")
    public void logoutViaPersonaAccountTest(){
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        BurgerMainPage burgerMainPage = new BurgerMainPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        registerPage.click();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        loginPage.logotipClick();
       burgerMainPage.clickButtonPersonalAcc();
       personalAccountPage.headerClick();
       personalAccountPage.logoutClick();
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
