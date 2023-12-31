PGDMP     9                    {            tdlist    15.3    15.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16397    tdlist    DATABASE     z   CREATE DATABASE tdlist WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_Turkey.1254';
    DROP DATABASE tdlist;
                postgres    false            �            1259    16408    titles    TABLE     H   CREATE TABLE public.titles (
    id integer NOT NULL,
    title text
);
    DROP TABLE public.titles;
       public         heap    postgres    false            �            1259    16407    header_id_seq    SEQUENCE     �   CREATE SEQUENCE public.header_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.header_id_seq;
       public          postgres    false    217                       0    0    header_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.header_id_seq OWNED BY public.titles.id;
          public          postgres    false    216            �            1259    16399    todo    TABLE     _   CREATE TABLE public.todo (
    taskid integer NOT NULL,
    task text,
    title_id integer
);
    DROP TABLE public.todo;
       public         heap    postgres    false            �            1259    16398    todo_id_seq    SEQUENCE     �   CREATE SEQUENCE public.todo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.todo_id_seq;
       public          postgres    false    215            	           0    0    todo_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.todo_id_seq OWNED BY public.todo.taskid;
          public          postgres    false    214            k           2604    16411 	   titles id    DEFAULT     f   ALTER TABLE ONLY public.titles ALTER COLUMN id SET DEFAULT nextval('public.header_id_seq'::regclass);
 8   ALTER TABLE public.titles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            j           2604    16402    todo taskid    DEFAULT     f   ALTER TABLE ONLY public.todo ALTER COLUMN taskid SET DEFAULT nextval('public.todo_id_seq'::regclass);
 :   ALTER TABLE public.todo ALTER COLUMN taskid DROP DEFAULT;
       public          postgres    false    214    215    215                      0    16408    titles 
   TABLE DATA           +   COPY public.titles (id, title) FROM stdin;
    public          postgres    false    217   �       �          0    16399    todo 
   TABLE DATA           6   COPY public.todo (taskid, task, title_id) FROM stdin;
    public          postgres    false    215          
           0    0    header_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.header_id_seq', 12, true);
          public          postgres    false    216                       0    0    todo_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.todo_id_seq', 7, true);
          public          postgres    false    214            o           2606    16415    titles header_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.titles
    ADD CONSTRAINT header_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.titles DROP CONSTRAINT header_pkey;
       public            postgres    false    217            m           2606    16406    todo todo_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.todo
    ADD CONSTRAINT todo_pkey PRIMARY KEY (taskid);
 8   ALTER TABLE ONLY public.todo DROP CONSTRAINT todo_pkey;
       public            postgres    false    215                  x������ � �      �      x������ � �     