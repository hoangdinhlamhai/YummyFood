package com.heri.adminWeb.config;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    public String toPhysicalTableName(String name, JdbcEnvironment context) {
        return name;  // Trả về đúng tên bảng như trong annotation @Table(name = "MonAn")
    }
}
