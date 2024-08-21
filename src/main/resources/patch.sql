ALTER TABLE product
ALTER COLUMN images TYPE text[] USING images::text[];

ALTER TABLE product
ALTER COLUMN keywords TYPE text[] USING images::text[];
