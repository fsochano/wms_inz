package com.sochanski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@Order(0)
@Component
public class SqlDataLoader implements CommandLineRunner {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    @Autowired
    public SqlDataLoader(ResourceLoader resourceLoader, DataSource dataSource) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSQLsFiles("classpath:sql/constraints/*.sql");
        loadSQLsFiles("classpath:sql/data/*.sql");
    }

    private void loadSQLsFiles(String sqlFilesPattern) throws IOException {
        Resource[] sqls = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                .getResources(sqlFilesPattern);

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setSeparator("/\n");
        resourceDatabasePopulator.addScripts(sqls);
        resourceDatabasePopulator.execute(dataSource);
    }
}
