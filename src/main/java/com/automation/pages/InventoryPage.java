package com.automation.pages;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.drivers.DriverManager;

/** Page object for https://www.saucedemo.com/inventory.html. */
public class InventoryPage extends BaseBage {

    private static final Logger logger = LoggerFactory.getLogger(InventoryPage.class);
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Inventory page selectors
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By productSortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By inventoryItems = By.cssSelector("[data-test='inventory-item']");
    private final By inventoryItemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By inventoryItemDescriptions = By.cssSelector("[data-test='inventory-item-desc']");
    private final By inventoryItemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By shoppingCartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By shoppingCartBadge = By.cssSelector("[data-test='shopping-cart-badge']");

    // Header and menu selectors available from the inventory page
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By closeMenuButton = By.id("react-burger-cross-btn");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By resetAppStateLink = By.id("reset_sidebar_link");

    public boolean isLoaded() {
        boolean loaded = waitFor(pageTitle).getText().equals("Products");
        logger.info("Inventory page loaded: {}", loaded);
        return loaded;
    }

    public String getPageTitle() {
        return waitFor(pageTitle).getText();
    }

    public int getInventoryItemCount() {
        return DriverManager.getDriver().findElements(inventoryItems).size();
    }

    public List<String> getInventoryItemNames() {
        return DriverManager.getDriver().findElements(inventoryItemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getInventoryItemDescriptions() {
        return DriverManager.getDriver().findElements(inventoryItemDescriptions).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getInventoryItemPrices() {
        return DriverManager.getDriver().findElements(inventoryItemPrices).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void sortProductsBy(String visibleText) {
        logger.info("Sorting products by: {}", visibleText);
        new Select(waitFor(productSortDropdown)).selectByVisibleText(visibleText);
    }

    public void addProductToCart(String productName) {
        logger.info("Adding product to cart: {}", productName);
        click(addToCartButton(productName));
    }

    public void removeProductFromCart(String productName) {
        logger.info("Removing product from cart: {}", productName);
        click(removeFromCartButton(productName));
    }

    public void openProductDetails(String productName) {
        logger.info("Opening product details: {}", productName);
        click(By.xpath("//*[@data-test='inventory-item-name' and normalize-space()="
                + toXpathLiteral(productName) + "]"));
    }

    public void openCart() {
        logger.info("Opening shopping cart.");
        click(shoppingCartLink);
    }

    public int getCartItemCount() {
        List<WebElement> badges = DriverManager.getDriver().findElements(shoppingCartBadge);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    public void openMenu() {
        logger.info("Opening navigation menu.");
        click(menuButton);
    }

    public void closeMenu() {
        logger.info("Closing navigation menu.");
        click(closeMenuButton);
    }

    public void clickAllItems() {
        openMenu();
        click(allItemsLink);
    }

    public void clickAbout() {
        openMenu();
        click(aboutLink);
    }

    public void logout() {
        logger.info("Logging out from inventory page.");
        openMenu();
        click(logoutLink);
    }

    public void resetAppState() {
        logger.info("Resetting application state.");
        openMenu();
        click(resetAppStateLink);
    }

    // Convenience methods for the six products supplied by Sauce Demo.
    public void addSauceLabsBackpackToCart() { addProductToCart("Sauce Labs Backpack"); }
    public void addSauceLabsBikeLightToCart() { addProductToCart("Sauce Labs Bike Light"); }
    public void addSauceLabsBoltTShirtToCart() { addProductToCart("Sauce Labs Bolt T-Shirt"); }
    public void addSauceLabsFleeceJacketToCart() { addProductToCart("Sauce Labs Fleece Jacket"); }
    public void addSauceLabsOnesieToCart() { addProductToCart("Sauce Labs Onesie"); }
    public void addTestAllTheThingsTShirtRedToCart() { addProductToCart("Test.allTheThings() T-Shirt (Red)"); }

    private WebElement waitFor(By locator) {
        return new WebDriverWait(DriverManager.getDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private By addToCartButton(String productName) {
        return By.id("add-to-cart-" + productName.toLowerCase(Locale.ROOT)
                .replace(" ", "-").replace(".", "").replace("(", "").replace(")", ""));
    }

    private By removeFromCartButton(String productName) {
        return By.id("remove-" + productName.toLowerCase(Locale.ROOT)
                .replace(" ", "-").replace(".", "").replace("(", "").replace(")", ""));
    }

    private String toXpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
