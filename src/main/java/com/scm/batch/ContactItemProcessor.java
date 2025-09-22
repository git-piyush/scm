package com.scm.batch;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.repsitories.UserRepo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class ContactItemProcessor implements ItemProcessor<Contact, Contact> {

    @Autowired
    private UserRepo userRepo;

    @Override
    public Contact process(Contact contact) {
        // Generate UUID for id
        contact.setId(UUID.randomUUID().toString());

        // Ensure name and email are trimmed/cleaned
        contact.setName(contact.getName().trim());
        contact.setEmail(contact.getEmail().trim());
        contact.setPhoneNumber(contact.getPhoneNumber());
        contact.setAddress(contact.getAddress());
        contact.setDescription(contact.getDescription());
        contact.setWebsiteLink(contact.getWebsiteLink());
        contact.setLinkedInLink(contact.getLinkedInLink());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userRepo.findByEmail(username).get();
        contact.setUser(user);
        return contact;
    }
}
