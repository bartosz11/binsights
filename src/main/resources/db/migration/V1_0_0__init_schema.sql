create table application_metrics_schemas (id uuid not null, collectipaddresses boolean not null, enabled boolean not null, version varchar(255), application_id uuid, primary key (id));
create table application_metrics_schemas_fields (application_metrics_schema_id uuid not null, fields_id uuid not null, primary key (application_metrics_schema_id, fields_id));
create table applications (id uuid not null, influxdbbucketid varchar(255), influxdbbucket_name varchar(255), influxdbretention integer not null, name varchar(255), primary key (id));
create table applications_schemas (application_id uuid not null, schemas_id uuid not null, primary key (application_id, schemas_id));
create table invite_codes (id varchar(255) not null, expires_on bigint not null, user_id uuid, primary key (id));
create table metric_fields (id uuid not null, description varchar(255), name varchar(255), type smallint check (type between 0 and 3), schema_id uuid, primary key (id));
create table users (id uuid not null, enabled boolean not null, last_updated bigint not null, password varchar(255), username varchar(255), primary key (id));
create table users_invite_codes (user_id uuid not null, invite_codes_id varchar(255) not null, primary key (user_id, invite_codes_id));
alter table application_metrics_schemas_fields add constraint schemas_fields_fid_unique unique (fields_id);
alter table applications add constraint applications_bcid_unique unique (influxdbbucketid);
alter table applications add constraint applications_bc_name_unique unique (influxdbbucket_name);
alter table applications add constraint applications_name_unique unique (name);
alter table applications_schemas add constraint apps_schemas_scid_unique unique (schemas_id);
alter table users_invite_codes add constraint users_invites_invid_unique unique (invite_codes_id);
alter table application_metrics_schemas add constraint schemas_fk_appid_ref_apps foreign key (application_id) references applications;
alter table application_metrics_schemas_fields add constraint schemas_fields_fk_fid_ref_fields foreign key (fields_id) references metric_fields;
alter table application_metrics_schemas_fields add constraint schemas_fields_fk_sid_ref_schemas foreign key (application_metrics_schema_id) references application_metrics_schemas;
alter table applications_schemas add constraint apps_schemas_fk_sid_ref_schemas foreign key (schemas_id) references application_metrics_schemas;
alter table applications_schemas add constraint schemas_fk_aid_ref_apps foreign key (application_id) references applications;
alter table invite_codes add constraint invites_fk_uid_ref_users foreign key (user_id) references users;
alter table metric_fields add constraint fields_fk_sid_ref_schemas foreign key (schema_id) references application_metrics_schemas;
alter table users_invite_codes add constraint users_invites_fk_invid_ref_invites foreign key (invite_codes_id) references invite_codes;
alter table users_invite_codes add constraint users_invites_fk_uid_ref_users foreign key (user_id) references users;