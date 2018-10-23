DELETE
FROM citizen;

ALTER SEQUENCE global_seq
  RESTART WITH 1000;

INSERT INTO citizen (id_game,
                     name,
                     birthday,
                     national_rank,
                     level,
                     experience_points,
                     ground_division,
                     strength,
                     military_rank_points,
                     military_rank,
                     aircraft_rank_points,
                     aircraft_rank)
VALUES (2, 'Plato', '2007-06-04', 67, 27, 10357, 1, 127200, 40001, 'Commander', 0, 'Airman');