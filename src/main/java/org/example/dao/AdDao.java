package org.example.dao;

import jakarta.servlet.http.HttpSession;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.entity.Advertisement;

import java.util.List;

public interface AdDao {
    void save(Advertisement advertisement, String userId);
    void update(Advertisement advertisement, String userId);
    void delete(String id);

    default List<Advertisement> findAllByCategory(String category) {
        return null;
    }

    default List<Advertisement> findAll() {
        return null;
    }

    default List<Advertisement> findByUser(String id) {
        return null;
    }

    AdDto get(String id);

    void changeActivity(String id, String userId);
}
