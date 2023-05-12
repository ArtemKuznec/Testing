package ui;

import configurator.Configurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TrianglePageTests {

    @BeforeClass
    public void configuratorSetup() {
        Configurator.setup();
    }

    @Test
    public void pageElementsTest() {
        TrianglePage page = new TrianglePage();
        if (page.isShowAnswersButtonExist()) {
            page.getShowAnswersButton().click();
            Assert.assertTrue(page.getHideAnswersButton().isDisplayed());
            Assert.assertTrue(page.getAnswersLink().isDisplayed());
        }
    }

    @AfterClass
    public static void driverQuit() {
        Configurator.getDriver().quit();
    }
}
