
-- ----------------------------
-- Table structure for pay_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_config`;
CREATE TABLE `pay_config`  (
                               `pay_config_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付配置ID',
                               `application_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名字',
                               `pay_platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付平台（alipay支付宝，wx微信）',
                               `pay_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式（alipay_app=支付宝APP支付，\r\nalipay_pc=支付宝pc支付，alipay_wap=支付宝wap支付，JSAPI=微信公众号小程序支付，MWEB=微信H5支付，NATIVE=微信Native支付，APP=微信APP支付\r\n）',
                               `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'APP_ID',
                               `app_secret` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'APP_SECRET',
                               `private_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支付宝应用私钥',
                               `notify_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支付回调地址',
                               `cert_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支付宝应用公钥证书路径（appCertPublicKey_2021.crt）',
                               `public_cert_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支付宝公钥证书路径（alipayCertPublicKey_RSA2.crt）',
                               `root_cert_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '支付宝根证书路径（alipayRootCert.crt）',
                               `mch_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付商户号',
                               `mch_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信支付商户秘钥',
                               `sub_app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务商模式下的子商户公众账号ID',
                               `sub_mch_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '服务商模式下的子商户号',
                               `key_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信支付证书的位置',
                               `apiclient_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信支付apiclient_key.pem证书文件（暂时不用）',
                               `apiclient_cert` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信支付apiclient_cert.pem证书文件（暂时不用）',
                               `api_v3_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'apiV3秘钥值（暂时不用）',
                               `cert_serial_no` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'apiV3证书序列号值（暂时不用）',
                               `start_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否启用（1启用 0关闭）',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               PRIMARY KEY (`pay_config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付配置2.0版' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pay_config
-- ----------------------------
INSERT INTO `pay_config` VALUES ('1411237385693298688', '网来网去APP', 'wx', 'APP', 'wx76f6d5ecd6056697', 'GyUYanLwsiYOHmKjHKaIESaIfnkApzs3', '', 'http://2ql7515607.qicp.vip', '', '', '', '16110011480', 'u2TYEaBQr6ACOUxDcWA0L8IyzPs1hDPw8', NULL, NULL, 'D:/wlwq/cert/apiclient_cert_20211703161758.p12', 'D:/wlwq/cert/apiclient_key_20212005152053.pem', 'D:/wlwq/cert/apiclient_cert_20212105152101.pem', 'GyUYanLwsiYOHmKjHKaIESaIfnkApzs3', '1BD3148B0E3D08F7464386A03DB5552713205107C', 1, '2022-05-19 11:09:39', '2021-07-03 16:16:27');
INSERT INTO `pay_config` VALUES ('1412327217718886400', '网来网去APP', 'alipay', 'alipay_app', '20210021148659928', '', 'MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeQjKy/D0HNW6hjnV024RjiTbbdzLssxVmNFD+5Ogp1x+w/iTQq44p8xMAs61s6+VrJ9ICeARyWtjmwZZ7595MAWahv1q08mGxQiCth/wnR0tPiDbtTMk2+klI06RhQ3MDNDFUtPmY+nnb8ldwlwq/KXHYYv2XyyNMAkOjLtk3Zmuj8RCzfa2rB571QeXlgj237373I7usgdTXJXUnmT0IxxumP7pO1QsJj8lxV+ZQJD7nmGkTKx2Fd5eKgOlWR53Acdnctz7rBP4GcSV10232mWxRyJzFOYid8wPnReH1qXtwqqh3UJP6N0aI1k1667qkI9UxNXZSDo0m8fj4VMWmDAgMBAAECggEAWWpFguMJCB2kWBtJObEdZ6+LEwyR9TJpkMlWSe3DToCBI1s0KcIEHKd9wRPDlJrjkFt7SHHO8V1q11XhVDh5XLAd8J1Q/pNDpwcZt3Hxga/CWTk473JG7Y0UAtWxNlMJlHHUvWtS6p/6Yc7Wimlv8kGzZ+AcFcRpkxRMs2xZJ+x+cuxdHmjRwuV5smxZ0VFTbpUQolTz/ADEaTlK5iDSpIpyG+VJD+XA6BpeD/9cBm2fDUl+pcR3B2meKwxytF+JcYzmNCIvIS+FgfpntdqkgzUMaXRVYtgC5EdCvKInGa5mf5hfHrFWVG6KTQvdBphMlU3yUYpjqpS8BiyNf4M74QKBgQDWq+6AB2M4vC1WNyZ7bmMRQbvonvKYhexg3mgTOdrRe7JuidwfgeYTkZktA1bfLF3vPTMabuHR6SMgUt71Yt86w/eEZWQnmEqCj235LgI14GdVb8x0vSlPFEDDkzJB3Ra22VlS4yXL/QILBZ8YEaheN6uxfvUm2xMo99dXElwINwKBgQC8ufTT9MeQqKzZCYToObjX2MAWYMlPsXuVDJoXH6OH7osg9rNCeB6z+vNYWJli/d5qiheBpHgKxPuUHTg247ZyyXocSRhW0loHMrB1uaCZc9yvSLP1dXJwLOrpJH504Z5RFNCYHlHgzlYx6yJctnTsgPXsjqipeA3F7gwQ0xirFQKBgQCKY9JgFOMhKyarAui6/8G72T2TTAymyQdGt3ouksh+7Zhs7FDMuwggq2cs/o8dVlPELBbqvnqzwPEVIHd9h9zS3IyPfHGhmOrH2kESN1fTdbQH/we2Zk/gG/VYX2cx0J6ZB+supSKzq4qKongaox+AhbEYvvi5sNdcPNJE3qAX5wKBgHNwL3BivpsegbO0HebKP+VPgJdvllO31UbjUXGpeabQ46tDG30h6m3ep3C3qQMjFCQI2b4D8yVrPhNoGBWDDfuCf73DfC7HNihG+ibYZ4LVmvxgtSsELv6sivgWyX8G7obb+4fArAadT53zHK6nMDfl3gdXb5VRiOMz649zABjNAoGATVzmvybkEwSKv7QiVJ1yleOwBgrTbpPYYCTTDM3SemlZ8cobR8XOTdxdH5i/XwQd4QVi1XhrcdG9muuzSxyeebvRix2PK0uxU7RsLJzCyRWkXmCg39SQwoROv4irVmB5f4gW+eKyQG+G/cXdZ6+fnJdqhS+NMEQeBA1V7sL2VBwM=', 'http://2ql7515607.qicp.vip', 'D:/wlwq/cert/appCertPublicKey_2021002148659928_20213606163639.crt', 'D:/wlwq/cert/alipayCertPublicKey_RSA2_20213606163650.crt', 'D:/wlwq/cert/alipayRootCert_20213606163656.crt', '', '', NULL, NULL, '', '', '', '', '', 1, '2022-05-19 11:09:55', '2021-07-06 16:27:03');
