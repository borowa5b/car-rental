CREATE TABLE IF NOT EXISTS rental(
  	id varchar(255) NOT NULL PRIMARY KEY,
  	car_id varchar(255) NOT NULL,
  	customer_id varchar(255) NOT NULL,
  	status varchar(255) NOT NULL,
  	price numeric(38, 2) NOT NULL,
  	start_date timestamp(6) NOT NULL,
  	end_date timestamp(6) NOT NULL,
  	entity_version int8 NOT NULL,
  	creation_date timestamp(6) NOT NULL,
  	modification_date timestamp(6)
);

CREATE TABLE IF NOT EXISTS car(
      id varchar(255) NOT NULL PRIMARY KEY,
      brand varchar(255) NOT NULL,
      model varchar(255) NOT NULL,
      generation varchar(255) NOT NULL,
      production_year int4 NOT NULL,
      color varchar(255) NOT NULL,
  	  price_per_day numeric(38, 2) NOT NULL,
  	  quantity integer NOT NULL,
      entity_version int8 NOT NULL,
      creation_date timestamp(6) NOT NULL,
      modification_date timestamp(6)
);

CREATE TABLE IF NOT EXISTS customer(
      id varchar(255) NOT NULL PRIMARY KEY,
      name varchar(255) NOT NULL,
      surname varchar(255) NOT NULL,
      email varchar(255) NOT NULL UNIQUE,
      phone_number varchar(255) NOT NULL,
      address varchar(255) NOT NULL,
      document_number varchar(255) NOT NULL,
      entity_version int8 NOT NULL,
      creation_date timestamp(6) NOT NULL,
      modification_date timestamp(6) NULL
);

CREATE TABLE IF NOT EXISTS application_event(
      id varchar(255) NOT NULL PRIMARY KEY,
      type varchar(255) NOT NULL,
      version varchar(10) NOT NULL,
      status varchar(255) NOT NULL,
      payload jsonb NOT NULL,
      published_on_date timestamp(6) NULL,
      entity_version int8 NOT NULL,
      creation_date timestamp(6) NOT NULL,
      modification_date timestamp(6) NULL
);

CREATE TABLE IF NOT EXISTS external_event(
      id varchar(255) NOT NULL PRIMARY KEY,
      type varchar(255) NOT NULL,
      version varchar(10) NOT NULL,
      status varchar(255) NOT NULL,
      payload jsonb NOT NULL,
      error_message varchar(1024) NULL,
      entity_version int8 NOT NULL,
      processed_on_date timestamp(6) NULL,
      creation_date timestamp(6) NOT NULL,
      modification_date timestamp(6) NULL
);