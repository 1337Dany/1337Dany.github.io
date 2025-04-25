package org.example.s30050tpo06.services;

import net.datafaker.Faker;
import org.example.s30050tpo06.entities.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FakeDataGenerator {
    public List<Person> generateFakeData(
            int count,
            String language,
            boolean includeInitials,
            boolean includeAddress,
            boolean includeUniversity,
            boolean includeCountry,
            boolean includeEmail,
            boolean includePhoneNumber,
            boolean includeGender,
            boolean includeJob,
            boolean includeHobbies
    ) {
        List<Person> personList = new ArrayList<>();
        Faker faker = new Faker(new Locale(language));

        for (int i = 0; i < count; i++) {
            Person person = new Person(
                    includeInitials ? faker.name().firstName() : null,
                    includeInitials ? faker.name().lastName() : null,
                    includeInitials ? faker.date().birthday().toLocalDateTime().toLocalDate() : null,
                    includeAddress ? faker.address().fullAddress() : null,
                    includeUniversity ? faker.university().name() : null,
                    includeCountry ? faker.country().name() : null,
                    includeEmail ? faker.internet().emailAddress() : null,
                    includePhoneNumber ? faker.phoneNumber().phoneNumber() : null,
                    includeGender ? faker.gender().binaryTypes() : null,
                    includeJob ? faker.job().title() : null,
                    includeHobbies ? faker.hobby().activity() : null
            );
            personList.add(person);
        }
        return personList;
    }
}
