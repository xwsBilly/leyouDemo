import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Country
 *
 * @author xieweisong
 * @date 2019/8/1 14:45
 */
@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {

    // @XmlElementWrapper注解表示生成一个包装器元素，仅允许出现在集合属性上。
    @XmlElementWrapper(name = "provinceList")
    // @XmlElement注解指定一个字段或get/set方法映射到XML的节点，并且可以通过name属性改变java对象属性在XML节点中的名字。
    @XmlElement(name="province-name")
    private List<String> provinces;

    public class Province{

    }
}
