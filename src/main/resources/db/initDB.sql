DROP TABLE IF EXISTS citizen;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1000;

-- https://www.postgresql.org/docs/9.1/static/datatype-numeric.html
CREATE TABLE citizen
(
  id                    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  date                  date DEFAULT now()  NOT NULL,
  id_game               INT                 NOT NULL,
  name                  TEXT                NOT NULL,
  birthday              date                NOT NULL,
  national_rank         INT                 NOT NULL,
  level                 INT                 NOT NULL,
  experience_points     INT                 NOT NULL ,
  ground_division       INT                 NOT NULL ,
  strength              INT                 NOT NULL ,
  military_rank_points  BIGINT              NOT NULL ,
  military_rank         TEXT                NOT NULL ,
  aircraft_rank_points  BIGINT              NOT NULL ,
  aircraft_rank         TEXT                NOT NULL
);
CREATE UNIQUE INDEX citizens_unique_id_game_idx ON citizen (id_game);