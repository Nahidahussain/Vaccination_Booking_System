package com.vaccinationbookingsystem.model;

import com.vaccinationbookingsystem.Enum.Gender;
import com.vaccinationbookingsystem.Enum.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    int age;

    //  @Column(unique = true,nullable = false)
    // now this column became unique, and user have to pass the email id ,se we set to nullable
    @Column(unique = true,nullable = false)
    String emailId;

    // i am telling mysql that gender is enum and we are storing as string
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "password",nullable = false)
    String password;

//    @Enumerated(value = EnumType.ORDINAL)
    UserRole userRole;
}
