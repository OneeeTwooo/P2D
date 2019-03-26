package com.htp.repository.impl;

import com.htp.domain.Department;
import com.htp.repository.DepartmentDao;
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
public class DepartmentDaoImpl implements DepartmentDao {

    private static final String DEP_ID = "dep_id";
    private static final String DEP_NAME = "dep_name";
    private static final String DEP_CAPACITY = "dep_capacity";
    private static final String FACTORY_ID = "factory_id";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*Read from Result Set by column name*/
    private Department getDepartmentRowMapper(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();
        department.setDepId(resultSet.getLong(DEP_ID));
        department.setDepName(resultSet.getString(DEP_NAME));
        department.setDepCapacity(resultSet.getLong(DEP_CAPACITY));
        department.setFactoryId(resultSet.getLong(FACTORY_ID));
        return department;
    }

    @Override
    public List<Department> findAll() {
        final String findAllQuery = "select * from department";
//        return jdbcTemplate.query(findAllQuery, this::getUserRowMapper);
        return namedParameterJdbcTemplate.query(findAllQuery, this::getDepartmentRowMapper);
    }

    @Override
    public Department findById(Long id) {
//        final String findById = "select * from user where user_id = ?";
////        return jdbcTemplate.queryForObject(findById, new Object[]{id}, this::getUserRowMapper);
        final String findById = "select * from department where dep_id = :depId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("depId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getDepartmentRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from department where dep_id = :depId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("depId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    public Department save(Department department) {
        final String createQuery = "INSERT INTO department (dep_id, dep_name, dep_capacity, factory_id) " +
                "VALUES (:depId, :depName, :depCapacity, :factoryId);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        addDepartmentValue(department, params);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdDepartmentId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdDepartmentId);
    }

    @Override
    public Department update(Department department) {
        final String createQuery = "UPDATE department set dep_name = :depName, " +
                "dep_capacity = :depCapacity, factory_id = :factoryId where dep_id = :depId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        addDepartmentValue(department, params);

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(department.getDepId());
    }

    @Override
    public List<Long> batchUpdate(List<Department> departments) {
        final String createQuery = "UPDATE department set dep_name = :depName, " +
                "dep_capacity = :depCapacity, factory_id = :factoryId where dep_id = :depId";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Department department : departments) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            addDepartmentValue(department, params);
            batch.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return departments.stream().map(Department::getDepId).collect(Collectors.toList());
    }

    private void addDepartmentValue(Department department, MapSqlParameterSource params) {
        params.addValue("depId", department.getDepId());
        params.addValue("depName", department.getDepName());
        params.addValue("depCapacity", department.getDepCapacity());
        params.addValue("factoryId", department.getFactoryId());
    }

}
