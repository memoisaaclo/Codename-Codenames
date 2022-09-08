package coms309.people;


import java.util.ArrayList;

/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    private String authorization;

    private ArrayList<Person> friends = new ArrayList<Person>();

    public Person() {

    }

    public Person(String firstName, String lastName, String address, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
    }
    public Person(String firstName, String lastName, String address, String telephone, String authorization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.authorization = authorization;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAuthorization() { return this.authorization; }

    public void setAuthorization(String authorization) { this.authorization = authorization; }

    public ArrayList<Person> getFriends() { return friends; }

    public String printPrettyFriends() {
        String ret = "";
        for (Person p :
             friends) {
            ret += p.getFirstName() + " " + p.getLastName();
        }
        return ret;
    }

    public void addFriend(Person friend) { this.friends.add(friend); }

    public void setFriends(ArrayList<Person> friends) { this.friends = friends; }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + address + " "
                + telephone + " "
                + authorization + " "
                + printPrettyFriends();
    }
}
