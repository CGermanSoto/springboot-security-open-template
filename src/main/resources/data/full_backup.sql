--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

-- Add this line at the top of your SQL file
DO
$$
    BEGIN
        IF NOT EXISTS (SELECT
                       FROM pg_database
                       WHERE datname = 'springboot_security_open_template') THEN
            CREATE DATABASE springboot_security_open_template;
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

COPY public.jwt_token (id, token, is_valid, expiry_date, user_id) FROM stdin;
\.


--
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

COPY public.module (id, name, base_path) FROM stdin;
1	USER_ADMIN	/user-admin
2	AUTH	/auth
3	ENDPOINT_MANAGEMENT	/endpoint-management
4	USER_DEVELOPER	/user-developer
5	USER_TECHNICIAN	/user-technician
6	USER_CUSTOMER	/user-customer
\.


--
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

COPY public.operation (id, tag, path, http_method, permit_all, module_id) FROM stdin;
2	AUTHENTICATE	/authenticate	POST	t	2
3	SHOW_PROFILE	/profile	GET	f	2
5	LOGOUT	/logout	POST	f	2
6	REFRESH_TOKEN	/refresh-token	PUT	f	2
7	MODULE	/module	POST	f	3
8	OPERATION	/operation	POST	f	3
9	PERMISSION	/permission	POST	f	3
4	VALIDATE_TOKEN	/validate-token	GET	f	2
1	REGISTER_ONE_ADMIN		POST	f	1
12	MODULE_DELETE	/module/[0-9]*	DELETE	f	3
11	OPERATION_DELETE	/operation/[0-9]*	DELETE	f	3
10	PERMISSION_DELETE	/permission/[0-9]*	DELETE	f	3
14	DELETE_ONE_ADMIN	/[0-9]*	DELETE	f	1
13	UPDATE_ONE_ADMIN	/[0-9]*	PUT	f	1
16	DELETE_ONE_DEVELOPER	/[0-9]*	DELETE	f	4
17	UPDATE_ONE_DEVELOPER	/[0-9]*	PUT	f	4
15	REGISTER_ONE_DEVELOPER		POST	f	4
18	FIND_ONE_DEVELOPER	/[0-9]*	GET	f	4
19	FIND_ALL_DEVELOPERS		GET	f	4
20	FIND_ONE_ADMIN	/[0-9]*	GET	f	1
21	FIND_ALL_ADMINS		GET	f	1
23	FIND_ONE_TECHNICIAN	/[0-9]*	GET	f	5
25	DELETE_ONE_TECHNICIAN	/[0-9]*	DELETE	f	5
26	UPDATE_ONE_TECHNICIAN	/[0-9]*	PUT	f	5
22	REGISTER_ONE_TECHNICIAN		POST	f	5
24	FIND_ALL_TECHNICIAN		GET	f	5
27	REGISTER_ONE_CUSTOMER	 	POST	f	6
28	FIND_ONE_CUSTOMER	/[0-9]*	GET	f	6
29	FIND_ALL_CUSTOMER	 	GET	f	6
30	DELETE_ONE_CUSTOMER	/[0-9]*	DELETE	f	6
31	UPDATE_ONE_CUSTOMER	/[0-9]*	PUT	f	6
\.


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

COPY public.permission (id, role_id, operation_id) FROM stdin;
1	1	3
2	2	3
3	3	3
4	4	3
5	1	4
6	2	4
7	3	4
8	4	4
9	1	5
10	2	5
11	3	5
12	4	5
13	1	6
14	2	6
15	3	6
16	4	6
17	4	7
18	4	8
19	4	9
20	4	10
21	4	11
22	4	12
23	1	13
24	1	14
25	4	13
26	4	14
27	1	1
28	4	1
29	1	15
30	4	15
31	4	16
32	4	17
33	4	18
34	4	19
35	1	20
36	1	21
37	4	20
38	4	21
39	1	22
40	1	23
41	1	24
42	1	25
43	1	26
44	1	27
45	1	28
46	1	29
47	1	30
48	1	31
49	4	22
50	4	23
51	4	24
52	4	25
53	4	26
54	4	27
55	4	28
56	4	29
57	4	30
58	4	31
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

COPY public.role (id, name) FROM stdin;
1	OTI_ADMIN
2	TECHNICIAN
4	DEVELOPER
3	CUSTOMER
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

COPY public."user" (id, username, password, fullname, lastname, rol_id) FROM stdin;
1	admin123	$2a$10$qU.kwfZGqmdT/LZENM7zPe5HUps5L1pVQHMNUIRsr/ive7RSxkCPC	Admin	Admin	1
2	spacecodee	$2a$10$4KDJk1Y5cSrwSEqN7bTxX.Lzm7p9w9nB35GuZ57QWq4Ulzh6v7rxC	spacecodee	spacecodee	4
3	admin456	$2a$10$kURqR.wFaAWKa3lW8MVlDeLl0benKJVYYSheiAYii7Go54fRUbo/O	Admin2	Admin2	1
\.


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

