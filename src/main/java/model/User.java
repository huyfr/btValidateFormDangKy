package model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Entity
@Table(name = "user", schema = "exercise")
public class User implements Validator {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                age == user.age &&
                phoneNumber.equals(user.phoneNumber) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        //firstName
        String firstName = user.getFirstName();
        //empty
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        //length
        if (firstName.length() < 5 || firstName.length() > 45) {
            errors.rejectValue("firstName", "firstName.length");
        }

        //lastName
        String lastName = user.getLastName();
        //empty
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");
        //length
        if (lastName.length() < 5 || lastName.length() > 45) {
            errors.rejectValue("lastName", "lastName.length");
        }

        //phoneNumber
        String phoneNumber = user.getPhoneNumber();
        //empty
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "phoneNumber.empty");
        //length
        if (phoneNumber.length() > 11 || phoneNumber.length() < 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.length");
        }
        //startsWith
        if (!phoneNumber.startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneNumber.startsWith");
        }
        //matches
        if (!phoneNumber.matches("(^$|[0-9]*$)")){
            errors.rejectValue("phoneNumber", "phoneNumber.matches");
        }

        int age = user.getAge();
        //empty
        ValidationUtils.rejectIfEmpty(errors,"age", "age.empty");
        // >18
        if (age < 18) {
            errors.rejectValue("age", "age.lessThan18");
        }

        String email = user.getEmail();
        //empty
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        //matches
        if (email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
            errors.rejectValue("email", "email.matches");
        }
    }
}