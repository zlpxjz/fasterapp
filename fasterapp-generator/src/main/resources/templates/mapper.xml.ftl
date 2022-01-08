<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.mapper.${entity?cap_first}Mapper">
    <resultMap id="BaseResultMap" type="${basePackage}.model.${entity?cap_first}Model">
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
    </resultMap>

    <sql id="Base_Column_List">
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        <#list fields as field>
        ${entity?uncap_first}.${field.columnName} <#if field_has_next>,</#if>
        </#list>
    </sql>

    <select id="selectAll" resultMap="BaseResultMap">
        <!--
          WARNING - @mbggenerated
          This element is automatically generated when running, do not modify.
        -->
        select <include refid="Base_Column_List"/>
        from ${table} ${entity?uncap_first}
        where is_deleted = 'N'
    </select>

    <select id="selectByPrimaryKey" parameterType="${pkFullType}" resultMap="BaseResultMap">
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
    </select>

    <delete id="deleteByPrimaryKey" parameterType="${pkFullType}">
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
    </delete>

    <insert id="insert" parameterType="${basePackage}.model.${entity?cap_first}Model">
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
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="java.util.Map">
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
    </update>

    <update id="updateByPrimaryKey" parameterType="${basePackage}.model.${entity?cap_first}Model">
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
    </update>
</mapper>