package com.aioldsweb.www.model.service;

import com.aioldsweb.www.model.entity.ContactEntity;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<ContactEntity> getAll();
    boolean delete(ContactEntity entity);
    Optional<ContactEntity> get(Long id);
    ContactEntity save(ContactEntity entity);
    boolean deleteById(Long id);
}
