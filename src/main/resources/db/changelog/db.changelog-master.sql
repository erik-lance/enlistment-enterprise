-- liquibase formatted sql

-- changeset erik:1679234138717-1
CREATE TABLE "public"."admin" ("id" INTEGER NOT NULL, "firstname" TEXT, "lastname" TEXT, CONSTRAINT "admin_pkey" PRIMARY KEY ("id"));

-- changeset erik:1679234138717-2
CREATE TABLE "public"."room" ("name" TEXT NOT NULL, "capacity" INTEGER NOT NULL, CONSTRAINT "room_pkey" PRIMARY KEY ("name"));

-- changeset erik:1679234138717-3
CREATE TABLE "public"."room_sections" ("room_name" TEXT NOT NULL, "sections_section_id" TEXT NOT NULL);

-- changeset erik:1679234138717-4
CREATE TABLE "public"."section" ("section_id" TEXT NOT NULL, "number_of_students" INTEGER NOT NULL, "days" INTEGER, "end_time" time(6) WITHOUT TIME ZONE, "start_time" time(6) WITHOUT TIME ZONE, "version" INTEGER DEFAULT 0 NOT NULL, "room_name" TEXT, "subject_subject_id" TEXT, CONSTRAINT "section_pkey" PRIMARY KEY ("section_id"));

-- changeset erik:1679234138717-5
CREATE TABLE "public"."student" ("student_number" INTEGER NOT NULL, "firstname" TEXT, "lastname" TEXT, CONSTRAINT "student_pkey" PRIMARY KEY ("student_number"));

-- changeset erik:1679234138717-6
CREATE TABLE "public"."student_sections" ("student_student_number" INTEGER NOT NULL, "sections_section_id" TEXT NOT NULL);

-- changeset erik:1679234138717-7
CREATE TABLE "public"."student_subjects_taken" ("student_student_number" INTEGER NOT NULL, "subjects_taken_subject_id" TEXT NOT NULL);

-- changeset erik:1679234138717-8
CREATE TABLE "public"."subject" ("subject_id" TEXT NOT NULL, CONSTRAINT "subject_pkey" PRIMARY KEY ("subject_id"));

-- changeset erik:1679234138717-9
CREATE TABLE "public"."subject_prerequisites" ("subject_subject_id" TEXT NOT NULL, "prerequisites_subject_id" TEXT NOT NULL);

-- changeset erik:1679234138717-10
ALTER TABLE "public"."room_sections" ADD CONSTRAINT "uk_4y9hvexlvcosyoi6sxolp9if2" UNIQUE ("sections_section_id");

-- changeset erik:1679234138717-11
ALTER TABLE "public"."room_sections" ADD CONSTRAINT "fk18tmp1kdjwlixo8tcyag4xnv" FOREIGN KEY ("sections_section_id") REFERENCES "public"."section" ("section_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-12
ALTER TABLE "public"."subject_prerequisites" ADD CONSTRAINT "fk6ta30r0qhlti2fsjg762emtwb" FOREIGN KEY ("prerequisites_subject_id") REFERENCES "public"."subject" ("subject_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-13
ALTER TABLE "public"."student_sections" ADD CONSTRAINT "fk89rtc8dmim7fd4lulgkecplhj" FOREIGN KEY ("sections_section_id") REFERENCES "public"."section" ("section_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-14
ALTER TABLE "public"."section" ADD CONSTRAINT "fkb7vxfqjj8qa0r38kh4qs1eac1" FOREIGN KEY ("room_name") REFERENCES "public"."room" ("name") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-15
ALTER TABLE "public"."student_sections" ADD CONSTRAINT "fkcbuj7ic505urpqreop2xuwstg" FOREIGN KEY ("student_student_number") REFERENCES "public"."student" ("student_number") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-16
ALTER TABLE "public"."room_sections" ADD CONSTRAINT "fkfd4bv684oyhmv5ntq0gll0exl" FOREIGN KEY ("room_name") REFERENCES "public"."room" ("name") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-17
ALTER TABLE "public"."student_subjects_taken" ADD CONSTRAINT "fkitrqnu6k56yf07ntuo8q1rast" FOREIGN KEY ("subjects_taken_subject_id") REFERENCES "public"."subject" ("subject_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-18
ALTER TABLE "public"."section" ADD CONSTRAINT "fkn23lvqkfvxq9wo0w6qt9xgomd" FOREIGN KEY ("subject_subject_id") REFERENCES "public"."subject" ("subject_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-19
ALTER TABLE "public"."subject_prerequisites" ADD CONSTRAINT "fkogx9mcr8od8y3uf7cxb0spqed" FOREIGN KEY ("subject_subject_id") REFERENCES "public"."subject" ("subject_id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset erik:1679234138717-20
--validCheckSum: any
ALTER TABLE "public"."student_subjects_taken" ADD CONSTRAINT "fkr7ky8jeghxfcdqfugflneppgv" FOREIGN KEY ("student_student_number") REFERENCES "public"."student" ("student_number") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- liquibase formatted sql

-- changeset erik:1679244450727-1
CREATE TABLE public.faculty (faculty_number INTEGER NOT NULL, CONSTRAINT "facultyPK" PRIMARY KEY (faculty_number));

-- changeset erik:1679244450727-2
ALTER TABLE public.section ADD instructor_faculty_number INTEGER;

-- changeset erik:1679244450727-3
--validCheckSum: any
ALTER TABLE public.section ADD CONSTRAINT "FKhbrcxeiyot9gwvpjd9klgc0rw" FOREIGN KEY (instructor_faculty_number) REFERENCES public.faculty (faculty_number);

-- liquibase formatted sql

-- changeset gianm:1679492914670-2
CREATE TABLE public.faculty (faculty_number INTEGER NOT NULL, CONSTRAINT "facultyPK" PRIMARY KEY (faculty_number));

-- changeset gianm:1679492914670-3
CREATE TABLE public.faculty_sections (faculty_faculty_number INTEGER NOT NULL, sections_section_id VARCHAR(255) NOT NULL);
-- changeset gianm:1679492914670-12
ALTER TABLE public.faculty_sections ADD CONSTRAINT "UK_2ce4o3g0a4rl929cbewtmmu1y" UNIQUE (sections_section_id);

-- changeset gianm:1679492914670-15
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK2j11yqgbi4wdcyov12kgr5xy0" FOREIGN KEY (sections_section_id) REFERENCES public.section (section_id);

-- changeset gianm:1679492914670-16
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK656uje4ggsa8h9sqf9yfji4n" FOREIGN KEY (faculty_faculty_number) REFERENCES public.faculty (faculty_number);
-- liquibase formatted sql

-- changeset Sam:1680094544811-2
--validCheckSum: any
CREATE TABLE public.faculty (faculty_number INTEGER NOT NULL, firstname VARCHAR(255), lastname VARCHAR(255), CONSTRAINT "facultyPK" PRIMARY KEY (faculty_number));

-- changeset Sam:1680094544811-12
ALTER TABLE public.faculty_sections ADD CONSTRAINT "UK_2ce4o3g0a4rl929cbewtmmu1y" UNIQUE (sections_section_id);

-- changeset Sam:1680094544811-15
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK2j11yqgbi4wdcyov12kgr5xy0" FOREIGN KEY (sections_section_id) REFERENCES public.section (section_id);

-- changeset Sam:1680094544811-16
--validCheckSum: any
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK656uje4ggsa8h9sqf9yfji4n" FOREIGN KEY (faculty_faculty_number) REFERENCES public.faculty (faculty_number);

-- liquibase formatted sql

-- changeset erik:1680210372892-1
CREATE TABLE public.faculty_sections (faculty_faculty_number INTEGER NOT NULL, sections_section_id VARCHAR(255) NOT NULL);

-- changeset erik:1680210372892-2
ALTER TABLE public.faculty ADD firstname VARCHAR(255);

-- changeset erik:1680210372892-3
ALTER TABLE public.faculty ADD lastname VARCHAR(255);

-- changeset erik:1680210372892-4
ALTER TABLE public.faculty_sections ADD CONSTRAINT "UK_2ce4o3g0a4rl929cbewtmmu1y" UNIQUE (sections_section_id);

-- changeset erik:1680210372892-5
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK2j11yqgbi4wdcyov12kgr5xy0" FOREIGN KEY (sections_section_id) REFERENCES public.section (section_id);

-- changeset erik:1680210372892-6
ALTER TABLE public.faculty_sections ADD CONSTRAINT "FK656uje4ggsa8h9sqf9yfji4n" FOREIGN KEY (faculty_faculty_number) REFERENCES public.faculty (faculty_number);

