CREATE USER pivotal IDENTIFIED BY bekind;
GRANT CONNECT TO pivotal;
GRANT CONNECT, RESOURCE, DBA TO pivotal;
GRANT UNLIMITED TABLESPACE TO pivotal;
GRANT CREATE SESSION TO pivotal;

drop table employee;

create table employee (
  id         number(10, 0) not null,
  email      varchar2(255 char),
  first_name varchar2(255 char),
  last_name  varchar2(255 char),
  created_at date,
  primary key (id)
);

CREATE OR REPLACE PROCEDURE employeeProcedure(
  first_name IN     varchar2,
  last_name  IN     varchar2,
  email      IN OUT varchar2,
  id         OUT    number,
  created_at OUT    date)
IS
  BEGIN

    email := upper(email);
    id := DBMS_RANDOM.RANDOM;
    created_at := sysdate;

    insert into employee values (id, email, first_name, last_name, created_at);

  COMMIT;
  END;
/
