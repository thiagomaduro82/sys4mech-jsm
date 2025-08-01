-- Create the roles table
create table roles (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(60) not null unique,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the permissions table
create table permissions (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(60) not null unique,
    description varchar(255) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the roles_permissions table
create table role_permissions (
    id bigint unsigned not null auto_increment primary key,
    role_id bigint unsigned not null,
    permission_id bigint unsigned not null,
    foreign key (role_id) references roles(id) on delete cascade,
    foreign key (permission_id) references permissions(id)
) ENGINE = innoDB default charset = utf8mb4;

-- Create the users table
create table users (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(60) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role_id bigint unsigned not null,
    foreign key (role_id) references roles(id),
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the employees table
create table employees (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(60) not null,
    email varchar(255) not null unique,
    address_line1 varchar(100) not null,
    address_line2 varchar(100),
    city varchar(50) not null,
    county varchar(50) not null,
    postal_code varchar(20) not null,
    country varchar(50) not null,
    date_of_birth date not null,
    phone varchar(20),
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the customers table
create table customers (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(60) not null,
    date_of_birth date not null,
    email varchar(255) not null unique,
    address_line1 varchar(100) not null,
    address_line2 varchar(100),
    city varchar(50) not null,
    county varchar(50) not null,
    postal_code varchar(20) not null,
    country varchar(50) not null,
    phone varchar(20),
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the customer_cars table
create table customer_cars (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    customer_id bigint unsigned not null,
    make varchar(100) not null,
    model varchar(100) not null,
    year int not null,
    color varchar(30),
    registration_number varchar(20) not null unique,
    vin varchar(20) not null unique,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (customer_id) references customers(id) on delete cascade
) ENGINE = innoDB default charset = utf8mb4;

-- Create the customer_car_photos table
create table customer_car_photos (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    customer_car_id bigint unsigned not null,
    photo_url varchar(255) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (customer_car_id) references customer_cars(id) on delete cascade
) ENGINE = innoDB default charset = utf8mb4;

-- Create the services table
create table services (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(100) not null,
    amount decimal(10, 2) not null,
    vat_rate decimal(5, 2) not null,
    electronic_diagnosis boolean not null default false,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the suppliers table
create table suppliers (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    name varchar(100) not null,
    contact_name varchar(100),
    email varchar(255) not null unique,
    phone varchar(20),
    address_line1 varchar(100) not null,
    address_line2 varchar(100),
    city varchar(50) not null,
    county varchar(50) not null,
    postal_code varchar(20) not null,
    country varchar(50) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
) ENGINE = innoDB default charset = utf8mb4;

-- Create the car_parts table
create table car_parts (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    supplier_id bigint unsigned not null,
    name varchar(100) not null,
    description varchar(255),
    cost_price decimal(10, 2) not null,
    selling_price decimal(10, 2) not null,
    vat_rate decimal(5, 2) not null,
    barcode varchar(50) not null unique,
    stock_quantity int not null default 0,
    min_stock_quantity int not null default 0,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (supplier_id) references suppliers(id)
) ENGINE = innoDB default charset = utf8mb4;

-- Create the service_orders table
create table service_orders (
    id bigint unsigned not null auto_increment primary key,
    uuid char(32) not null unique,
    customer_id bigint unsigned not null,
    customer_car_id bigint unsigned not null,
    employee_id bigint unsigned not null,
    status enum('SCHEDULE', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') not null default 'SCHEDULE',
    work_required text,
    observations text,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (customer_id) references customers(id),
    foreign key (customer_car_id) references customer_cars(id),
    foreign key (employee_id) references employees(id)
) ENGINE = innoDB default charset = utf8mb4;

-- Create the service_order_parts table
create table service_order_parts (
    id bigint unsigned not null auto_increment primary key,
    service_order_id bigint unsigned not null,
    car_part_id bigint unsigned not null,
    quantity int not null default 1,
    amount decimal(10, 2) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (service_order_id) references service_orders(id) on delete cascade,
    foreign key (car_part_id) references car_parts(id)
) ENGINE = innoDB default charset = utf8mb4;

-- Create the service_order_services table
create table service_order_services (
    id bigint unsigned not null auto_increment primary key,
    service_order_id bigint unsigned not null,
    service_id bigint unsigned not null,
    quantity int not null default 1,
    amount decimal(10, 2) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    foreign key (service_order_id) references service_orders(id) on delete cascade,
    foreign key (service_id) references services(id)
) ENGINE = innoDB default charset = utf8mb4;

