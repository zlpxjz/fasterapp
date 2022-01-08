DROP TABLE IF EXISTS t_sys_role_permssion_info;

CREATE TABLE t_sys_role_permssion_info (
    role_id   varchar(32),
    permission_id   varchar(32),
    id    varchar(32) not null ,
    created_by   varchar(32),
    created_date   timestamp,
    updated_by   varchar(32),
    updated_date   timestamp,
    is_deleted   char(1),
    PRIMARY KEY (,id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;