import com.leyou.common.utils.JAXBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TestJaxb
 *
 * @author xieweisong
 * @date 2019/8/1 14:45
 */
public class TestJaxb {

    public static void main(String[] args) {
        Country country = new Country();
        List<String> province = new ArrayList<>();
        province.add("zhejiang");
        province.add("jiangsu");
        country.setProvinces(province);
        String s = JAXBUtils.convertToXml(country);
        System.out.println(s);
        System.out.println("------------");
        Country country1 = JAXBUtils.convertToJavaBean(s, Country.class);
        System.out.println(country1);
    }
}
