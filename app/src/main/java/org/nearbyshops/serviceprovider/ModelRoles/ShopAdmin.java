package org.nearbyshops.serviceprovider.ModelRoles;

/**
 * Created by sumeet on 14/6/16.
 */
public class ShopAdmin {

    // Note : ShopAdmin has one to one relationship with Shop therefore the columns of ShopAdmin has been
    // merged inside the Shop table for preserving data and relationship consistency.
    // The entity remains distinct and not the same as Shop which in simple terms imply that although both
    // entity columns are merged in one table the entity remains distinct.

    // Table Name : Table does not exist for ShopAdmin because the columns are merged in Shop Table

    // Table Name
    public static final String TABLE_NAME = "SHOP_ADMIN";

    // column Names
    public static final String SHOP_ADMIN_ID = "SHOP_ADMIN_ID";
    public static final String NAME = "NAME";
    public static final String SHOP_ID = "SHOP_ID";

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    // to be Implemented
    public static final String DESIGNATION = "DESIGNATION";
//    public static final String ADMIN_ENABLED = "ADMIN_ENABLED";
//    public static final String ADMIN_WAITLISTED = "ADMIN_WAITLISTED";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";
    public static final String IS_WAITLISTED = "IS_WAITLISTED";

    public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
    public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";







    // instance Variables

    private int shopAdminID;
    private String name;
    private Integer shopID;

    private String username;
    private String password;

    private String about;
    private String profileImageURL;
    private String phoneNumber;


    private Boolean isEnabled;
    private Boolean isWaitlisted;

    private String designation;


    // getter and setters


    public int getShopAdminID() {
        return shopAdminID;
    }

    public void setShopAdminID(int shopAdminID) {
        this.shopAdminID = shopAdminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getWaitlisted() {
        return isWaitlisted;
    }

    public void setWaitlisted(Boolean waitlisted) {
        isWaitlisted = waitlisted;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
