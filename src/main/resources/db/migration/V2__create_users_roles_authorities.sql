CREATE TABLE users(
    id IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled VARCHAR(1) NOT NULL
);
ALTER TABLE users ADD CONSTRAINT email_length CHECK (LENGTH(email) >= 1 AND LENGTH(email) <= 255 );
ALTER TABLE users ADD CONSTRAINT password_length CHECK (LENGTH(password) >= 1 AND LENGTH(password) <= 255 );
ALTER TABLE users ADD CONSTRAINT user_enabled CHECK (enabled IN ('Y','N') );

CREATE TABLE role(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    reserved_by_system VARCHAR(1) NOT NULL
);
ALTER TABLE role ADD CONSTRAINT role_name_length CHECK (LENGTH(name) >= 1 AND LENGTH(name) <= 20 );
ALTER TABLE role ADD CONSTRAINT role_reserved_by_system CHECK (reserved_by_system IN ('Y','N') );

CREATE TABLE user_role(
    id_user BIGINT NOT NULL,
    id_role BIGINT NOT NULL,
    FOREIGN KEY(id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(id_role) REFERENCES role(id) ON DELETE CASCADE
);