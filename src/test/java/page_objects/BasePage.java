package page_objects;

import org.openqa.selenium.By;

public class BasePage {

    //region General Elements
    private final By header = By.xpath("//header");
    private final By body = By.xpath("//body");
    private final By footer = By.xpath("//footer");
    private final By logo = By.xpath("//header//a[@role='logo']");
    //endregion

    //region pop-up
    private final By kitchenalityPopupClose = By.xpath("//div[@class='class_close_sg']");
    private final By kitchenalityPopupContent = By.xpath("//div[contains(@class, 'left-column')]/h1");
    private final By cookieNotification = By.xpath("//a[@class='acceptStickyBlock']");
    private final By popUpCloseButton1 = By.xpath("//div[contains(@class,'espace-modal__overlay')]//div[@class='espace-modal__close-button']");
    private final By popUpCloseButton2 = By.xpath("//div[contains(@class,'espace-modal__overlay')]//div[@class='espace-modal__close-button']");
    private final By dialogueChat = By.xpath("//div[@id='talkNowActiveContent']//div[@id='lekaneBannerMinimizer']");
    private final By minimizedDialogueChat = By.xpath("//div[@id='talkNowActiveContent']");
    private final By closeChatWidget = By.xpath("//div[contains(@class,'widget-button-close')]");
    //endregion

    //region Menu desktop
    private final By BADAButton = By.xpath("//a[contains(@class, 'c-yellow-header__link-book-appointment')]");
    private final By findStoreButton = By.xpath("//div[@class='c-yellow-header__action-menu']//span[contains(@class, 'c-yellow-header__icon-find-store')]/parent::a");
    private final By searchIcon = By.xpath("//a[@aria-label='Search']/span[contains(@class,'c-yellow-header__icon-search')]");
    private final By searchPopup = By.xpath("//div[@class='c-yellow-header__search-results-container']");
    private final By searchField = By.xpath("//input[@class='c-yellow-header__search-input']");
    //endregion

    //region Menu Mobile
    private final By hamburgerMenu = By.xpath("//a[@class='c-yellow-header__link--hamburger']");
    //endregion

    //region Homepage
    private final By video = By.xpath("//div[@id='content-primary']//video");
    private final By slideShowImages = By.xpath("//div[@class='c-yellow-slideshow__image']");
    //endregion

    //region Login Page
    private final By usernameField = By.xpath("//input[@id='username']");
    private final By passwordField = By.xpath("//input[@id='password']");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("//div[@class='system-message error-message']//li");
    private final By sidebarStore = By.xpath("//body[contains(@class,'page-type__project-guide-page')]//div[@id='sidebar-store']");
    private final By unstableTeaser = By.xpath("//div[@class='column sidebar']/parent::div");
    //endregion

    //region Find Store page
    private final By storeName = By.xpath("//body[contains(@class,'page-type__find-page')]//ul[@id='stores-list']/li[contains(@class,'store')][1]//h3/a");
    private final By openingHours = By.xpath("//p[@class='hours']");
    private final By moreInfoButton = By.xpath("//body[contains(@class,'page-type__find-page')]//ul[@id='stores-list']/li[contains(@class,'store')][1]//li[@class='more-info']/a");
    private final By storeDetailHeading = By.xpath("//body[contains(@class,'page-type__studio-start-page')]//h1");
    private final By getDirectionLink = By.xpath("//body[contains(@class,'page-type__find-page')]//ul[@id='stores-list']/li[contains(@class,'store')][1]//a[@class='get-directions']");
    private final By storeBookingButton = By.xpath("//body[contains(@class,'page-type__find-page')]//ul[@id='stores-list']/li[contains(@class,'store')][1]//li[@class='select-store']/a");
    //endregion

    //region New Store page
    private final By searchBox = By.xpath("//input[@type='search']");
    private final By distances = By.xpath("//div[@class='tabs__tab-content tab-container'][@style='']//div[@class='flex']/div");
    private final By storeNames = By.xpath("//div[@class='tabs__tab-content tab-container'][@style='']//div[@class='flex']/h4");
    private final By postcodes = By.xpath("//div[@class='tabs__tab-content tab-container'][@style='']//div[@class='card']//div[@class='address']");
    private final By allStoreTab = By.xpath("//div[@class='tabs__bar']/div[2]");
    private final By closestStoreTab = By.xpath("//div[@class='tabs__bar']/div[1]");
    private final By viewMoreButton = By.xpath("//div[@class='view-more']//span");
    private final By viewStoreInfoButton = By.xpath("//div[@class='tabs__tab-content tab-container'][@style='']//div[@class='flex choise-area']/a[1]");
    private final By directionLink = By.xpath("//div[@class='tabs__tab-content tab-container'][@style='']//div[@class='flex choise-area']/a[2]");
    private final By storeHeading = By.tagName("h1");
    private final By findStoreDropdown = By.xpath("//div[@class='drop-down__content']");
    private final By headerStoreName = By.xpath("//button[@class='drop-down__button']/strong");
    private final By headerStoreNameMobile = By.xpath("//button[@class='drop-down__button mobile']/strong");
    private final By headerStores = By.xpath("//ul[@class='drop-down__content-list']/li/a");
    private final By headerViewMore = By.xpath("//div[@class='drop-down__footer-link']/a");
    private final By storePageCarousel = By.xpath("//div[contains(@class,'topcarousel')]");
    private final By contactCard = By.xpath("//div[@class='contact-card']");
    private final By storeDescription = By.xpath("//div[@class='read-more-content']/parent::div/parent::div");
    private final By descriptionSeeMore = By.xpath("//div[@class='read-more-button']/span[not(@style='display: none;')]");
    private final By campaignBlock = By.xpath("//div[contains(@class,'campaign-block')]/ancestor::div[@class='section-wrapper']");
    private final By campaignExpand = By.xpath("//div[contains(@class,'campaign-block')]//a[@class='mdc-button white']");
    private final By storeInfoBlock = By.xpath("//div[@class='tabs']/ancestor::div[@class='section-wrapper']");
    private final By storeInfoTabs = By.xpath("//div[@class='tabs__bar']//div[contains(@class,'tabs__tab')]");
    private final By displaySales = By.xpath("//div[@class='price']/parent::a");
    private final By openMapLink = By.xpath("//div[contains(@class,'storetab')]//a[@class='maplink']");
    private final By seeAllDisplaySale = By.xpath("//div[contains(@class,'linkarea')]/a");

    //endregion

    //region BADA1
    private final By changeStoreLink = By.xpath("//div[@class='selected-store-box']//a[@class='open-in-lightbox']");
    private final By BADALocationField = By.xpath("//form[contains(@class,'form-general')]//input[@type='search']");
    private final By BADASearchButton = By.xpath("//form[contains(@class,'form-general')]//button");
    private final By mapDialog = By.xpath("//body[contains(@class,'page-type__schedule-consultation-with-booking-page')]//div[@role='dialog']");
    private final By mapDialogSearchField = By.xpath("//div[@class='c-input-with-icon-button']//input[@type='search']");
    private final By mapDialogSearchButton = By.xpath("//div[@class='c-input-with-icon-button']//span[@data-icon='search']");
    private final By selectStoreButtons = By.xpath("//body[contains(@class,'page-type__schedule-consultation-with-booking-page')]//div[@class='select-store']/a");
    private final By BADA1storeNames = By.xpath("//body[contains(@class,'page-type__schedule-consultation-with-booking-page')]//ul[@id='stores-list']//span[@class='name']");
    private final By selectedStoreName = By.xpath("//body[contains(@class,'page-type__schedule-consultation-with-booking-page')]//div[@id='store-details']//a");
    //endregion

    //region BADA2 Page
    private final By selectStore = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//input[@name='store']/parent::label");
    private final By selectedStore = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//input[@name='store']/parent::label//h4");
    private final By allStoreButton = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@id='tab-button-all']");
    private final By closestStoreButton = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@id='tab-button-near']");
    private final By changeLocationLink = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//a[@data-cy='ChangeLocationLink']");
    private final By postcodeField = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//input[@type='number']");
    private final By searchLocationButton = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@kind='primary']");
    private final By searchLocationPopup = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@kind='primary']/ancestor::div[not(@class)][not(@id)]");
    private final By storeMeeting = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//div[@data-cy='MeetingTypeItem-store']/label");
    private final By homeMeeting = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//div[@data-cy='MeetingTypeItem-home']/label");
    private final By videoMeeting = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//div[@data-cy='MeetingTypeItem-video']/label");
    private final By availableSlot = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//time[@type='available']/parent::div");
    private final By calendarNextButton = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@kind='primary'][not(@disabled)]");
    private final By customerForm = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//form[@data-cy='CustomerForm']/parent::div");
    private final By submitButton = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//button[@type='submit']");
    private final By requiredSign = By.xpath("//body[contains(@class,'page-type__book-design-appointment-page')]//form[@data-cy='CustomerForm']//input/following-sibling::span[2]");
    //endregion

    //region OPC Product Page
    private final By categoryHeading = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//h1");

    //endregion

    //region 10 minute Mail
    private final By tempEmailAddress = By.xpath("//input[@id='mail']");
    private final By refreshButton = By.xpath("//button[@class='refresh-countdown']");
    private final By emailEntry = By.xpath("//a[@class='viewLink'][@data-mail-id]");
    private final By emailContent = By.xpath("//div[@class='inbox-data-content']");
    private final By topContent = By.xpath("//div[@class='section-top-qr']");
    private final By bottomContent = By.xpath("//div[@class='section-populer-article']");
    //endregion

    //region Search Page
    private final By searchMask = By.xpath("//div[@class='c-yellow-header__mask c-yellow-header__mask--visible']");
    private final By searchFilter = By.xpath("//body[contains(@class,'page-type__search-page')]//div[@id='filters']");
    private final By searchResult = By.xpath("//body[contains(@class,'page-type__search-page')]//div[@id='sr']");
    private final By searchArticles = By.xpath("//body[contains(@class,'page-type__search-page')]//div[@class='article']");
    //endregion

    //region Order Catalogue Page
    private final By viewCatalogButton = By.xpath("//body[contains(@class,'page-type__catalogues-request-page')]//form//div[@class='items']/div[1]//a[@class='brochure__link']");
    private final By firstCatalogue = By.xpath("//body[contains(@class,'page-type__order-catalogue-page')]//form/div[@data-size=4][1]//a[@class='catalogue-link']");
    private final By firstOrderCatalogueBtn = By.xpath("//body[contains(@class,'page-type__order-catalogue-page')]//form/div[@data-size='4'][1]//button");
    private final By orderCatalogueForm = By.xpath("//body[contains(@class,'page-type__order-catalogue-page')]//div[@id='order-catalogue']");
    private final By storeTeaser = By.xpath("//body[contains(@class,'page-type__order-catalogue-page')]//div[@id='closest-store']/ancestor::div[@class='column']");
    //endregion

    //region OPC
    private final By categoryCards = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//a[@data-cy='CategoryCard']");
    private final By categoryNames = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//a[@data-cy='CategoryCard']//h2");
    private final By openFilterMobile = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//ul[@data-cy='FilterList']/preceding-sibling::button");
    private final By brandFilter = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//li[@data-cy='Dropdown']/ancestor::ul[@data-cy='FilterList']/div[1]");
    private final By brandSelect = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//ul[@data-cy='FilterList']/div[1]/li[@data-cy='Dropdown']//ul/li[2]");
    private final By colorFilter = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//li[@data-cy='Dropdown']/ancestor::ul[@data-cy='FilterList']/div[2]");
    private final By colorSelect = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//ul[@data-cy='FilterList']/div[2]/li[@data-cy='Dropdown']//ul/li[2]");
    private final By typeFilter = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//li[@data-cy='Dropdown']/ancestor::ul[@data-cy='FilterList']/div[3]");
    private final By typeSelect = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//ul[@data-cy='FilterList']/div[3]/li[@data-cy='Dropdown']//ul/li[2]");
    private final By sortOption = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//ul[@data-cy='SortBox']");
    private final By sortSelect = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//ul[@data-cy='SortBox']//li[@data-cy='Dropdown']//ul/li[2]");
    private final By filter = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//ul[@data-cy='FilterList']");
    private final By productCards = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//a[@data-cy='ProductCard']");
    private final By productCardNames = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//a[@data-cy='ProductCard']//h2");
    private final By productHeading = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//h1");
    private final By productImage = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//div[@data-cy='data-CurrentSlide']");
    private final By productDescription = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//h1/parent::div");
    private final By wishListSummary = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@id='opc']//div[@data-cy='WishlistSummary']/span/div");
    private final By wishListEmailField = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//input[@type='email']");
    private final By wishListSendButton = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//button[@data-cy='ConfirmButton']");
    private final By wishListButton = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//button[@data-cy='WishlistButton']");
    private final By wishListOverlay = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@data-cy='Modal']/parent::div/parent::div");
    private final By wishListPopUpMobile = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@data-cy='Modal']");
    private final By wishListCloseBtn = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@data-cy='Modal']//span[@data-cy='Close']");
    private final By compareButton = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//a[@kind='inverted']");
    private final By removeButton = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//span[@role='button']");
    private final By addProductButton = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//button[@kind='primary']");
    private final By addToCompare = By.xpath("//body[contains(@class,'page-type__opc-group-page')]//div[@data-cy='Modal']/div/div/div[3]/div[1]");
    //endregion

    //region Kitchenality page
    private final By quizItems = By.xpath("//body[contains(@class,'page-type__embed-page')]//div[@data-testid='quiz__grid__item']");
    private final By quizNextButton = By.xpath("//body[contains(@class,'page-type__embed-page')]//button[@data-testid='quiz__next__button']");
    private final By numberOfItems = By.xpath("//body[contains(@class,'page-type__embed-page')]//button[@data-testid='quiz__next__button']/preceding-sibling::p");
    private final By emailBox = By.xpath("//body[contains(@class,'page-type__embed-page')]//input[@type='email']");
    private final By saveKitchenalityButton = By.xpath("//body[contains(@class,'page-type__embed-page')]//button[@data-testid='save__form__button']");
    //endregion

    //region Webshop
    private final By stockStatusText = By.xpath("//body[contains(@class,'page-type__ecom-cart-page')]//td[@class='cart-stock']//p");
    //endregion

    public By getHeader() {
        return header;
    }

    public By getBody() {
        return body;
    }

    public By getFooter() {
        return footer;
    }

    public By getUsernameField() {
        return usernameField;
    }

    public By getPasswordField() {
        return passwordField;
    }

    public By getLoginButton() {
        return loginButton;
    }

    public By getAllStoreButton() {
        return allStoreButton;
    }

    public By getChangeLocationLink() {
        return changeLocationLink;
    }

    public By getPostcodeField() {
        return postcodeField;
    }

    public By getSearchLocationButton() {
        return searchLocationButton;
    }

    public By getStoreMeeting() {
        return storeMeeting;
    }

    public By getHomeMeeting() {
        return homeMeeting;
    }

    public By getAvailableSlot() {
        return availableSlot;
    }

    public By getCalendarNextButton() {
        return calendarNextButton;
    }

    public By getCustomerForm() {
        return customerForm;
    }

    public By getSubmitButton() {
        return submitButton;
    }

    public By getRequiredSign() {
        return requiredSign;
    }

    public By getClosestStoreButton() {
        return closestStoreButton;
    }

    public By getCategoryHeading() {
        return categoryHeading;
    }

    public By getLogo() {
        return logo;
    }

    public By getEmailEntry() {
        return emailEntry;
    }

    public By getKitchenalityPopupClose() {
        return kitchenalityPopupClose;
    }

    public By getPopUpCloseButton1() {
        return popUpCloseButton1;
    }

    public By getVideo() {
        return video;
    }

    public By getSearchMask() {
        return searchMask;
    }

    public By getSearchFilter() {
        return searchFilter;
    }

    public By getSearchResult() {
        return searchResult;
    }

    public By getSearchIcon() {
        return searchIcon;
    }

    public By getSearchPopup() {
        return searchPopup;
    }

    public By getSearchField() {
        return searchField;
    }

    public By getBADAButton() {
        return BADAButton;
    }

    public By getFindStoreButton() {
        return findStoreButton;
    }

    public By getFirstCatalogue() {
        return firstCatalogue;
    }

    public By getFirstOrderCatalogueBtn() {
        return firstOrderCatalogueBtn;
    }

    public By getOrderCatalogueForm() {
        return orderCatalogueForm;
    }

    public By getProductHeading() {
        return productHeading;
    }

    public By getProductImage() {
        return productImage;
    }

    public By getProductDescription() {
        return productDescription;
    }

    public By getQuizNextButton() {
        return quizNextButton;
    }

    public By getEmailBox() {
        return emailBox;
    }

    public By getSaveKitchenalityButton() {
        return saveKitchenalityButton;
    }

    public By getQuizItems() {
        return quizItems;
    }

    public By getPopUpCloseButton2() {
        return popUpCloseButton2;
    }

    public By getSearchArticles() {
        return searchArticles;
    }

    public By getTopContent() {
        return topContent;
    }

    public By getBottomContent() {
        return bottomContent;
    }

    public By getTempEmailAddress() {
        return tempEmailAddress;
    }

    public By getWishListSummary() {
        return wishListSummary;
    }

    public By getWishListEmailField() {
        return wishListEmailField;
    }

    public By getWishListSendButton() {
        return wishListSendButton;
    }

    public By getCompareButton() {
        return compareButton;
    }

    public By getRemoveButton() {
        return removeButton;
    }

    public By getAddProductButton() {
        return addProductButton;
    }

    public By getAddToCompare() {
        return addToCompare;
    }

    public By getFilter() {
        return filter;
    }

    public By getProductCards() {
        return productCards;
    }

    public By getWishListButton() {
        return wishListButton;
    }

    public By getWishListOverlay() {
        return wishListOverlay;
    }

    public By getWishListCloseBtn() {
        return wishListCloseBtn;
    }

    public By getCategoryCards() {
        return categoryCards;
    }

    public By getBrandFilter() {
        return brandFilter;
    }

    public By getBrandSelect() {
        return brandSelect;
    }

    public By getColorFilter() {
        return colorFilter;
    }

    public By getColorSelect() {
        return colorSelect;
    }

    public By getTypeFilter() {
        return typeFilter;
    }

    public By getTypeSelect() {
        return typeSelect;
    }

    public By getSortOption() {
        return sortOption;
    }

    public By getSortSelect() {
        return sortSelect;
    }

    public By getViewCatalogButton() {
        return viewCatalogButton;
    }

    public By getSearchLocationPopup() {
        return searchLocationPopup;
    }

    public By getHamburgerMenu() {
        return hamburgerMenu;
    }

    public By getSidebarStore() {
        return sidebarStore;
    }

    public By getCookieNotification() {
        return cookieNotification;
    }

    public By getNumberOfItems() {
        return numberOfItems;
    }

    public By getOpenFilterMobile() {
        return openFilterMobile;
    }

    public By getWishListPopUpMobile() {
        return wishListPopUpMobile;
    }

    public By getUnstableTeaser() {
        return unstableTeaser;
    }

    public By getKitchenalityPopupContent() {
        return kitchenalityPopupContent;
    }

    public By getStoreTeaser() {
        return storeTeaser;
    }


    public By getEmailContent() {
        return emailContent;
    }

    public By getCategoryNames() {
        return categoryNames;
    }

    public By getProductCardNames() {
        return productCardNames;
    }

    public By getSlideShowImages() {
        return slideShowImages;
    }

    public By getChangeStoreLink() {
        return changeStoreLink;
    }

    public By getBADALocationField() {
        return BADALocationField;
    }

    public By getBADASearchButton() {
        return BADASearchButton;
    }

    public By getMapDialog() {
        return mapDialog;
    }

    public By getSelectStoreButtons() {
        return selectStoreButtons;
    }

    public By getBADA1storeNames() {
        return BADA1storeNames;
    }

    public By getSelectedStoreName() {
        return selectedStoreName;
    }

    public By getStoreName() {
        return storeName;
    }

    public By getMoreInfoButton() {
        return moreInfoButton;
    }

    public By getStoreDetailHeading() {
        return storeDetailHeading;
    }

    public By getGetDirectionLink() {
        return getDirectionLink;
    }

    public By getStoreBookingButton() {
        return storeBookingButton;
    }

    public By getErrorMessage() {
        return errorMessage;
    }

    public By getMapDialogSearchField() {
        return mapDialogSearchField;
    }

    public By getMapDialogSearchButton() {
        return mapDialogSearchButton;
    }

    public By getStockStatusText() {
        return stockStatusText;
    }

    public By getOpeningHours() {
        return openingHours;
    }

    public By getSelectedStore() {
        return selectedStore;
    }

    public By getSelectStore() {
        return selectStore;
    }

    public By getDialogueChat() {
        return dialogueChat;
    }

    public By getVideoMeeting() {
        return videoMeeting;
    }

    public By getMinimizedDialogueChat() {
        return minimizedDialogueChat;
    }

    public By getCloseChatWidget() {
        return closeChatWidget;
    }

    public By getRefreshButton() {
        return refreshButton;
    }

    public By getSearchBox() {
        return searchBox;
    }

    public By getDistances() {
        return distances;
    }

    public By getStoreNames() {
        return storeNames;
    }

    public By getPostcodes() {
        return postcodes;
    }

    public By getAllStoreTab() {
        return allStoreTab;
    }

    public By getClosestStoreTab() {
        return closestStoreTab;
    }

    public By getViewMoreButton() {
        return viewMoreButton;
    }

    public By getViewStoreInfoButton() {
        return viewStoreInfoButton;
    }

    public By getDirectionLink() {
        return directionLink;
    }

    public By getStoreHeading() {
        return storeHeading;
    }

    public By getFindStoreDropdown() {
        return findStoreDropdown;
    }

    public By getHeaderStoreName() {
        return headerStoreName;
    }

    public By getHeaderStoreNameMobile() {
        return headerStoreNameMobile;
    }

    public By getHeaderStores() {
        return headerStores;
    }

    public By getHeaderViewMore() {
        return headerViewMore;
    }

    public By getStorePageCarousel() {
        return storePageCarousel;
    }

    public By getContactCard() {
        return contactCard;
    }

    public By getStoreDescription() {
        return storeDescription;
    }

    public By getDescriptionSeeMore() {
        return descriptionSeeMore;
    }

    public By getCampaignBlock() {
        return campaignBlock;
    }

    public By getCampaignExpand() {
        return campaignExpand;
    }

    public By getStoreInfoBlock() {
        return storeInfoBlock;
    }

    public By getStoreInfoTabs() {
        return storeInfoTabs;
    }

    public By getOpenMapLink() {
        return openMapLink;
    }

    public By getSeeAllDisplaySale() {
        return seeAllDisplaySale;
    }

    public By getDisplaySales() {
        return displaySales;
    }
}
