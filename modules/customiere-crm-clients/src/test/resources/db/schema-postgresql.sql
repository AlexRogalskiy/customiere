-- Table: public.devices

-- DROP TABLE public.devices;

CREATE TABLE public.devices
(
    id uuid NOT NULL,
    created_by character varying(512) COLLATE pg_catalog."default" NOT NULL,
    created timestamp without time zone NOT NULL DEFAULT now(),
    modified_by character varying(512) COLLATE pg_catalog."default",
    modified timestamp without time zone,
    row_version bigint NOT NULL DEFAULT 0,
    company character varying(255) COLLATE pg_catalog."default" NOT NULL,
    partner character varying(255) COLLATE pg_catalog."default" NOT NULL,
    product character varying(255) COLLATE pg_catalog."default" NOT NULL,
    amount integer NOT NULL,
    code numeric NOT NULL,
    data text COLLATE pg_catalog."default" NOT NULL,
    status status_enum NOT NULL DEFAULT 'NEW'::status_enum,
    CONSTRAINT devices_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.devices
    OWNER to postgres;
-- Index: company_idx

-- DROP INDEX public.company_idx;

CREATE INDEX company_idx
    ON public.devices USING btree
    (company COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: partner_idx

-- DROP INDEX public.partner_idx;

CREATE INDEX partner_idx
    ON public.devices USING btree
    (partner COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
