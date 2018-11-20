CREATE TABLE public.beer
(
  id bigint NOT NULL,
  name character varying(100),
  type character varying(50),
  volume bigint,
  CONSTRAINT pk_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.beer
  OWNER TO beerstore;

CREATE SEQUENCE public.beer_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;
ALTER TABLE public.beer_seq
  OWNER TO beerstore;
