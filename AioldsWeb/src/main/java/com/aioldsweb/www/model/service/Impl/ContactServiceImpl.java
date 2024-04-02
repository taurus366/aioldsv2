package com.aioldsweb.www.model.service.Impl;

import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.model.repository.ContactRepository;
import com.aioldsweb.www.model.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactEntity> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public boolean delete(ContactEntity entity) {
        try {
            contactRepository.delete(entity);
            return true;
        } catch (Exception skip) {
            return false;
        }
    }

    @Override
    public Optional<ContactEntity> get(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public ContactEntity save(ContactEntity entity) {
        return contactRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            contactRepository.deleteById(id);
            return true;
        }catch (Exception skip) {
            return false;
        }
    }
}
