DROP TABLE IF EXISTS ${table};

CREATE TABLE ${table} (
    <#list fields as field>
    ${field.columnName}   ${field.columnDefinition},
    </#list>
    PRIMARY KEY (<#list fields as field><#if field.key><#if (field_index>0)>,</#if>${field.columnName}</#if></#list>)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;