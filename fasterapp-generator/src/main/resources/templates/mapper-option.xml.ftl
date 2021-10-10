<#if option == 'BaseResultMap'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        <#list pkFields as field>
        <id column="${field.columnName}" jdbcType="${field.jdbcType}" property="${field.name}" />
        </#list>
        <#list nonPkFields as field>
        <result column="${field.columnName}" jdbcType="${field.jdbcType}" property="${field.name}" />
        </#list>
<#elseif option == 'Base_Column_List'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        <#list fields as field>
        ${table}.${field.columnName} <#if field_has_next>,</#if>
        </#list>
<#elseif option == 'selectAll'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        SELECT <include refid="Base_Column_List"/>
        FROM ${table}
        WHERE STATUS = '1'
<#elseif option == 'selectByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        SELECT <include refid="Base_Column_List"/>
        FROM ${table}
        WHERE STATUS = '1'
        <#list pkFields as field>
         AND ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        </#list>
<#elseif option == 'deleteByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        UPDATE ${table} set STATUS = '1'
        <#list pkFields as field>
        <#if field_index = 0>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        <#else>
        AND ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        </#if>
        </#list>
<#elseif option == 'insert'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        INSERT INTO  ${table}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as field>
            ${field.columnName},
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list fields as field>
            <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}},
        </#list>
        </trim>
<#elseif option == 'updateByPrimaryKeySelective'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        UPDATE  ${table} set
        <trim suffix=" " suffixOverrides=",">
        <#list nonPkFields as field>
        <if test="_parameter.containsKey('${field.name}')">
        ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}},
        </if>
        </#list>
        </trim>
        <#list pkFields as field>
        <#if field_index = 0>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        <#else>
        AND ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        </#if>
        </#list>
<#elseif option == 'updateByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        UPDATE  ${table} set
        <trim suffix=" " suffixOverrides=",">
        <#list nonPkFields as field>
            ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}},
        </#list>
        </trim>
        <#list pkFields as field>
        <#if field_index = 0>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        <#else>
        AND ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        </#if>
        </#list>
</#if>