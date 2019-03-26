package com.htp.repository.impl;

import com.htp.domain.Factory;
import com.htp.repository.FactoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class FactoryDaoImpl implements FactoryDao {


    private static final String FACTORY_ID = "factory_id";
    private static final String FACTORY_NAME = "factory_name";
    private static final String FACTORY_OPEN_YEAR = "factory_open_year";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*Read from Result Set by column name*/
    private Factory getDepartmentRowMapper(ResultSet resultSet, int i) throws SQLException {
        Factory factory = new Factory();
        factory.setFactoryId(resultSet.getLong(FACTORY_ID));
        factory.setFactoryName(resultSet.getString(FACTORY_NAME));
        factory.setFactoryOpenYear(resultSet.getDate(FACTORY_OPEN_YEAR));
        return factory;
    }

    @Override
    public List<Factory> findAll() {
        final String findAllQuery = "select * from factory";
//        return jdbcTemplate.query(findAllQuery, this::getUserRowMapper);
        return namedParameterJdbcTemplate.query(findAllQuery, this::getDepartmentRowMapper);
    }

    @Override
    public Factory findById(Long id) {
//        final String findById = "select * from user where user_id = ?";
////        return jdbcTemplate.queryForObject(findById, new Object[]{id}, this::getUserRowMapper);
        final String findById = "select * from factory where factory_id = :factoryId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("factoryId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getDepartmentRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from factory where factory_id = :factoryId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("factoryId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    public Factory save(Factory factory) {
        final String createQuery = "INSERT INTO factory (factory_id, factory_name, factory_open_year) " +
                "VALUES (:factoryId, :factoryName, :factoryOpenYear);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        addFactoryValue(factory, params);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdFactoryId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdFactoryId);
    }

    @Override
    public Factory update(Factory factory) {
        final String createQuery = "UPDATE factory set factory_name = :factoryName, factory_open_year = :factoryOpenYear " +
                "where factory_id = :factoryId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        addFactoryValue(factory, params);

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(factory.getFactoryId());
    }

    @Override
    public List<Long> batchUpdate(List<Factory> factories) {
        final String createQuery = "UPDATE factory set factory_name = :factoryName, factory_open_year = :factoryOpenYear " +
                "where factory_id = :factoryId";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Factory factory : factories) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            addFactoryValue(factory, params);
            batch.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return factories.stream().map(Factory::getFactoryId).collect(Collectors.toList());
    }

    private void addFactoryValue(Factory factory, MapSqlParameterSource params) {
        params.addValue("factoryId", factory.getFactoryId());
        params.addValue("factoryName", factory.getFactoryName());
        params.addValue("factoryOpenYear", factory.getFactoryOpenYear());
    }

}
