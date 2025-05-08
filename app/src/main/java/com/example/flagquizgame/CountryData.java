package com.example.flagquizgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 提供国家数据的工具类
 */
public class CountryData {

    // 国家数据列表
    private static final List<Country> countries = Arrays.asList(
            new Country("中国", "cn"),
            new Country("美国", "us"),
            new Country("英国", "gb"),
            new Country("法国", "fr"),
            new Country("德国", "de"),
            new Country("日本", "jp"),
            new Country("韩国", "kr"),
            new Country("俄罗斯", "ru"),
            new Country("澳大利亚", "au"),
            new Country("加拿大", "ca"),
            new Country("巴西", "br"),
            new Country("印度", "in"),
            new Country("意大利", "it"),
            new Country("西班牙", "es"),
            new Country("墨西哥", "mx"),
            new Country("荷兰", "nl"),
            new Country("瑞典", "se"),
            new Country("瑞士", "ch"),
            new Country("挪威", "no"),
            new Country("芬兰", "fi"),
            new Country("丹麦", "dk"),
            new Country("比利时", "be"),
            new Country("奥地利", "at"),
            new Country("葡萄牙", "pt"),
            new Country("希腊", "gr"),
            new Country("新西兰", "nz"),
            new Country("阿根廷", "ar"),
            new Country("南非", "za"),
            new Country("埃及", "eg"),
            new Country("泰国", "th"),
            new Country("新加坡", "sg"),
            new Country("马来西亚", "my"),
            new Country("印度尼西亚", "id"),
            new Country("越南", "vn"),
            new Country("菲律宾", "ph"),
            new Country("土耳其", "tr"),
            new Country("以色列", "il"),
            new Country("波兰", "pl"),
            new Country("乌克兰", "ua"),
            new Country("捷克", "cz"),
            new Country("匈牙利", "hu"),
            new Country("爱尔兰", "ie"),
            new Country("冰岛", "is"),
            new Country("沙特阿拉伯", "sa"),
            new Country("阿联酋", "ae"),
            new Country("卡塔尔", "qa"),
            new Country("智利", "cl"),
            new Country("哥伦比亚", "co"),
            new Country("秘鲁", "pe"),
            new Country("尼日利亚", "ng"),
            new Country("肯尼亚", "ke"),
            new Country("摩洛哥", "ma"),
            new Country("伊朗", "ir"),
            new Country("巴基斯坦", "pk"),
            new Country("孟加拉国", "bd"),
            new Country("斯里兰卡", "lk"),
            new Country("蒙古", "mn"),
            new Country("柬埔寨", "kh"),
            new Country("缅甸", "mm"),
            new Country("老挝", "la"),
            new Country("朝鲜", "kp"),
            new Country("罗马尼亚", "ro"),
            new Country("保加利亚", "bg"),
            new Country("塞尔维亚", "rs"),
            new Country("克罗地亚", "hr"),
            new Country("斯洛文尼亚", "si"),
            new Country("斯洛伐克", "sk"),
            new Country("爱沙尼亚", "ee"),
            new Country("拉脱维亚", "lv"),
            new Country("立陶宛", "lt"),
            new Country("白俄罗斯", "by"),
            new Country("摩尔多瓦", "md"),
            new Country("古巴", "cu"),
            new Country("牙买加", "jm")
    );

    /**
     * 获取随机的一组国家（用于生成一轮问题）
     * @param count 需要的国家数量
     * @return 随机选择的国家列表
     */
    public static List<Country> getRandomCountries(int count) {
        List<Country> shuffledCountries = new ArrayList<>(countries);
        Collections.shuffle(shuffledCountries);
        return shuffledCountries.subList(0, Math.min(count, shuffledCountries.size()));
    }

    /**
     * 为一个问题生成选项
     * @param correctCountry 正确答案的国家
     * @param optionCount 需要生成的选项总数
     * @return 包含正确答案和干扰选项的列表
     */
    public static List<Country> generateOptionsForQuestion(Country correctCountry, int optionCount) {
        List<Country> allCountriesCopy = new ArrayList<>(countries);
        allCountriesCopy.remove(correctCountry);
        Collections.shuffle(allCountriesCopy);

        List<Country> options = new ArrayList<>();
        options.add(correctCountry);

        // 添加干扰选项
        for (int i = 0; i < optionCount - 1 && i < allCountriesCopy.size(); i++) {
            options.add(allCountriesCopy.get(i));
        }

        // 随机打乱选项顺序
        Collections.shuffle(options);

        return options;
    }
}
