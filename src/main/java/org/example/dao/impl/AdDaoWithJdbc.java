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
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdDaoWithJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    private HttpSession session;

    @Override
    public void save(Advertisement advertisement, String userId) {
        String sql = "insert into advertisement(id, name, description, price, currency, stars, created_by) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                advertisement.getId(),
                advertisement.getName(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getCurrency().name().toString(),
                advertisement.getStars(),
                userId
        );
    }

    @Override
    public void update(Advertisement advertisement, String userId) {
        String sql = "update advertisement set name = ?, description = ?, price = ?, currency = ?, stars = ?, updated_at = ?, updated_by = ? where id = ?";
        jdbcTemplate.update(
                sql,
                advertisement.getName(),
                advertisement.getDescription(),
                advertisement.getPrice(),
                advertisement.getCurrency().name(),
                advertisement.getStars(),
                LocalDateTime.now(),
                userId,
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
                ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
                ad.setStars(rs.getInt("stars"));
                ad.setAddOrder(rs.getLong("add_order"));
                return ad;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Advertisement> findAll() {
        String sql = "select * from advertisement where deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Advertisement ad = new Advertisement();
            ad.setId(rs.getString("id"));
            ad.setName(rs.getString("name"));
            ad.setDescription(rs.getString("description"));
            ad.setPrice(rs.getDouble("price"));
            ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
            ad.setStars(rs.getInt("stars"));
            ad.setIsActive(rs.getBoolean("isactive"));
            ad.setAddOrder(rs.getLong("add_order"));
            return ad;
        });
    }

    @Override
    public List<Advertisement> findById(String id) {
        String sql = "select * from advertisement where created_by = id and deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Advertisement ad = new Advertisement();
            ad.setId(rs.getString("id"));
            ad.setName(rs.getString("name"));
            ad.setDescription(rs.getString("description"));
            ad.setPrice(rs.getDouble("price"));
            ad.setCurrency(Currency.valueOf(rs.getObject("currency").toString()));
            ad.setStars(rs.getInt("stars"));
            ad.setIsActive(rs.getBoolean("isactive"));
            ad.setAddOrder(rs.getLong("add_order"));
            return ad;
        });
    }
}
