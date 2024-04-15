package transitions;

import api.BaseURL;


import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pagestellarburgers.BurgerMainPage;
import registration.BaseTest;


@RunWith(Parameterized.class)
public class TestTransitionsSection extends BaseTest {
    private final String section;
    public TestTransitionsSection(String section) {
        this.section = section;
    }

    @Before
    public void createTestData() {
        driver.get(BaseURL.URL_BURGER);
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Булки"},
                {"Соусы"},
                {"Начинки"},
        };
    }

    @Test
    @Description("Переходы по секциям в Конструторе")
    public void transitionsSectionsTest() {
        BurgerMainPage burgerMainPage = new BurgerMainPage(driver);
        burgerMainPage.sectionClick(section);
        String getTextSection = burgerMainPage.displayedSection(section);
        Assert.assertEquals(section, getTextSection);
    }
    @After
    public void tearDown(){
        driver.quit();
    }


}
