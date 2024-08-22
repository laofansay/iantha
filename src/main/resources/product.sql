/*
 Navicat Premium Data Transfer

 Source Server         : mac
 Source Server Type    : PostgreSQL
 Source Server Version : 160001 (160001)
 Source Host           : localhost:5432
 Source Catalog        : shop
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160001 (160001)
 File Encoding         : 65001

 Date: 21/08/2024 18:07:08
*/


-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS "public"."product";
CREATE TABLE "public"."product" (
  "id" int8 NOT NULL,
  "title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "trans_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "images" text[] COLLATE "pg_catalog"."default",
  "keywords" text[] COLLATE "pg_catalog"."default",
  "metadata" text COLLATE "pg_catalog"."default",
  "guide_price" numeric(21,2) NOT NULL,
  "price" numeric(21,2) NOT NULL,
  "show_price" numeric(21,2) NOT NULL,
  "discount" int4 NOT NULL,
  "stock" int4 NOT NULL,
  "is_physical" bool NOT NULL,
  "is_available" bool NOT NULL,
  "is_featured" bool NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "sell_count" int4,
  "stock_count" int4,
  "shelves_status" bool,
  "shelves_date" timestamp(6),
  "created_at" timestamp(6) NOT NULL,
  "updated_at" timestamp(6) NOT NULL,
  "brand_id" int8,
  "categories_id" int8
)
;
ALTER TABLE "public"."product" OWNER TO "laofansayApi";
COMMENT ON COLUMN "public"."product"."trans_code" IS '商品编码';
COMMENT ON COLUMN "public"."product"."images" IS '默认图';
COMMENT ON COLUMN "public"."product"."guide_price" IS '成本价';
COMMENT ON COLUMN "public"."product"."price" IS '平台销售单价';
COMMENT ON COLUMN "public"."product"."show_price" IS '画线价';
COMMENT ON COLUMN "public"."product"."stock" IS '折扣';
COMMENT ON COLUMN "public"."product"."is_physical" IS '库存';
COMMENT ON COLUMN "public"."product"."is_available" IS '是否物理商品';
COMMENT ON COLUMN "public"."product"."is_featured" IS '是否可用';
COMMENT ON COLUMN "public"."product"."status" IS '是否物色';
COMMENT ON COLUMN "public"."product"."sell_count" IS '销量,此处简单维护,期望接入进入销存系统';
COMMENT ON COLUMN "public"."product"."stock_count" IS '库存,此处简单维护,期望接入进入销存系统';
COMMENT ON COLUMN "public"."product"."shelves_status" IS '上架状态';
COMMENT ON COLUMN "public"."product"."shelves_date" IS '上架时间';

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (1, 'before', 'green officially yuck', 'zowie not ah', '{https://lemanoosh.com/app/uploads/bkid-pipe-01.jpg}', '{pipe,brushed,wood}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 'f', 'f', 'PRE', 27177, 23057, 't', '2024-08-18 14:32:32', '2024-08-18 19:49:53', '2024-08-18 09:38:17', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (2, 'scarily apud', 'revitalisation', 'boot complex dimly', '{https://lemanoosh.com/app/uploads/BO_2019_A1_Natural_Brushed_05-768x1156.jpg}', '{speaker,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 't', 't', 'DOWN', 18834, 27161, 'f', '2024-08-18 13:01:37', '2024-08-18 14:39:29', '2024-08-18 17:13:14', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (3, 'inasmuch likewise but', 'lasting', 'per to', '{https://lemanoosh.com/app/uploads/gerhardt-kellermann-zeitmagazin-10.jpg}', '{music,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 't', 'f', 'DOWN', 26218, 6400, 'f', '2024-08-18 04:18:28', '2024-08-18 22:15:02', '2024-08-19 00:55:07', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (4, 'generally underpass', 'linguistics partially', 'peen', '{https://lemanoosh.com/app/uploads/plp-women-footwear-sneakers-04-07-768x1246.jpg}', '{shoes,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 'f', 'f', 'HOT', 27381, 29223, 't', '2024-08-18 11:30:17', '2024-08-18 13:56:20', '2024-08-18 03:58:24', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (5, 'manipulation cooked meanwhile', 'immediately', 'bah heavily separately', '{https://lemanoosh.com/app/uploads/0055-768x1023.jpg}', '{shoes,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 't', 'f', 'HOT', 15142, 9194, 't', '2024-08-18 16:58:53', '2024-08-18 19:13:48', '2024-08-18 04:40:25', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (6, 'content yearly cheerfully', 'thistle whose carpenter', 'or swiftly drat', '{https://lemanoosh.com/app/uploads/carl-hauser-0121-768x993.jpg}', '{shoes,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 'f', 't', 'HOT', 21503, 26240, 't', '2024-08-18 11:45:21', '2024-08-18 20:32:54', '2024-08-18 21:49:54', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (7, 'goldbrick', 'which aha', 'indeed', '{https://lemanoosh.com/app/uploads/carl-hauser-020-768x973.jpg}', '{shoes,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 'f', 'f', 'f', 'HOT', 5307, 21943, 'f', '2024-08-18 11:22:07', '2024-08-18 08:03:29', '2024-08-18 03:16:12', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (8, 'rev', 'dry whose', 'phooey lest', '{https://lemanoosh.com/app/uploads/Orange_white-_Helmet_01.jpg}', '{shoes,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 'f', 'f', 'DOWN', 1369, 9662, 'f', '2024-08-18 15:39:50', '2024-08-18 02:26:23', '2024-08-18 23:51:00', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (10, 'mosque zowie anenst', 'once', 'concerning unblock until', '{https://lemanoosh.com/app/uploads/BO_2019_A1_Natural_Brushed_05-768x1156.jpg}', '{speaker,brushed,mechanical}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 't', 't', 'EXP', 24640, 7845, 'f', '2024-08-18 20:57:09', '2024-08-18 09:46:06', '2024-08-18 04:40:17', NULL, NULL);
INSERT INTO "public"."product" ("id", "title", "trans_code", "description", "images", "keywords", "metadata", "guide_price", "price", "show_price", "discount", "stock", "is_physical", "is_available", "is_featured", "status", "sell_count", "stock_count", "shelves_status", "shelves_date", "created_at", "updated_at", "brand_id", "categories_id") VALUES (9, 'abcd', 'hence', 'afore meanwhile', '{https://lemanoosh.com/app/uploads/bkid-pipe-01.jpg,https://images.pexels.com/photos/19153799/pexels-photo-19153799.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2,https://images.pexels.com/photos/19400633/pexels-photo-19400633.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2}', '{pipe,brushed,wood}', 'JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.', 1.00, 2.00, 3.00, 90, 100, 't', 't', 't', 'PRE', 16476, 1593, 't', '2024-08-18 16:02:21', '2024-08-18 19:07:09', '2024-08-18 04:35:51', NULL, NULL);
COMMIT;

-- ----------------------------
-- Primary Key structure for table product
-- ----------------------------
ALTER TABLE "public"."product" ADD CONSTRAINT "product_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table product
-- ----------------------------
ALTER TABLE "public"."product" ADD CONSTRAINT "fk_product__brand_id" FOREIGN KEY ("brand_id") REFERENCES "public"."brand" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."product" ADD CONSTRAINT "fk_product__categories_id" FOREIGN KEY ("categories_id") REFERENCES "public"."category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
