DROP TABLE IF EXISTS friendship_status CASCADE;
DROP TABLE IF EXISTS mpa_rating CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS film_genre CASCADE;
DROP TABLE IF EXISTS friends CASCADE;

CREATE TABLE IF NOT EXISTS friendship_status
(
    status_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      varchar
);

CREATE TABLE IF NOT EXISTS mpa_rating
(
    rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      varchar(5)
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     varchar
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar NOT NULL,
    description  varchar NOT NULL,
    release_date date,
    duration     long,
    rate         long,
    rating_id    int REFERENCES mpa_rating (rating_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    varchar NOT NULL,
    login    varchar NOT NULL,
    name     varchar,
    birthday date
);

CREATE TABLE IF NOT EXISTS likes
(
    like_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id integer REFERENCES films (film_id),
    user_id integer REFERENCES users (user_id),
    UNIQUE (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friends
(
    friendship_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id       integer REFERENCES users (user_id) NOT NULL,
    friend_id     integer REFERENCES users (user_id) NOT NULL,
    status_id     integer REFERENCES friendship_status (status_id),
    UNIQUE (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_id      integer REFERENCES genre (genre_id),
    film_id       integer REFERENCES films (film_id),
    UNIQUE (genre_id, film_id)
);