-- apply changes
create table url (
  id                            bigint generated by default as identity not null,
  name                          varchar(255),
  created_at                    timestamp not null,
  constraint pk_url primary key (id)
);