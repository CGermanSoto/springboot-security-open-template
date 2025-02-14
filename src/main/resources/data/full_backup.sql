--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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

CREATE TABLE public.jwt_token (
    id integer NOT NULL,
    token character varying NOT NULL,
    is_valid boolean NOT NULL,
    expiry_date timestamp without time zone NOT NULL,
    user_id integer NOT NULL,
    is_revoked boolean NOT NULL,
    state character varying NOT NULL,
    revoked_at timestamp without time zone,
    revoked_reason character varying,
    refresh_count integer,
    last_refresh_at timestamp without time zone,
    previous_token character varying,
    usage_count integer,
    last_access_at timestamp without time zone,
    last_operation character varying,
    jti character varying NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public.jwt_token OWNER TO spacecodee;

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

CREATE TABLE public.module (
    id integer NOT NULL,
    name character varying NOT NULL,
    base_path character varying NOT NULL
);


ALTER TABLE public.module OWNER TO spacecodee;

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

CREATE TABLE public.operation (
    id integer NOT NULL,
    tag character varying NOT NULL,
    path character varying,
    http_method character varying NOT NULL,
    permit_all boolean NOT NULL,
    module_id integer NOT NULL
);


ALTER TABLE public.operation OWNER TO spacecodee;

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

CREATE TABLE public.permission (
    id integer NOT NULL,
    role_id integer NOT NULL,
    operation_id integer NOT NULL
);


ALTER TABLE public.permission OWNER TO spacecodee;

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

CREATE TABLE public.role (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.role OWNER TO spacecodee;

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

CREATE TABLE public."user" (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    rol_id integer NOT NULL,
    email character varying NOT NULL,
    phone_number character varying,
    status boolean NOT NULL,
    profile_picture_path character varying NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE public."user" OWNER TO spacecodee;

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

ALTER TABLE ONLY public.jwt_token ALTER COLUMN id SET DEFAULT nextval('public.jwt_token_id_seq'::regclass);


--
-- Name: module id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.module ALTER COLUMN id SET DEFAULT nextval('public.module_id_seq'::regclass);


--
-- Name: operation id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.operation ALTER COLUMN id SET DEFAULT nextval('public.operation_id_seq'::regclass);


--
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission ALTER COLUMN id SET DEFAULT nextval('public.permission_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- Data for Name: jwt_token; Type: TABLE DATA; Schema: public; Owner: spacecodee
--



--
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.module VALUES (2, 'AUTH', '/auth');
INSERT INTO public.module VALUES (3, 'MODULE', '/security/module');
INSERT INTO public.module VALUES (8, 'PERMISSION', '/security/permission');
INSERT INTO public.module VALUES (9, 'OPERATION', '/security/operation');
INSERT INTO public.module VALUES (10, 'ROLE', '/security/role');
INSERT INTO public.module VALUES (7, 'MONITORING_CACHE', '/monitoring/cache');
INSERT INTO public.module VALUES (11, 'MONITORING_RATELIMIT', '/monitoring/rate-limit');
INSERT INTO public.module VALUES (12, 'PERMISSION_JWT', '/security/permission-jwt');
INSERT INTO public.module VALUES (13, 'USER', '/user/security');
INSERT INTO public.module VALUES (1, 'SYSTEM_ADMIN', '/user/system-admin');
INSERT INTO public.module VALUES (5, 'EDITOR', '/user/editor');
INSERT INTO public.module VALUES (4, 'DEVELOPER', '/user/security/developer');
INSERT INTO public.module VALUES (6, 'GUEST', '/user/guest');
INSERT INTO public.module VALUES (14, 'MANAGER', '/user/manager');
INSERT INTO public.module VALUES (15, 'VIEWER', '/user/viewer');


--
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.operation VALUES (1, 'CREATE_ONE_SYSTEM_ADMIN', '', 'POST', false, 1);
INSERT INTO public.operation VALUES (2, 'UPDATE_ONE_SYSTEM_ADMIN', '/[0-9]*', 'PUT', false, 1);
INSERT INTO public.operation VALUES (3, 'UPDATE_STATUS_ONE_SYSTEM_ADMIN', '/[0-9]+/status/(true|false)', 'PATCH', false, 1);
INSERT INTO public.operation VALUES (4, 'DELETE_ONE_SYSTEM_ADMIN', '/[0-9]*', 'DELETE', false, 1);
INSERT INTO public.operation VALUES (5, 'FIND_ONE_SYSTEM_ADMIN', '/[0-9]*', 'GET', false, 1);
INSERT INTO public.operation VALUES (6, 'FIND_ONE_DETAIL_SYSTEM_ADMIN', '/[0-9]*/detail', 'GET', false, 1);
INSERT INTO public.operation VALUES (7, 'FIND_ALL_PAGE_SYSTEM_ADMINS', '/search', 'GET', false, 1);
INSERT INTO public.operation VALUES (8, 'LOGOUT', '/logout', 'POST', false, 2);
INSERT INTO public.operation VALUES (9, 'LOGIN', '/login', 'POST', true, 2);
INSERT INTO public.operation VALUES (10, 'REFRESH_TOKEN', '/refresh-token', 'POST', false, 2);
INSERT INTO public.operation VALUES (11, 'VALIDATE_TOKEN', '/validate-token', 'GET', false, 2);
INSERT INTO public.operation VALUES (12, 'UPDATE_ONE_MODULE', '/[0-9]*', 'PUT', false, 3);
INSERT INTO public.operation VALUES (13, 'DELETE_ONE_MODULE', '/[0-9]*', 'DELETE', false, 3);
INSERT INTO public.operation VALUES (14, 'FIND_ONE_MODULE', '/[0-9]*', 'GET', false, 3);
INSERT INTO public.operation VALUES (15, 'FIND_ALL_PAGE_MODULE', '/search', 'GET', false, 3);
INSERT INTO public.operation VALUES (16, 'FIND_ALL_MODULE', '', 'GET', false, 3);
INSERT INTO public.operation VALUES (17, 'CREATE_ONE_MODULE', '', 'POST', false, 3);
INSERT INTO public.operation VALUES (18, 'DELETE_ONE_DEVELOPER', '/[0-9]*', 'DELETE', false, 4);
INSERT INTO public.operation VALUES (19, 'UPDATE_ONE_DEVELOPER', '/[0-9]*', 'PUT', false, 4);
INSERT INTO public.operation VALUES (20, 'FIND_ONE_DEVELOPER', '/[0-9]*', 'GET', false, 4);
INSERT INTO public.operation VALUES (21, 'CREATE_ONE_DEVELOPER', '', 'POST', false, 4);
INSERT INTO public.operation VALUES (22, 'FIND_ONE_DETAIL_DEVELOPER', '/[0-9]*/detail', 'GET', false, 4);
INSERT INTO public.operation VALUES (23, 'UPDATE_STATUS_ONE_DEVELOPER', '/[0-9]+/status/(true|false)', 'PUT', false, 4);
INSERT INTO public.operation VALUES (24, 'FIND_ALL_PAGE_DEVELOPERS', '/search', 'GET', false, 4);
INSERT INTO public.operation VALUES (25, 'DELETE_ONE_EDITOR', '/[0-9]*', 'DELETE', false, 5);
INSERT INTO public.operation VALUES (26, 'UPDATE_ONE_EDITOR', '/[0-9]*', 'PUT', false, 5);
INSERT INTO public.operation VALUES (28, 'CREATE_ONE_EDITOR', '', 'POST', false, 5);
INSERT INTO public.operation VALUES (29, 'FIND_ONE_EDITOR', '/[0-9]*', 'GET', false, 5);
INSERT INTO public.operation VALUES (32, 'CREATE_ONE_GUEST', '', 'POST', false, 6);
INSERT INTO public.operation VALUES (33, 'DELETE_ONE_GUEST', '/[0-9]*', 'DELETE', false, 6);
INSERT INTO public.operation VALUES (34, 'UPDATE_ONE_GUEST', '/[0-9]*', 'PUT', false, 6);
INSERT INTO public.operation VALUES (35, 'UPDATE_STATUS_ONE_GUEST', '/[0-9]+/status/(true|false)', 'PATCH', false, 6);
INSERT INTO public.operation VALUES (36, 'FIND_ONE_GUEST', '/[0-9]*', 'GET', false, 6);
INSERT INTO public.operation VALUES (37, 'FIND_ONE_DETAIL_GUEST', '/[0-9]*/detail', 'GET', false, 6);
INSERT INTO public.operation VALUES (38, 'FIND_ALL_PAGE_GUEST', '/search', 'GET', false, 6);
INSERT INTO public.operation VALUES (40, 'UPDATE_ONE_PERMISSION', '/[0-9]*', 'PUT', false, 8);
INSERT INTO public.operation VALUES (41, 'FIND_ONE_DETAIL_PERMISSION', '/[0-9]*/detail', 'GET', false, 8);
INSERT INTO public.operation VALUES (42, 'CREATE_ONE_PERMISSION', '', 'POST', false, 8);
INSERT INTO public.operation VALUES (43, 'FIND_ALL_PAGE_PERMISSION', '/search', 'GET', false, 8);
INSERT INTO public.operation VALUES (44, 'DELETE_ONE_PERMISSION', '/[0-9]*', 'DELETE', false, 8);
INSERT INTO public.operation VALUES (45, 'FIND_ONE_PERMISSION', '/[0-9]*', 'GET', false, 8);
INSERT INTO public.operation VALUES (46, 'FIND_ALL_PERMISSION', '', 'GET', false, 8);
INSERT INTO public.operation VALUES (47, 'FIND_ALL_PERMISSION_BY_ROLE', '/role/[0-9]*', 'GET', false, 8);
INSERT INTO public.operation VALUES (48, 'CREATE_ONE_OPERATION', '', 'POST', false, 9);
INSERT INTO public.operation VALUES (49, 'UPDATE_ONE_OPERATION', '/[0-9]*', 'PUT', false, 9);
INSERT INTO public.operation VALUES (50, 'DELETE_ONE_OPERATION', '/[0-9]*', 'DELETE', false, 9);
INSERT INTO public.operation VALUES (51, 'FIND_ONE_OPERATION', '/[0-9]*', 'GET', false, 9);
INSERT INTO public.operation VALUES (52, 'FIND_ONE_DETAIL_OPERATION', '/[0-9]*/detail', 'GET', false, 9);
INSERT INTO public.operation VALUES (53, 'FIND_ALL_PAGE_OPERATION', '/search', 'GET', false, 9);
INSERT INTO public.operation VALUES (54, 'FIND_ALL_OPERATION_BY_MODULE', '/module/[0-9]*', 'GET', false, 9);
INSERT INTO public.operation VALUES (55, 'FIND_ALL_OPERATION', '', 'GET', false, 9);
INSERT INTO public.operation VALUES (56, 'CREATE_ONE_ROLE', '', 'POST', false, 10);
INSERT INTO public.operation VALUES (57, 'UPDATE_ONE_ROLE', '/[0-9]*', 'PUT', false, 10);
INSERT INTO public.operation VALUES (58, 'DELETE_ONE_ROLE', '/[0-9]*', 'DELETE', false, 10);
INSERT INTO public.operation VALUES (59, 'FIND_ONE_ROLE', '/[0-9]*', 'GET', false, 10);
INSERT INTO public.operation VALUES (60, 'FIND_ONE_DETAIL_ROLE', '/[0-9]*/detail', 'GET', false, 10);
INSERT INTO public.operation VALUES (61, 'FIND_ALL_PAGE_ROLE', '/search', 'GET', false, 10);
INSERT INTO public.operation VALUES (62, 'FIND_ALL_ROLE', '', 'GET', false, 10);
INSERT INTO public.operation VALUES (39, 'GET_CACHE_STATS', '/stats', 'GET', false, 7);
INSERT INTO public.operation VALUES (63, 'GET_RATE_LIMIT_STATS', '/stats', 'GET', false, 11);
INSERT INTO public.operation VALUES (64, 'RESET_IP_RATE_LIMIT', '/reset/[0-9]*', 'POST', false, 11);
INSERT INTO public.operation VALUES (65, 'CLEAN_UP_RATE_LIMIT', '/cleanup', 'POST', false, 11);
INSERT INTO public.operation VALUES (66, 'FIND_PERMISSIONS_BY_CURRENT_USER', '/user/permissions', 'GET', false, 12);
INSERT INTO public.operation VALUES (68, 'UPDATE_ONE_MANAGER', '/[0-9]*', 'PUT', false, 14);
INSERT INTO public.operation VALUES (69, 'FIND_ONE_MANAGER', '/[0-9]*', 'GET', false, 14);
INSERT INTO public.operation VALUES (70, 'CREATE_ONE_MANAGER', '', 'POST', false, 14);
INSERT INTO public.operation VALUES (71, 'DELETE_ONE_MANAGER', '/[0-9]*', 'DELETE', false, 14);
INSERT INTO public.operation VALUES (72, 'FIND_ALL_PAGE_MANAGERS', '/search', 'GET', false, 14);
INSERT INTO public.operation VALUES (73, 'FIND_ONE_DETAIL_MANAGER', '/[0-9]*/detail', 'GET', false, 14);
INSERT INTO public.operation VALUES (74, 'UPDATE_STATUS_ONE_MANAGER', '/[0-9]+/status/(true|false)', 'PUT', false, 14);
INSERT INTO public.operation VALUES (75, 'CREATE_ONE_VIEWER', '', 'POST', false, 15);
INSERT INTO public.operation VALUES (76, 'DELETE_ONE_VIEWER', '/[0-9]*', 'DELETE', false, 15);
INSERT INTO public.operation VALUES (77, 'UPDATE_ONE_VIEWER', '/[0-9]*', 'PUT', false, 15);
INSERT INTO public.operation VALUES (78, 'UPDATE_STATUS_ONE_VIEWER', '/[0-9]+/status/(true|false)', 'PATCH', false, 15);
INSERT INTO public.operation VALUES (79, 'FIND_ONE_VIEWER', '/[0-9]*', 'GET', false, 15);
INSERT INTO public.operation VALUES (80, 'FIND_ONE_DETAIL_VIEWER', '/[0-9]*/detail', 'GET', false, 15);
INSERT INTO public.operation VALUES (81, 'FIND_ALL_PAGE_VIEWER', '/search', 'GET', false, 15);
INSERT INTO public.operation VALUES (27, 'UPDATE_STATUS_ONE_EDITOR', '/[0-9]+/status/(true|false)', 'PUT', false, 5);
INSERT INTO public.operation VALUES (31, 'FIND_ALL_PAGE_EDITORS', '/search', 'GET', false, 5);
INSERT INTO public.operation VALUES (30, 'FIND_ONE_DETAIL_EDITOR', '/[0-9]*/detail', 'GET', false, 5);
INSERT INTO public.operation VALUES (67, 'SHOW_USER_PROFILE', '/[0-9]*/profile', 'GET', false, 13);


--
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.permission VALUES (1, 4, 1);
INSERT INTO public.permission VALUES (2, 4, 2);
INSERT INTO public.permission VALUES (3, 4, 3);
INSERT INTO public.permission VALUES (4, 4, 4);
INSERT INTO public.permission VALUES (5, 4, 5);
INSERT INTO public.permission VALUES (6, 4, 6);
INSERT INTO public.permission VALUES (7, 4, 7);
INSERT INTO public.permission VALUES (8, 4, 8);
INSERT INTO public.permission VALUES (9, 4, 10);
INSERT INTO public.permission VALUES (10, 4, 11);
INSERT INTO public.permission VALUES (11, 4, 12);
INSERT INTO public.permission VALUES (12, 4, 13);
INSERT INTO public.permission VALUES (13, 4, 14);
INSERT INTO public.permission VALUES (14, 4, 15);
INSERT INTO public.permission VALUES (15, 4, 16);
INSERT INTO public.permission VALUES (16, 4, 17);
INSERT INTO public.permission VALUES (17, 4, 18);
INSERT INTO public.permission VALUES (18, 4, 19);
INSERT INTO public.permission VALUES (19, 4, 20);
INSERT INTO public.permission VALUES (20, 4, 21);
INSERT INTO public.permission VALUES (21, 4, 22);
INSERT INTO public.permission VALUES (22, 4, 23);
INSERT INTO public.permission VALUES (23, 4, 24);
INSERT INTO public.permission VALUES (24, 4, 25);
INSERT INTO public.permission VALUES (25, 4, 26);
INSERT INTO public.permission VALUES (26, 4, 27);
INSERT INTO public.permission VALUES (27, 4, 28);
INSERT INTO public.permission VALUES (28, 4, 29);
INSERT INTO public.permission VALUES (29, 4, 30);
INSERT INTO public.permission VALUES (30, 4, 31);
INSERT INTO public.permission VALUES (31, 4, 32);
INSERT INTO public.permission VALUES (32, 4, 33);
INSERT INTO public.permission VALUES (33, 4, 34);
INSERT INTO public.permission VALUES (34, 4, 35);
INSERT INTO public.permission VALUES (35, 4, 36);
INSERT INTO public.permission VALUES (36, 4, 37);
INSERT INTO public.permission VALUES (37, 4, 38);
INSERT INTO public.permission VALUES (38, 4, 39);
INSERT INTO public.permission VALUES (39, 4, 40);
INSERT INTO public.permission VALUES (40, 4, 41);
INSERT INTO public.permission VALUES (41, 4, 42);
INSERT INTO public.permission VALUES (42, 4, 43);
INSERT INTO public.permission VALUES (43, 4, 44);
INSERT INTO public.permission VALUES (44, 4, 45);
INSERT INTO public.permission VALUES (45, 4, 46);
INSERT INTO public.permission VALUES (46, 4, 47);
INSERT INTO public.permission VALUES (47, 4, 48);
INSERT INTO public.permission VALUES (48, 4, 49);
INSERT INTO public.permission VALUES (49, 4, 50);
INSERT INTO public.permission VALUES (50, 4, 51);
INSERT INTO public.permission VALUES (51, 4, 52);
INSERT INTO public.permission VALUES (52, 4, 53);
INSERT INTO public.permission VALUES (53, 4, 54);
INSERT INTO public.permission VALUES (54, 4, 55);
INSERT INTO public.permission VALUES (55, 4, 56);
INSERT INTO public.permission VALUES (56, 4, 57);
INSERT INTO public.permission VALUES (57, 4, 58);
INSERT INTO public.permission VALUES (58, 4, 59);
INSERT INTO public.permission VALUES (59, 4, 60);
INSERT INTO public.permission VALUES (60, 4, 61);
INSERT INTO public.permission VALUES (61, 4, 62);
INSERT INTO public.permission VALUES (62, 4, 63);
INSERT INTO public.permission VALUES (63, 4, 64);
INSERT INTO public.permission VALUES (64, 4, 65);
INSERT INTO public.permission VALUES (65, 4, 66);
INSERT INTO public.permission VALUES (66, 4, 67);
INSERT INTO public.permission VALUES (67, 4, 68);
INSERT INTO public.permission VALUES (68, 4, 69);
INSERT INTO public.permission VALUES (69, 4, 70);
INSERT INTO public.permission VALUES (70, 4, 71);
INSERT INTO public.permission VALUES (71, 4, 72);
INSERT INTO public.permission VALUES (72, 4, 73);
INSERT INTO public.permission VALUES (73, 4, 74);
INSERT INTO public.permission VALUES (74, 4, 75);
INSERT INTO public.permission VALUES (75, 4, 76);
INSERT INTO public.permission VALUES (76, 4, 77);
INSERT INTO public.permission VALUES (77, 4, 78);
INSERT INTO public.permission VALUES (78, 4, 79);
INSERT INTO public.permission VALUES (79, 4, 80);
INSERT INTO public.permission VALUES (80, 4, 81);
INSERT INTO public.permission VALUES (81, 1, 1);
INSERT INTO public.permission VALUES (82, 1, 2);
INSERT INTO public.permission VALUES (83, 1, 3);
INSERT INTO public.permission VALUES (84, 1, 4);
INSERT INTO public.permission VALUES (85, 1, 5);
INSERT INTO public.permission VALUES (86, 1, 6);
INSERT INTO public.permission VALUES (87, 1, 7);
INSERT INTO public.permission VALUES (88, 1, 8);
INSERT INTO public.permission VALUES (90, 1, 10);
INSERT INTO public.permission VALUES (91, 1, 11);
INSERT INTO public.permission VALUES (92, 1, 25);
INSERT INTO public.permission VALUES (93, 1, 26);
INSERT INTO public.permission VALUES (94, 1, 27);
INSERT INTO public.permission VALUES (95, 1, 28);
INSERT INTO public.permission VALUES (96, 1, 29);
INSERT INTO public.permission VALUES (97, 1, 30);
INSERT INTO public.permission VALUES (98, 1, 31);
INSERT INTO public.permission VALUES (99, 1, 32);
INSERT INTO public.permission VALUES (100, 1, 33);
INSERT INTO public.permission VALUES (101, 1, 34);
INSERT INTO public.permission VALUES (102, 1, 35);
INSERT INTO public.permission VALUES (103, 1, 36);
INSERT INTO public.permission VALUES (104, 1, 37);
INSERT INTO public.permission VALUES (105, 1, 38);
INSERT INTO public.permission VALUES (106, 1, 39);
INSERT INTO public.permission VALUES (107, 1, 63);
INSERT INTO public.permission VALUES (108, 1, 64);
INSERT INTO public.permission VALUES (109, 1, 65);
INSERT INTO public.permission VALUES (110, 1, 66);
INSERT INTO public.permission VALUES (111, 1, 67);
INSERT INTO public.permission VALUES (112, 1, 68);
INSERT INTO public.permission VALUES (113, 1, 69);
INSERT INTO public.permission VALUES (114, 1, 70);
INSERT INTO public.permission VALUES (115, 1, 71);
INSERT INTO public.permission VALUES (116, 1, 72);
INSERT INTO public.permission VALUES (117, 1, 73);
INSERT INTO public.permission VALUES (118, 1, 74);
INSERT INTO public.permission VALUES (119, 1, 75);
INSERT INTO public.permission VALUES (120, 1, 76);
INSERT INTO public.permission VALUES (121, 1, 77);
INSERT INTO public.permission VALUES (122, 1, 78);
INSERT INTO public.permission VALUES (123, 1, 79);
INSERT INTO public.permission VALUES (124, 1, 80);
INSERT INTO public.permission VALUES (125, 1, 81);
INSERT INTO public.permission VALUES (126, 2, 8);
INSERT INTO public.permission VALUES (127, 2, 10);
INSERT INTO public.permission VALUES (128, 2, 11);
INSERT INTO public.permission VALUES (129, 2, 25);
INSERT INTO public.permission VALUES (130, 2, 26);
INSERT INTO public.permission VALUES (131, 2, 27);
INSERT INTO public.permission VALUES (132, 2, 28);
INSERT INTO public.permission VALUES (133, 2, 29);
INSERT INTO public.permission VALUES (134, 2, 30);
INSERT INTO public.permission VALUES (135, 2, 31);
INSERT INTO public.permission VALUES (136, 2, 32);
INSERT INTO public.permission VALUES (137, 2, 33);
INSERT INTO public.permission VALUES (138, 2, 34);
INSERT INTO public.permission VALUES (139, 2, 35);
INSERT INTO public.permission VALUES (140, 2, 36);
INSERT INTO public.permission VALUES (141, 2, 37);
INSERT INTO public.permission VALUES (142, 2, 38);
INSERT INTO public.permission VALUES (143, 2, 66);
INSERT INTO public.permission VALUES (144, 2, 67);
INSERT INTO public.permission VALUES (145, 2, 72);
INSERT INTO public.permission VALUES (146, 2, 73);
INSERT INTO public.permission VALUES (147, 2, 75);
INSERT INTO public.permission VALUES (148, 2, 76);
INSERT INTO public.permission VALUES (149, 2, 77);
INSERT INTO public.permission VALUES (150, 2, 78);
INSERT INTO public.permission VALUES (151, 2, 79);
INSERT INTO public.permission VALUES (152, 2, 80);
INSERT INTO public.permission VALUES (153, 2, 81);
INSERT INTO public.permission VALUES (154, 3, 8);
INSERT INTO public.permission VALUES (155, 3, 10);
INSERT INTO public.permission VALUES (156, 3, 11);
INSERT INTO public.permission VALUES (157, 3, 29);
INSERT INTO public.permission VALUES (158, 3, 30);
INSERT INTO public.permission VALUES (159, 3, 31);
INSERT INTO public.permission VALUES (160, 3, 32);
INSERT INTO public.permission VALUES (161, 3, 33);
INSERT INTO public.permission VALUES (162, 3, 34);
INSERT INTO public.permission VALUES (163, 3, 35);
INSERT INTO public.permission VALUES (164, 3, 36);
INSERT INTO public.permission VALUES (165, 3, 37);
INSERT INTO public.permission VALUES (166, 3, 38);
INSERT INTO public.permission VALUES (167, 3, 66);
INSERT INTO public.permission VALUES (168, 3, 67);
INSERT INTO public.permission VALUES (169, 3, 75);
INSERT INTO public.permission VALUES (170, 3, 76);
INSERT INTO public.permission VALUES (171, 3, 77);
INSERT INTO public.permission VALUES (172, 3, 78);
INSERT INTO public.permission VALUES (173, 3, 79);
INSERT INTO public.permission VALUES (174, 3, 80);
INSERT INTO public.permission VALUES (175, 3, 81);
INSERT INTO public.permission VALUES (176, 5, 8);
INSERT INTO public.permission VALUES (177, 5, 10);
INSERT INTO public.permission VALUES (178, 5, 11);
INSERT INTO public.permission VALUES (179, 5, 36);
INSERT INTO public.permission VALUES (180, 5, 37);
INSERT INTO public.permission VALUES (181, 5, 38);
INSERT INTO public.permission VALUES (182, 5, 66);
INSERT INTO public.permission VALUES (183, 5, 67);
INSERT INTO public.permission VALUES (184, 5, 79);
INSERT INTO public.permission VALUES (185, 5, 80);
INSERT INTO public.permission VALUES (186, 5, 81);
INSERT INTO public.permission VALUES (187, 6, 8);
INSERT INTO public.permission VALUES (188, 6, 10);
INSERT INTO public.permission VALUES (189, 6, 11);
INSERT INTO public.permission VALUES (190, 6, 36);
INSERT INTO public.permission VALUES (191, 6, 37);
INSERT INTO public.permission VALUES (192, 6, 38);
INSERT INTO public.permission VALUES (193, 6, 66);
INSERT INTO public.permission VALUES (194, 6, 67);
INSERT INTO public.permission VALUES (195, 6, 79);
INSERT INTO public.permission VALUES (196, 6, 80);
INSERT INTO public.permission VALUES (197, 6, 81);


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public.role VALUES (4, 'DEVELOPER');
INSERT INTO public.role VALUES (3, 'EDITOR');
INSERT INTO public.role VALUES (1, 'SYSTEM_ADMIN');
INSERT INTO public.role VALUES (2, 'MANAGER');
INSERT INTO public.role VALUES (5, 'VIEWER');
INSERT INTO public.role VALUES (6, 'GUEST');


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: spacecodee
--

INSERT INTO public."user" VALUES (1, 'spacecodee', '$2a$10$4KDJk1Y5cSrwSEqN7bTxX.Lzm7p9w9nB35GuZ57QWq4Ulzh6v7rxC', 'spacecodee', 'spacecodee', 4, 'spacecodee@spacecodee.com', '965412876', true, 'https://picsum.photos/200', NULL, NULL);
INSERT INTO public."user" VALUES (2, 'dev1code', '$2a$10$5TLQH08u4XC4uwE3F7qYouH9CkhRAr49iQBtBSV/zURG2OLUZxzMC', 'John', 'Smith', 4, 'john.smith@example.com', '912345678', true, 'https://picsum.photos/200', '2025-02-14 17:00:28.855232', NULL);
INSERT INTO public."user" VALUES (3, 'mariadev', '$2a$10$w61gIw8HystHnsvCcM7o4uVDlCu5NErduT0gzwnwE.vljgmBH74na', 'Maria', 'Garcia', 4, 'maria.garcia@example.com', '923456789', true, 'https://images.pexels.com/photos/848573/pexels-photo-848573.jpeg', '2025-02-14 17:02:46.439409', NULL);
INSERT INTO public."user" VALUES (4, 'sarah_code', '$2a$10$8ARvMguntopNxnYBnI6EcO0h/83I0AWgf.cCmW4JscCpsiFlmZes6', 'Sarah', 'Johnson', 4, 'sarah.j@techdev.com', '912345678', true, 'https://images.pexels.com/photos/848573/pexels-photo-848573.jpeg', '2025-02-14 21:32:35.841225', NULL);
INSERT INTO public."user" VALUES (5, 'admin_tech', '$2a$10$GINx5F1kpC0J2rdK0hJu.OTtSUkYejJgTDHveZdLRb/M/etlukDU6', 'James', 'Cooper', 1, 'james.c@adminsys.com', '945678901', true, 'https://images.pexels.com/photos/29604465/pexels-photo-29604465/free-photo-of-urban-fishing-by-the-river-under-bridge-skyscrapers.jpeg', '2025-02-14 21:36:34.169716', '2025-02-14 21:51:29.070504');
INSERT INTO public."user" VALUES (6, 'root_admin', '$2a$10$XT2Yf/s4IPoJo3pPsAtC3OKuZcpxFPVm0rxgeYzVRvxvKnF1P5hI.', 'Lisa Andrea', 'Anderson', 1, 'lisa.admin@systech.com', '934567890', true, 'https://images.pexels.com/photos/789303/pexels-photo-789303.jpeg', '2025-02-14 21:52:45.299788', '2025-02-14 21:54:59.967906');


--
-- Name: jwt_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.jwt_token_id_seq', 1, false);


--
-- Name: module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.module_id_seq', 16, false);


--
-- Name: operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.operation_id_seq', 81, true);


--
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.permission_id_seq', 197, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.role_id_seq', 7, false);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: spacecodee
--

SELECT pg_catalog.setval('public.user_id_seq', 6, true);


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
    ADD CONSTRAINT jwt_token_user_id_fk FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- Name: operation operation_module_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.operation
    ADD CONSTRAINT operation_module_id_fk FOREIGN KEY (module_id) REFERENCES public.module(id);


--
-- Name: permission permission_operation_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_operation_id_fk FOREIGN KEY (operation_id) REFERENCES public.operation(id);


--
-- Name: permission permission_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_role_id_fk FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: user user_role_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: spacecodee
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_role_role_id_fk FOREIGN KEY (rol_id) REFERENCES public.role(id);


--
-- PostgreSQL database dump complete
--

