--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

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
-- Name: Activities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Activities" (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    type character varying NOT NULL
);


ALTER TABLE public."Activities" OWNER TO postgres;

--
-- Name: competition; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.competition (
    id uuid NOT NULL,
    name character varying NOT NULL,
    society_id uuid NOT NULL,
    description text,
    about text,
    logo text,
    cover text,
    date date,
    "time" time without time zone,
    rule_book text,
    activity_id uuid
);


ALTER TABLE public.competition OWNER TO postgres;

--
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event (
    id uuid NOT NULL,
    name character varying NOT NULL,
    society_id uuid NOT NULL,
    description text,
    about text,
    logo text,
    cover text,
    date date,
    "time" time without time zone,
    activity_id uuid
);


ALTER TABLE public.event OWNER TO postgres;

--
-- Name: organizer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizer (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    role character varying NOT NULL
);


ALTER TABLE public.organizer OWNER TO postgres;

--
-- Name: organizer_society; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizer_society (
    organizer_id uuid NOT NULL,
    society_id uuid NOT NULL
);


ALTER TABLE public.organizer_society OWNER TO postgres;

--
-- Name: organizer_team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizer_team (
    organizer_id uuid NOT NULL,
    team_id uuid NOT NULL
);


ALTER TABLE public.organizer_team OWNER TO postgres;

--
-- Name: participant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participant (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    full_name character varying NOT NULL,
    email character varying NOT NULL,
    phone character varying NOT NULL,
    student boolean NOT NULL,
    organization character varying
);


ALTER TABLE public.participant OWNER TO postgres;

--
-- Name: participant_Activities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."participant_Activities" (
    participant_id uuid NOT NULL,
    activity_id uuid
);


ALTER TABLE public."participant_Activities" OWNER TO postgres;

--
-- Name: society; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.society (
    id uuid NOT NULL,
    name character varying NOT NULL,
    description text,
    about text,
    logo text,
    cover text
);


ALTER TABLE public.society OWNER TO postgres;

--
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id uuid NOT NULL,
    society_id uuid NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.team OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id uuid NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    oauth character varying,
    access_token character varying,
    refresh_token character varying,
    profilepic text,
    role text
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Data for Name: Activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Activities" (id, name, type) FROM stdin;
\.


--
-- Data for Name: competition; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.competition (id, name, society_id, description, about, logo, cover, date, "time", rule_book, activity_id) FROM stdin;
\.


--
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event (id, name, society_id, description, about, logo, cover, date, "time", activity_id) FROM stdin;
\.


--
-- Data for Name: organizer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizer (id, user_id, created_at, updated_at, role) FROM stdin;
1b12f0e3-93d7-46aa-a865-18e9a3827c9d	ee8997a8-fbb6-404e-bf7a-4f9f5fa0721b	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
71391e90-c61d-4b27-a815-311a01005688	0ebe20e6-c658-41f4-91fb-8a5fa8e66dc5	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
0a47a783-ebb3-4d31-a365-402595de308a	33e6e1d9-af96-45a8-9895-3b7fcc67ad57	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
e1177f3a-a008-4067-82e0-1ccddf35b58e	cb2e92cc-4ed0-42be-9ce7-3123350bcda2	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
f893e137-e705-4e32-9680-123f9b648716	2ec516b0-8f39-4150-9d09-8b016dbbd09f	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
c8506770-7c38-4494-b6f9-c19a652fb653	7e83e592-f3d6-443f-bd07-37e450c9f4a6	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
a62b2d05-f8d0-431c-accf-9ab3d6c4e53a	d23e0ed2-73df-41b6-8866-f21bd39b6410	2025-02-24 00:00:00+05	2025-02-24 00:00:00+05	excom
90c6ac42-c9b7-42e8-92ff-71881055238e	9a297c26-f254-45f0-84ba-6ca812623fda	2025-02-25 00:00:00+05	2025-02-25 00:00:00+05	excom
\.


--
-- Data for Name: organizer_society; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizer_society (organizer_id, society_id) FROM stdin;
a62b2d05-f8d0-431c-accf-9ab3d6c4e53a	b6a39983-ff1f-424a-8065-9ef0b2462563
90c6ac42-c9b7-42e8-92ff-71881055238e	b6a39983-ff1f-424a-8065-9ef0b2462563
\.


--
-- Data for Name: organizer_team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizer_team (organizer_id, team_id) FROM stdin;
\.


--
-- Data for Name: participant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participant (id, user_id, full_name, email, phone, student, organization) FROM stdin;
\.


--
-- Data for Name: participant_Activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."participant_Activities" (participant_id, activity_id) FROM stdin;
\.


--
-- Data for Name: society; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.society (id, name, description, about, logo, cover) FROM stdin;
b6a39983-ff1f-424a-8065-9ef0b2462563	Procom	A tech and programming society	Procom is a society focused on programming competitions, workshops, and tech-related events.	procom_logo.png	procom_cover.png
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, society_id, name) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, email, password, oauth, access_token, refresh_token, profilepic, role) FROM stdin;
d6753db0-2bd8-4f6c-8364-b8c56f1ee207	user3@gmaill.com	$2a$10$Cv0Z4MnIj/zg7kBDzVNI/OkephopLStJWPR9yEEKZwPi.ekstbV6S	\N	\N	\N	\N	participant
0135704b-962c-423a-9674-15c925e10dfe	org1@gmaill.com	$2a$10$khCIBXhtMK8g3EKYxWslJeTJJ7Rp3rbeZ9Vj0k47mnuZEqFEYfQ66	\N	\N	\N	\N	organizer
05a98ecc-40cc-4a28-beb6-fe294b551fd4	org2@gmaill.com	$2a$10$sgh.QwXPa/D65Ey9Fk5C7uQ9KCfvzB3RTXfkjQ2yVxJiIVGImAbaO	\N	\N	\N	\N	organizer
e0f89191-e503-4b28-ad7f-aa9cd229d6ed	org4@gmaill.com	$2a$10$wFZpK/vond0w9CLp..g.guzytRJe18kWrfVLD.ZTwGN9o7jawsuOm	\N	\N	\N	\N	organizer
5a984203-8179-4574-961a-2eed10a0e94e	org5@gmaill.com	$2a$10$Z4/G367sMV0p71WW1juAdeDUux5RpbYv/qr5tfnIbr8cE2OBqybeW	\N	\N	\N	\N	organizer
4ffbb4c2-a841-49f0-b9b9-f67788e27668	org6@gmaill.com	$2a$10$rpBqd47cYP/ttytQO6C9QetaVsvzM3JWTvGLvlHMbJ5b7UETRZTJq	\N	\N	\N	\N	organizer
3d9607ce-6a74-4d0a-87b7-7eaec3aae660	org12@gmaill.com	$2a$10$BRT491STF2GZ6dJyeL5Mp.jMCtmW1RchDiawWYAY7XfPebCSNdwtS	\N	\N	\N	\N	organizer
bdff592c-890a-42c1-a33d-652f522d6bcf	org13@gmaill.com	$2a$10$/xlPIzP2ZZqnZQEDvz1I1el0kKqcbl7gf6S6KGrAl8TfO8etMZl32	\N	\N	\N	\N	organizer
8d6d42d0-235a-4a43-b676-fd9841dc1c6d	org15@gmaill.com	$2a$10$WPQYFochkxY4QrsdXp1tVuKZukGBND/19cIfWnvq1JkDkFspa2GGa	\N	\N	\N	\N	organizer
ee8997a8-fbb6-404e-bf7a-4f9f5fa0721b	org16@gmaill.com	$2a$10$ledxBhOnzmFORcpHzjc9RuWSpzG9xrwAVqTN3CtRZMCzxWppxl7ti	\N	\N	\N	\N	organizer
e3c2c187-94e3-41f3-a2f8-3377cfcb1f7a	org18@gmaill.com	$2a$10$1jrAuyf5cB6SHdRhWbU6/OkFT6huAGvKTUr6eoP/zlak7ITn7avXK	\N	\N	\N	\N	organizer
fab464e5-ce60-4f13-ba53-afadcb578c1c	org118@gmaill.com	$2a$10$qj8xyyjDiBlzilSDupTqwOi/ELfflWMmIMs3Giada/mF955/yOVFe	\N	\N	\N	\N	organizer
a2643919-ede8-422c-966a-48c4200b26e1	org128@gmaill.com	$2a$10$DE7HPaDFluC.kp78lXEOWu.TKOs58nDhGH0Z0b7UiSS1Q24AO16nS	\N	\N	\N	\N	organizer
3f4d71f5-ca91-45a2-b492-d00fcc676b8e	org138@gmaill.com	$2a$10$5KVt3UINWKyJyJuW6FEKru5aVwT/rGJaCteX.supoj8p.9VMqz.H.	\N	\N	\N	\N	organizer
cfe32a0f-c45c-4d1e-b10c-50b6ad45ff29	org238@gmaill.com	$2a$10$Lvpi8xNbFxyndOUCHnHiD.WXPxYQyquYrWLYpxgXPEMCJNJcVQvV6	\N	\N	\N	\N	organizer
c579bba0-70d0-4155-b0c5-a544fa9074fb	org338@gmaill.com	$2a$10$aSAhxR1E1FWp7FGhJCD8uez3s/enweIRa/7AApOqn5oDuccy9SLku	\N	\N	\N	\N	organizer
f133d052-42db-42bd-8b41-2dc65c958d91	org334@gmaill.com	$2a$10$H27KAysjW44mHxgB8Wa8G.bWIC/zqlbuyZuXkgqkIQq7LpwQC.Ulm	\N	\N	\N	\N	organizer
0ebe20e6-c658-41f4-91fb-8a5fa8e66dc5	org335@gmaill.com	$2a$10$WNZBxkiCXwWaYluxG/q1ueVmB6kpprzuWSUOBQddyz0TAPi7pTvSq	\N	\N	\N	\N	organizer
33e6e1d9-af96-45a8-9895-3b7fcc67ad57	org535@gmaill.com	$2a$10$zxCqTk6XdMb8oJLRLCOomO9HZucDKCDLHxb3n8DTV/PX5iAq7o3O6	\N	\N	\N	\N	organizer
cb2e92cc-4ed0-42be-9ce7-3123350bcda2	org435@gmaill.com	$2a$10$4/M9dgyzjQ8pgJfUbjC9ye7CvAeCqqqt6NM0gwCF8k.pvjwgSLi.G	\N	\N	\N	\N	organizer
2ec516b0-8f39-4150-9d09-8b016dbbd09f	org4355@gmaill.com	$2a$10$5U5F3IbbH2bNaVD.u4XrnuxAJ8kN5PtYS5KPgIZGVHFdQp00BoTGu	\N	\N	\N	\N	organizer
7e83e592-f3d6-443f-bd07-37e450c9f4a6	org4395@gmaill.com	$2a$10$CkihLFaxeyZ/k/r/7atiGega7IP38ZJw./LC.FWBaaYGw39bHrDPG	\N	\N	\N	\N	organizer
d23e0ed2-73df-41b6-8866-f21bd39b6410	org43445@gmaill.com	$2a$10$ZflvBM880BRwYdaZQ95IMuzqF6FZhgQ4bTwkN6wLHjJ61BdLKOXvG	\N	\N	\N	\N	organizer
9a297c26-f254-45f0-84ba-6ca812623fda	hamza0032717@gmaill.com	$2a$10$aC4K3Eo40x9.DDhsSQglBepCCF5UOipn.Sjqc5btvwVZgnmNBWbau	\N	\N	\N	\N	organizer
4feea56f-1114-446b-bf8e-bd23de794f84	hamza@gmaill.com	$2a$10$x6tbU0zyDcYxyrsOHAh7ROKs3Gb8PthEQgoyRarX.QzwvvNT.ANP2	\N	\N	\N	\N	participant
\.


--
-- Name: Activities Activities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Activities"
    ADD CONSTRAINT "Activities_pkey" PRIMARY KEY (id);


--
-- Name: competition competition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.competition
    ADD CONSTRAINT competition_pkey PRIMARY KEY (id);


--
-- Name: event event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- Name: organizer organizer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizer
    ADD CONSTRAINT organizer_pkey PRIMARY KEY (id);


--
-- Name: participant participant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participant
    ADD CONSTRAINT participant_pkey PRIMARY KEY (id);


--
-- Name: society society_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.society
    ADD CONSTRAINT society_pkey PRIMARY KEY (id);


--
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: competition fk_competition_society; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.competition
    ADD CONSTRAINT fk_competition_society FOREIGN KEY (society_id) REFERENCES public.society(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: event fk_event_activity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT fk_event_activity FOREIGN KEY (activity_id) REFERENCES public."Activities"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: competition fk_event_activity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.competition
    ADD CONSTRAINT fk_event_activity FOREIGN KEY (activity_id) REFERENCES public."Activities"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: event fk_event_society; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT fk_event_society FOREIGN KEY (society_id) REFERENCES public.society(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: participant_Activities fk_participant_activities_activity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."participant_Activities"
    ADD CONSTRAINT fk_participant_activities_activity FOREIGN KEY (activity_id) REFERENCES public."Activities"(id);


--
-- Name: organizer organizer_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizer
    ADD CONSTRAINT organizer_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: participant_Activities participant_Activities_participant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."participant_Activities"
    ADD CONSTRAINT "participant_Activities_participant_id_fkey" FOREIGN KEY (participant_id) REFERENCES public.participant(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: participant participant_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participant
    ADD CONSTRAINT participant_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: team team_society_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_society_id_fkey FOREIGN KEY (society_id) REFERENCES public.society(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- PostgreSQL database dump complete
--

