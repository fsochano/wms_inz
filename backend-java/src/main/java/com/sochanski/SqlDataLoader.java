package com.sochanski;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Order(0)
@Component
public class SqlDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SqlDataLoader.class);

    private final ApplicationProperties applicationProperties;
    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    @Autowired
    public SqlDataLoader(ApplicationProperties applicationProperties, ResourceLoader resourceLoader, DataSource dataSource) {
        this.applicationProperties = applicationProperties;
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        if(applicationProperties.isLoadSqlConstraints()) {
            log.info("Loading constrains from sql");
            loadSQLsFiles("classpath:sql/constraints/*.sql");
            log.info("Constrains loaded");
        }
        if(applicationProperties.isLoadSqlData()) {
            log.info("Loading data from sql");
            loadSQLsFiles("classpath:sql/data/*.sql");
            log.info("Data loaded");
        }
    }

    private void loadSQLsFiles(String sqlFilesPattern) throws IOException {
        Resource[] sqls = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                .getResources(sqlFilesPattern);
        log.info("Scripts loaded ({})", Stream.of(sqls).map(Resource::getFilename).collect(joining(", ")));

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setSeparator("/\n");
        resourceDatabasePopulator.addScripts(sqls);

        resourceDatabasePopulator.execute(dataSource);
    }
}
