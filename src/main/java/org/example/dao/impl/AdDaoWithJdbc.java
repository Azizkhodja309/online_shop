package org.example.dao.impl;

import jakarta.servlet.http.HttpSession;
import org.example.dao.AdDao;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.entity.Advertisement;
import org.example.model.enums.Currency;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AdDaoWithJdbc implements AdDao {
    private final JdbcTemplate jdbcTemplate ;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdDaoWithJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    private HttpSession session;

    @Override
    public void save(Advertisement advertisement, String userId) {
        String sql = "insert into advertisement(id, name, description, price, currency, stars, created_by, created_at, image_url, category) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                advertisement.getId(),
                advertisement.getName(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getCurrency().name(),
                advertisement.getStars(),
                advertisement.getCreatedBy(),
                advertisement.getCreatedAt(),
                advertisement.getImageURL(),
                advertisement.getCategory()
        );
    }

    @Override
    public void update(Advertisement advertisement, String userId) {
        String sql = "update advertisement set name = ?, description = ?, price = ?, currency = ?, stars = ?, updated_at = ?, updated_by = ?, category = ? where id = ?";
        jdbcTemplate.update(
                sql,
                advertisement.getName(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getCurrency().name(),
                advertisement.getStars(),
                advertisement.getUpdatedAt(),
                advertisement.getUpdatedBy(),
                advertisement.getCategory(),
                advertisement.getId()
        );
    }

    @Override
    public void delete(String id) {
        String sql = "update advertisement set deleted = true where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public AdDto get(String id) {
        String sql = "select * from advertisement where id = ? and deleted = false";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                AdDto ad = new AdDto();
                ad.setId(rs.getString("id"));
                ad.setName(rs.getString("name"));
                ad.setDescription(rs.getString("description"));
                ad.setPrice(rs.getDouble("price"));
                ad.setImageURL(rs.getString("image_url"));
                ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
                ad.setStars(rs.getDouble("stars"));
                ad.setIsActive(rs.getBoolean("isactive"));
                ad.setAddOrder(rs.getLong("add_order"));
                ad.setCategory(rs.getString("category"));
                return ad;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void changeActivity(String id, String userId) {
        String sql = "update advertisement set isactive = ? where id = ? and deleted = false and created_by = ?";
        jdbcTemplate.update(sql, !get(id).getIsActive(), id, userId);
    }

    @Override
    public List<Advertisement> findAllByCategory(String category) {
        String sql = "select * from advertisement a where a.deleted = false and a.isactive = true and a.category = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Advertisement ad = new Advertisement();
            ad.setId(rs.getString("id"));
            ad.setName(rs.getString("name"));
            ad.setDescription(rs.getString("description"));
            ad.setPrice(rs.getDouble("price"));
            ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
            ad.setStars(rs.getDouble("stars"));
            ad.setImageURL(rs.getString("image_url"));
            ad.setAddOrder(rs.getLong("add_order"));
            ad.setIsActive(rs.getBoolean("isactive"));
            return ad;
        }, category);
    }

    @Override
    public List<Advertisement> findAll() {
        String sql = "select * from advertisement where deleted = false and isactive = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Advertisement ad = new Advertisement();
            ad.setId(rs.getString("id"));
            ad.setName(rs.getString("name"));
            ad.setDescription(rs.getString("description"));
            ad.setPrice(rs.getDouble("price"));
            ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
            ad.setStars(rs.getDouble("stars"));
            ad.setImageURL(rs.getString("image_url"));
            ad.setAddOrder(rs.getLong("add_order"));
            ad.setIsActive(rs.getBoolean("isactive"));
            ad.setCategory(rs.getString("category"));
            return ad;
        });
    }

    @Override
    public List<Advertisement> findByUser(String id) {
        String sql = "select * from advertisement where created_by = ? and deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Advertisement ad = new Advertisement();
            ad.setId(rs.getString("id"));
            ad.setName(rs.getString("name"));
            ad.setDescription(rs.getString("description"));
            ad.setPrice(rs.getDouble("price"));
            ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
            ad.setStars(rs.getDouble("stars"));
            ad.setIsActive(rs.getBoolean("isactive"));
            ad.setAddOrder(rs.getLong("add_order"));
            ad.setCreatedBy(rs.getString("created_by"));
            ad.setCategory(rs.getString("category"));
            return ad;
        }, id);
    }
}
