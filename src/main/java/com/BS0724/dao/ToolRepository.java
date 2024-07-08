package com.BS0724.dao;

import com.BS0724.model.Tool.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends CrudRepository<Tool, String> {
}
