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
        ${entity?uncap_first}.${field.columnName} <#if field_has_next>,</#if>
        </#list>
<#elseif option == 'selectAll'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        select <include refid="Base_Column_List"/>
        from ${table} ${entity?uncap_first}
        where is_deleted = 'N'
<#elseif option == 'selectByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        select <include refid="Base_Column_List"/>
        from ${table} ${entity?uncap_first}
        where is_deleted = 'N'
        <#list pkFields as field>
        and ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
        </#list>
<#elseif option == 'deleteByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        update ${table} set is_deleted = 'Y'
        <#list pkFields as field>
                <#if field_index = 0>
                where ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                <#else>
                and ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                </#if>
        </#list>
<#elseif option == 'insert'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        insert into  ${table}
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
        update  ${table} set
        <trim suffix=" " suffixOverrides=",">
                <#list nonPkFields as field>
                    <if test="_parameter.containsKey('${field.name}')">
                    ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}},
                    </if>
                </#list>
        </trim>
        <#list pkFields as field>
                <#if field_index = 0>
                where ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                <#else>
                and ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                </#if>
        </#list>
<#elseif option == 'updateByPrimaryKey'>
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        update  ${table} set
        <trim suffix=" " suffixOverrides=",">
                <#list nonPkFields as field>
                ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}},
                </#list>
        </trim>
        <#list pkFields as field>
                <#if field_index = 0>
                where ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                <#else>
                and ${field.columnName} = <#noparse>#{</#noparse>${field.name}, jdbcType=${field.jdbcType}}
                </#if>
        </#list>
</#if>