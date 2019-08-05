package com.leyou.common.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * JAXBUtils
 *
 * @author xieweisong
 * @date 2019/8/1 14:43
 */
public class JAXBUtils {
    /**
     * 以UTF-8编码的格式将JavaBean转成XML
     *
     * @param o
     * @return
     */
    public static String convertToXml(Object o) {
        return convertToXml(o, "UTF-8");
    }

    /**
     * 将JavaBean转成XML
     *
     * @param obj      JavaBean对象
     * @param encoding xml编码格式，例,"UTF-8"
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = "";
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将XML转成JavaBean
     *
     * @param xml xml字符串
     * @param cls JavaBean的class名称 例,Object.class
     * @param <T>
     * @return 返回转换后的JavaBean
     */
    public static <T> T convertToJavaBean(String xml, Class<T> cls) {
        T t = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return t;
    }
}
