package com.htp.repository;

import com.htp.domain.Department;

import java.util.List;

public interface DepartmentDao extends GenericDao<Department, Long> {
    List<Long> batchUpdate(List<Department> departments);
}