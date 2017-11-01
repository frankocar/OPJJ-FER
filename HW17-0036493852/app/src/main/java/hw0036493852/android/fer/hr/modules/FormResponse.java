package hw0036493852.android.fer.hr.modules;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Serializable class to fill out a form using
 * data in a specified format form the web.
 */

public class FormResponse implements Serializable {

    /**
     * URL pointing to the avatar image location
     */
    @SerializedName("avatar_location")
    String avatarUrl;
    /**
     * First name
     */
    @SerializedName("first_name")
    String name;
    /**
     * Last name
     */
    @SerializedName("last_name")
    String lastName;
    /**
     * Phone number
     */
    @SerializedName("phone_no")
    String phoneNumber;
    /**
     * Email address
     */
    @SerializedName("email_sknf")
    String email;
    /**
     * Spouse name
     */
    @SerializedName("spouse")
    String spouse;
    /**
     * Age
     */
    @SerializedName("age")
    String age;

    /**
     * Avatar URL geter
     *
     * @return URL pointing to the user avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Avatar URL setter
     *
     * @param avatarUrl new URL
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * First name getter
     *
     * @return Current name
     */
    public String getName() {
        return name;
    }

    /**
     * First name setter
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Last name getter
     *
     * @return Current last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Last name setter
     *
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Phone number getter
     *
     * @return current phone numbers
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Phone number setter
     *
     * @param phoneNumber new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Email address getter
     *
     * @return current email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email address setter
     *
     * @param email new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Spouse name getter
     *
     * @return current spouse name
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Spouse name setter
     *
     * @param spouse New spouse name
     */
    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    /**
     * Age getter
     *
     * @return current age
     */
    public String getAge() {
        return age;
    }

    /**
     * Age setter
     *
     * @param age new age
     */
    public void setAge(String age) {
        this.age = age;
    }
}
