--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

DO
$$
    BEGIN
        IF
            NOT EXISTS (SELECT
                        FROM pg_database
                        WHERE datname = 'springboot_security_open_template') THEN
            CREATE
                DATABASE springboot_security_open_template;
        END IF;
    END
$$;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: jwt_token; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public.jwt_token
(
    id          integer                     NOT NULL,
    token       character varying           NOT NULL,
    is_valid    boolean                     NOT NULL,
    expiry_date timestamp without time zone NOT NULL,
    user_id     integer                     NOT NULL
);


ALTER TABLE public.jwt_token
    OWNER TO spacecodee;

--
-- Name: jwt_token_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.jwt_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jwt_token_id_seq OWNER TO spacecodee;

--
-- Name: jwt_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.jwt_token_id_seq OWNED BY public.jwt_token.id;


--
-- Name: module; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public.module
(
    id        integer           NOT NULL,
    name      character varying NOT NULL,
    base_path character varying NOT NULL
);


ALTER TABLE public.module
    OWNER TO spacecodee;

--
-- Name: module_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.module_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.module_id_seq OWNER TO spacecodee;

--
-- Name: module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.module_id_seq OWNED BY public.module.id;


--
-- Name: operation; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public.operation
(
    id          integer           NOT NULL,
    tag         character varying NOT NULL,
    path        character varying,
    http_method character varying NOT NULL,
    permit_all  boolean           NOT NULL,
    module_id   integer           NOT NULL
);


ALTER TABLE public.operation
    OWNER TO spacecodee;

--
-- Name: operation_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.operation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.operation_id_seq OWNER TO spacecodee;

--
-- Name: operation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.operation_id_seq OWNED BY public.operation.id;


--
-- Name: permission; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public.permission
(
    id           integer NOT NULL,
    role_id      integer NOT NULL,
    operation_id integer NOT NULL
);


ALTER TABLE public.permission
    OWNER TO spacecodee;

--
-- Name: permission_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.permission_id_seq OWNER TO spacecodee;

--
-- Name: permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.permission_id_seq OWNED BY public.permission.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public.role
(
    id   integer           NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.role
    OWNER TO spacecodee;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_id_seq OWNER TO spacecodee;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: spacecodee
--

CREATE TABLE public."user"
(
    id       integer           NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    fullname character varying NOT NULL,
    lastname character varying NOT NULL,
    rol_id   integer           NOT NULL
);


ALTER TABLE public."user"
    OWNER TO spacecodee;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: spacecodee
--

CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_id_seq OWNER TO spacecodee;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: spacecodee
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- Name: jwt_token id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.jwt_token
    ALTER COLUMN id SET DEFAULT nextval('public.jwt_token_id_seq'::regclass);


--
-- Name: module id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.module
    ALTER COLUMN id SET DEFAULT nextval('public.module_id_seq'::regclass);


--
-- Name: operation id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.operation
    ALTER COLUMN id SET DEFAULT nextval('public.operation_id_seq'::regclass);


--
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ALTER COLUMN id SET DEFAULT nextval('public.permission_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.role
    ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user"
    ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- Data for Name: jwt_token; Type: TABLE DATA; Schema: public; Owner: spacecodee
--


--
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.module
VALUES (1, 'USER_ADMIN', '/user-admin');
INSERT INTO public.module
VALUES (2, 'AUTH', '/auth');
INSERT INTO public.module
VALUES (3, 'ENDPOINT_MANAGEMENT', '/endpoint-management');
INSERT INTO public.module
VALUES (4, 'USER_DEVELOPER', '/user-developer');
INSERT INTO public.module
VALUES (5, 'USER_TECHNICIAN', '/user-technician');
INSERT INTO public.module
VALUES (6, 'USER_CUSTOMER', '/user-customer');


--
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.operation
VALUES (2, 'AUTHENTICATE', '/authenticate', 'POST', true, 2);
INSERT INTO public.operation
VALUES (3, 'SHOW_PROFILE', '/profile', 'GET', false, 2);
INSERT INTO public.operation
VALUES (5, 'LOGOUT', '/logout', 'POST', false, 2);
INSERT INTO public.operation
VALUES (6, 'REFRESH_TOKEN', '/refresh-token', 'PUT', false, 2);
INSERT INTO public.operation
VALUES (7, 'MODULE', '/module', 'POST', false, 3);
INSERT INTO public.operation
VALUES (8, 'OPERATION', '/operation', 'POST', false, 3);
INSERT INTO public.operation
VALUES (9, 'PERMISSION', '/permission', 'POST', false, 3);
INSERT INTO public.operation
VALUES (4, 'VALIDATE_TOKEN', '/validate-token', 'GET', false, 2);
INSERT INTO public.operation
VALUES (1, 'REGISTER_ONE_ADMIN', '', 'POST', false, 1);
INSERT INTO public.operation
VALUES (12, 'MODULE_DELETE', '/module/[0-9]*', 'DELETE', false, 3);
INSERT INTO public.operation
VALUES (11, 'OPERATION_DELETE', '/operation/[0-9]*', 'DELETE', false, 3);
INSERT INTO public.operation
VALUES (10, 'PERMISSION_DELETE', '/permission/[0-9]*', 'DELETE', false, 3);
INSERT INTO public.operation
VALUES (14, 'DELETE_ONE_ADMIN', '/[0-9]*', 'DELETE', false, 1);
INSERT INTO public.operation
VALUES (13, 'UPDATE_ONE_ADMIN', '/[0-9]*', 'PUT', false, 1);
INSERT INTO public.operation
VALUES (16, 'DELETE_ONE_DEVELOPER', '/[0-9]*', 'DELETE', false, 4);
INSERT INTO public.operation
VALUES (17, 'UPDATE_ONE_DEVELOPER', '/[0-9]*', 'PUT', false, 4);
INSERT INTO public.operation
VALUES (15, 'REGISTER_ONE_DEVELOPER', '', 'POST', false, 4);
INSERT INTO public.operation
VALUES (18, 'FIND_ONE_DEVELOPER', '/[0-9]*', 'GET', false, 4);
INSERT INTO public.operation
VALUES (19, 'FIND_ALL_DEVELOPERS', '', 'GET', false, 4);
INSERT INTO public.operation
VALUES (20, 'FIND_ONE_ADMIN', '/[0-9]*', 'GET', false, 1);
INSERT INTO public.operation
VALUES (21, 'FIND_ALL_ADMINS', '', 'GET', false, 1);
INSERT INTO public.operation
VALUES (23, 'FIND_ONE_TECHNICIAN', '/[0-9]*', 'GET', false, 5);
INSERT INTO public.operation
VALUES (25, 'DELETE_ONE_TECHNICIAN', '/[0-9]*', 'DELETE', false, 5);
INSERT INTO public.operation
VALUES (26, 'UPDATE_ONE_TECHNICIAN', '/[0-9]*', 'PUT', false, 5);
INSERT INTO public.operation
VALUES (22, 'REGISTER_ONE_TECHNICIAN', '', 'POST', false, 5);
INSERT INTO public.operation
VALUES (24, 'FIND_ALL_TECHNICIAN', '', 'GET', false, 5);
INSERT INTO public.operation
VALUES (27, 'REGISTER_ONE_CUSTOMER', ' ', 'POST', false, 6);
INSERT INTO public.operation
VALUES (28, 'FIND_ONE_CUSTOMER', '/[0-9]*', 'GET', false, 6);
INSERT INTO public.operation
VALUES (29, 'FIND_ALL_CUSTOMER', ' ', 'GET', false, 6);
INSERT INTO public.operation
VALUES (30, 'DELETE_ONE_CUSTOMER', '/[0-9]*', 'DELETE', false, 6);
INSERT INTO public.operation
VALUES (31, 'UPDATE_ONE_CUSTOMER', '/[0-9]*', 'PUT', false, 6);


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.permission
VALUES (1, 1, 3);
INSERT INTO public.permission
VALUES (2, 2, 3);
INSERT INTO public.permission
VALUES (3, 3, 3);
INSERT INTO public.permission
VALUES (4, 4, 3);
INSERT INTO public.permission
VALUES (5, 1, 4);
INSERT INTO public.permission
VALUES (6, 2, 4);
INSERT INTO public.permission
VALUES (7, 3, 4);
INSERT INTO public.permission
VALUES (8, 4, 4);
INSERT INTO public.permission
VALUES (9, 1, 5);
INSERT INTO public.permission
VALUES (10, 2, 5);
INSERT INTO public.permission
VALUES (11, 3, 5);
INSERT INTO public.permission
VALUES (12, 4, 5);
INSERT INTO public.permission
VALUES (13, 1, 6);
INSERT INTO public.permission
VALUES (14, 2, 6);
INSERT INTO public.permission
VALUES (15, 3, 6);
INSERT INTO public.permission
VALUES (16, 4, 6);
INSERT INTO public.permission
VALUES (17, 4, 7);
INSERT INTO public.permission
VALUES (18, 4, 8);
INSERT INTO public.permission
VALUES (19, 4, 9);
INSERT INTO public.permission
VALUES (20, 4, 10);
INSERT INTO public.permission
VALUES (21, 4, 11);
INSERT INTO public.permission
VALUES (22, 4, 12);
INSERT INTO public.permission
VALUES (23, 1, 13);
INSERT INTO public.permission
VALUES (24, 1, 14);
INSERT INTO public.permission
VALUES (25, 4, 13);
INSERT INTO public.permission
VALUES (26, 4, 14);
INSERT INTO public.permission
VALUES (27, 1, 1);
INSERT INTO public.permission
VALUES (28, 4, 1);
INSERT INTO public.permission
VALUES (29, 1, 15);
INSERT INTO public.permission
VALUES (30, 4, 15);
INSERT INTO public.permission
VALUES (31, 4, 16);
INSERT INTO public.permission
VALUES (32, 4, 17);
INSERT INTO public.permission
VALUES (33, 4, 18);
INSERT INTO public.permission
VALUES (34, 4, 19);
INSERT INTO public.permission
VALUES (35, 1, 20);
INSERT INTO public.permission
VALUES (36, 1, 21);
INSERT INTO public.permission
VALUES (37, 4, 20);
INSERT INTO public.permission
VALUES (38, 4, 21);
INSERT INTO public.permission
VALUES (39, 1, 22);
INSERT INTO public.permission
VALUES (40, 1, 23);
INSERT INTO public.permission
VALUES (41, 1, 24);
INSERT INTO public.permission
VALUES (42, 1, 25);
INSERT INTO public.permission
VALUES (43, 1, 26);
INSERT INTO public.permission
VALUES (44, 1, 27);
INSERT INTO public.permission
VALUES (45, 1, 28);
INSERT INTO public.permission
VALUES (46, 1, 29);
INSERT INTO public.permission
VALUES (47, 1, 30);
INSERT INTO public.permission
VALUES (48, 1, 31);
INSERT INTO public.permission
VALUES (49, 4, 22);
INSERT INTO public.permission
VALUES (50, 4, 23);
INSERT INTO public.permission
VALUES (51, 4, 24);
INSERT INTO public.permission
VALUES (52, 4, 25);
INSERT INTO public.permission
VALUES (53, 4, 26);
INSERT INTO public.permission
VALUES (54, 4, 27);
INSERT INTO public.permission
VALUES (55, 4, 28);
INSERT INTO public.permission
VALUES (56, 4, 29);
INSERT INTO public.permission
VALUES (57, 4, 30);
INSERT INTO public.permission
VALUES (58, 4, 31);


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.role
VALUES (1, 'OTI_ADMIN');
INSERT INTO public.role
VALUES (2, 'TECHNICIAN');
INSERT INTO public.role
VALUES (4, 'DEVELOPER');
INSERT INTO public.role
VALUES (3, 'CUSTOMER');


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public."user"
VALUES (1, 'admin123', '$2a$10$qU.kwfZGqmdT/LZENM7zPe5HUps5L1pVQHMNUIRsr/ive7RSxkCPC', 'Admin', 'Admin', 1);
INSERT INTO public."user"
VALUES (2, 'spacecodee', '$2a$10$4KDJk1Y5cSrwSEqN7bTxX.Lzm7p9w9nB35GuZ57QWq4Ulzh6v7rxC', 'spacecodee', 'spacecodee', 4);
INSERT INTO public."user"
VALUES (3, 'admin456', '$2a$10$kURqR.wFaAWKa3lW8MVlDeLl0benKJVYYSheiAYii7Go54fRUbo/O', 'Admin2', 'Admin2', 1);


--
-- Name: jwt_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.jwt_token_id_seq', 1, false);


--
-- Name: module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.module_id_seq', 7, false);


--
-- Name: operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.operation_id_seq', 31, true);


--
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.permission_id_seq', 58, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.role_id_seq', 4, true);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.user_id_seq', 4, false);


--
-- Name: jwt_token jwt_token_pk; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.jwt_token
    ADD CONSTRAINT jwt_token_pk PRIMARY KEY (id);


--
-- Name: module module_pk; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_pk PRIMARY KEY (id);


--
-- Name: module module_pk_2; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_pk_2 UNIQUE (name);


--
-- Name: module module_pk_3; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_pk_3 UNIQUE (base_path);


--
-- Name: operation operation_pk; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.operation
    ADD CONSTRAINT operation_pk PRIMARY KEY (id);


--
-- Name: permission permission_pk; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pk PRIMARY KEY (id);


--
-- Name: role role_name_unique; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_name_unique UNIQUE (name);


--
-- Name: role role_pk; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pk PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: user user_username_key; Type: CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- Name: jwt_token jwt_token_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.jwt_token
    ADD CONSTRAINT jwt_token_user_id_fk FOREIGN KEY (user_id) REFERENCES public."user" (id);


--
-- Name: operation operation_module_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.operation
    ADD CONSTRAINT operation_module_id_fk FOREIGN KEY (module_id) REFERENCES public.module (id);


--
-- Name: permission permission_operation_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_operation_id_fk FOREIGN KEY (operation_id) REFERENCES public.operation (id);


--
-- Name: permission permission_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_role_id_fk FOREIGN KEY (role_id) REFERENCES public.role (id);


--
-- Name: user user_role_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_role_role_id_fk FOREIGN KEY (rol_id) REFERENCES public.role (id);


--
-- PostgreSQL database dump complete
--

