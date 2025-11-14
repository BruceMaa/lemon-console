-- liquibase formatted sql

-- changeset BruceMaa:1
-- comment 初始化表数据

-- 初始化药品管理菜单信息
INSERT INTO sys_menu VALUES(2034, '药品管理', 0, 1, '/medicine', 'Medicine', 'Layout', '/medicine/infos', 'bug', false, false, false, NULL, 999, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2035, '药品信息', 2034, 2, '/medicine/infos', 'MedicineInfos', 'medicine/infos/index', NULL, 'bookmark', false, false, false, NULL, 999, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2036, '列表', 2035, 3, NULL, NULL, NULL, NULL, NULL, false, false, false, 'medicine:infos:list', 1, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2037, '详情', 2035, 3, NULL, NULL, NULL, NULL, NULL, false, false, false, 'medicine:infos:get', 2, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2038, '修改', 2035, 3, NULL, NULL, NULL, NULL, NULL, false, false, false, 'medicine:infos:update', 3, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2039, '删除', 2035, 3, NULL, NULL, NULL, NULL, NULL, false, false, false, 'medicine:infos:delete', 4, 1, 1, NOW(), NULL, NULL);
INSERT INTO sys_menu VALUES(2040, '新增', 2035, 3, NULL, NULL, NULL, NULL, NULL, false, false, false, 'medicine:infos:create', 5, 1, 1, NOW(), NULL, NULL);

-- 初始化药品信息
INSERT INTO medicine_base_info
VALUES(1, 'XJ01XDR005E001010303182', '人工牛黄甲硝唑胶囊', 'Galculus Bovis and Metronidazole Capsules', 'rengongniuhuangjiaxiaozuojiaonang', ' 本品为胶囊剂，内容物为黄色或微黄色的颗粒或粉末。', '胶囊剂', '每粒含甲硝唑0.2g，人工牛黄5mg。', '粒', '每板20粒', '遮光，密闭保存。', '24个月。', 1, NOW(), NULL, NULL);
INSERT INTO medicine_base_info
VALUES(2, 'XA02ABN057A001010305849', '尿囊素铝片', ' Aldioxa Tablets', 'niaonangsulvpian', '本品为白色片。', '片剂', '0.1g', '克', '16片/盒', '密封保存。', '24个月', 1, NOW(), NULL, NULL);
INSERT INTO medicine_base_info
VALUES(3, 'ZA12HAY0555010100326', '银杏叶片', 'Ginkgo Biloba Leaves Extract Tablets', 'yinxingyepian', '本品为薄膜衣片，除去包衣后显浅棕黄色至棕褐色；味微苦。', '片剂(薄膜衣片)', ' 每片含总黄酮醇苷9.6mg,萜类内酯2.4mg', '毫克', '药用铝塑泡罩包装；每盒30片', '密封。', '24个月。', 1, NOW(), NULL, NULL);

INSERT INTO medicine_base_ext
VALUES(1, '["甲硝唑0.2g","人工牛黄5mg"]'::json, NULL, '["口服"]'::json, NULL, '甲硝唑对大多数厌氧菌具强大抗菌作用。抗菌谱包括脆弱拟杆菌属和其他拟杆菌属、梭形杆菌、产气梭状芽孢杆菌，真杆菌，韦容球菌，消化球菌和消化链球菌等。其杀菌浓度稍高于抑菌浓度。甲硝唑的杀菌机制尚未完全阐明，厌氧菌的硝基还原酶在敏感菌株的能量代谢中起重要作用。本品的硝基还原成一种细胞毒，从而作用于细菌的DNA代谢过程，促使细菌死亡。耐药菌往往缺乏硝基还原酶因而对甲硝唑耐药。人工牛黄具解热抗炎作用。', '甲硝唑口服吸收良好，生物利用度80%以上。口服0.25g、0.5g和2g后的血药峰浓度(Cmax)分别为6mg/L、12mg/L和40mg/L，达峰时间(tmax)为1～2小时。广泛分布于各组织和体液中，且能通过血-脑脊液屏障。唾液、胆汁、乳汁、羊水、精液、尿液、脓液和脑脊液等中药物的浓度均与同期血药浓度相近，并都能达到有效浓度。蛋白结合率小于20%。部分在肝脏代谢。代谢物也具有抗菌作用。血消除半衰期(t1/2)为7～8小时，60%～80%经肾排泄，其中20%为原形，其余为代谢物(25%为葡萄糖醛酸结合物，14%为其他代谢结合物)。10%随粪便排泄，14%从皮肤排泄。', '主要用于急性智齿冠周炎、局部牙槽脓肿、牙髓炎、根尖周炎等。', '口服。一次2粒，一日3次。', '1.对甲硝唑或吡咯类药物过敏患者禁用。2.有活动性中枢神经疾病和血液病患者禁用。3.孕妇禁用。4.饮酒者禁用。', '1.致癌、致突变作用：动物试验或体外测定发现本品具致癌、致突变作用，但人体中尚未证实。2.使用中发生中枢神经系统不良反应，应及时停药。3.本品可干扰丙氨酸氨基转移酶、乳酸脱氢酶、三酰甘油、己糖激酶等的检验结果，使其测定值降至零。4.用药期间不应饮用含酒精的饮料，因可引起体内乙醛蓄积、干扰酒精的氧化过程，导致双硫仑样反应，患者可出现腹部痉挛、恶心、呕吐、头痛、面部潮红等。5.肝功能减退者本品代谢减慢，药物及其代谢物易在体内蓄积，应减量使用，并作血药浓度监测。6.本品可自胃液持续清除，某些放置胃管作吸引减压者，可引起血药浓度下降。血液透析时，本品及代谢物迅速被清除，故应用本品不需减量。7.念珠菌感染者应用本品，其症状会加重，需同时给抗真菌治疗。8.厌氧菌感染合并肾功能衰竭患者，给药间隔时间应由8小时延长到12小时。9.治疗阴道滴虫病时，需同时治疗其性伴侣。10.重复一个疗程前，应做白细胞计数。11.严禁用于食品和饲料加工。', '1.本品最严重不良反应为高剂量时可引起癫痫发作和周围神经病变，后者主要表现为肢端麻木和感觉异常。某些病例长期用药时可产生持续性周围神经病变。2.其它常见的不良反应有：(1)胃肠道反应，如恶心、食欲减退、呕吐、腹泻、腹部不适、味觉改变、口干、口腔金属味等。(2)可逆性粒细胞减少。(3)过敏反应，皮疹、荨麻疹、瘙痒等。(4)中枢神经系统症状，如头痛、眩晕、晕厥、感觉异常、肢体麻木、共济失调和精神错乱等。(5)其他有发热、阴道念珠菌感染、膀胱炎、排尿困难、尿液颜色发黑等，均属可逆性，停药后自行恢复。', NULL);
INSERT INTO medicine_base_ext
VALUES(2, '["尿囊素铝"]'::json, NULL, '["口服"]'::json, NULL, '本品服后覆盖在胃肠道壁上，中和胃酸，并游离出具有促进肉芽组织形成及粘膜上皮组织再生的母体化学成份，产生抗溃疡效果；可改善胃粘膜微小血管新生及血流，促进粘液的合成分泌，从而达到预防和治疗粘膜损伤的目的。', '大白鼠口服本品后，在消化器内被水解为尿囊素和氢氧化铝。尿囊素大部分被吸收，血药浓度在给药后0.5～1小时达峰值。投药0.5小时后，脏器组织内分布达到最高，按峰值大小依次为胃、肠、肾、肝。投药48小时内，投药量的52.3%以CO2从呼出气排出，尿、粪、胆汁中的排泄分别为19.2%、2.0%和0.68%。', '适用于胃及十二指肠溃疡。', '饭前口服，成人每次0.2g(2片)，每日3次，或遵医嘱。', '接受透析疗法的患者禁用本品。', '1.肾功能不全患者慎用，接受透析疗法的患者禁用。2.忌与四环素类抗生素合用。', '偶有便秘、稀便、口干等症状，停药后自行消失。', NULL);
INSERT INTO medicine_base_ext
VALUES(3, NULL, NULL, '["口服"]'::json, NULL, NULL, NULL, '活血化瘀通络。用于瘀血阻络引起的胸痹、心痛、中风、半身不遂、舌强语蹇；冠心病稳定型心绞痛、脑梗死见上述证候者。', '口服。一次2片，一日3次；或遵医嘱。', '对本品过敏者禁用。', '心力衰褐者，孕妇慎用。', '偶有胃部不适。', NULL);