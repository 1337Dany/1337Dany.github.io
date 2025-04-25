package org.example.s30050tpo06.controllers;

import org.example.s30050tpo06.entities.PersonDTO;
import org.example.s30050tpo06.services.TranslationService;
import org.example.s30050tpo06.entities.Person;
import org.example.s30050tpo06.services.FakeDataGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DataGeneratorController {
    private final FakeDataGenerator fakeDataGenerator;
    private final TranslationService translationService;

    public DataGeneratorController(FakeDataGenerator fakeDataGenerator, TranslationService translationService) {
        this.fakeDataGenerator = fakeDataGenerator;
        this.translationService = translationService;
    }

    @RequestMapping("/generator")
    public String generator(
            @RequestParam(defaultValue = "0") int count,
            @RequestParam(defaultValue = "en") String language,
            @RequestParam(defaultValue = "true") boolean includeInitials,
            @RequestParam(defaultValue = "false") boolean includeAddress,
            @RequestParam(defaultValue = "false") boolean includeUniversity,
            @RequestParam(defaultValue = "false") boolean includeCountry,
            @RequestParam(defaultValue = "false") boolean includeEmail,
            @RequestParam(defaultValue = "false") boolean includePhoneNumber,
            @RequestParam(defaultValue = "false") boolean includeGender,
            @RequestParam(defaultValue = "false") boolean includeJob,
            @RequestParam(defaultValue = "false") boolean includeHobbies,
            Model model) {

        List<Person> fakeData = fakeDataGenerator.generateFakeData(
                count, language, includeInitials, includeAddress, includeUniversity,
                includeCountry, includeEmail, includePhoneNumber, includeGender,
                includeJob, includeHobbies);

        List<String> dataListDTO = new ArrayList<>();
        List<String> columnHeaders = new ArrayList<>();

        if (includeInitials) {
            columnHeaders.add(translationService.getTranslation(language, "first.name"));
            columnHeaders.add(translationService.getTranslation(language, "last.name"));
            columnHeaders.add(translationService.getTranslation(language, "date.of.birth"));
        }
        if (includeAddress) {
            columnHeaders.add(translationService.getTranslation(language, "address"));
        }
        if (includeUniversity) {
            columnHeaders.add(translationService.getTranslation(language, "university"));
        }
        if (includeCountry) {
            columnHeaders.add(translationService.getTranslation(language, "country"));
        }
        if (includeEmail) {
            columnHeaders.add(translationService.getTranslation(language, "email"));
        }
        if (includePhoneNumber) {
            columnHeaders.add(translationService.getTranslation(language, "phone.number"));
        }
        if (includeGender) {
            columnHeaders.add(translationService.getTranslation(language, "gender"));
        }
        if (includeJob) {
            columnHeaders.add(translationService.getTranslation(language, "job"));
        }
        if (includeHobbies) {
            columnHeaders.add(translationService.getTranslation(language, "hobbies"));
        }

        for (Person person : fakeData) {
            dataListDTO.add(person.firstName());
            dataListDTO.add(person.lastName());
            dataListDTO.add(person.dateOfBirth().toString());

            if (includeAddress) {
                dataListDTO.add(person.address());
            }
            if (includeUniversity) {
                dataListDTO.add(person.university());
            }
            if (includeCountry) {
                dataListDTO.add(person.country());
            }
            if (includeEmail) {
                dataListDTO.add(person.email());
            }
            if (includePhoneNumber) {
                dataListDTO.add(person.phoneNumber());
            }
            if (includeGender) {
                dataListDTO.add(person.gender());
            }
            if (includeJob) {
                dataListDTO.add(person.job());
            }
            if (includeHobbies) {
                dataListDTO.add(person.hobbies());
            }
        }

        model.addAttribute("data",new PersonDTO(dataListDTO));
        model.addAttribute("count", count);
        model.addAttribute("columnsCount", columnHeaders.size());
        model.addAttribute("columnHeaders", columnHeaders);
        model.addAttribute("includeInitials", includeInitials);
        model.addAttribute("includeAddress", includeAddress);
        model.addAttribute("includeUniversity", includeUniversity);
        model.addAttribute("includeCountry", includeCountry);
        model.addAttribute("includeEmail", includeEmail);
        model.addAttribute("includePhoneNumber", includePhoneNumber);
        model.addAttribute("includeGender", includeGender);
        model.addAttribute("includeJob", includeJob);
        model.addAttribute("includeHobbies", includeHobbies);

        return "generator";
    }
}