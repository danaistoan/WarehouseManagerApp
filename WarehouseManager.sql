--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-03-22 11:22:16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2116 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 16576)
-- Name: product_package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_package (
    id integer NOT NULL,
    description character varying(500) NOT NULL,
    type character varying(100) NOT NULL,
    product_pallet_id integer
);


ALTER TABLE product_package OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16574)
-- Name: product_package_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE product_package_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_package_id_seq OWNER TO postgres;

--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 181
-- Name: product_package_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_package_id_seq OWNED BY product_package.id;


--
-- TOC entry 184 (class 1259 OID 16587)
-- Name: product_pallet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_pallet (
    id integer NOT NULL,
    description character varying(500)
);


ALTER TABLE product_pallet OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16585)
-- Name: product_pallet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE product_pallet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_pallet_id_seq OWNER TO postgres;

--
-- TOC entry 2118 (class 0 OID 0)
-- Dependencies: 183
-- Name: product_pallet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_pallet_id_seq OWNED BY product_pallet.id;


--
-- TOC entry 1988 (class 2604 OID 16579)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package ALTER COLUMN id SET DEFAULT nextval('product_package_id_seq'::regclass);


--
-- TOC entry 1989 (class 2604 OID 16590)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_pallet ALTER COLUMN id SET DEFAULT nextval('product_pallet_id_seq'::regclass);


--
-- TOC entry 1991 (class 2606 OID 16584)
-- Name: package_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT package_pk PRIMARY KEY (id);


--
-- TOC entry 1993 (class 2606 OID 16592)
-- Name: pallet_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_pallet
    ADD CONSTRAINT pallet_pk PRIMARY KEY (id);


--
-- TOC entry 1994 (class 2606 OID 16603)
-- Name: product_pallet_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT product_pallet_id FOREIGN KEY (product_pallet_id) REFERENCES product_pallet(id);


--
-- TOC entry 2115 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-03-22 11:22:17

--
-- PostgreSQL database dump complete
--

