package client.commen;

import mp.dao.PrimaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @description: Mapper工具
 * @author: liyue
 * @date: 2020/12/30 15:17
 */
@Component
public class MapperUtils {
    @Autowired
    private  PrimaryMapper primaryMapper;
    public static MapperUtils mapperUtils;

    public void setPrimaryMapper(PrimaryMapper primaryMapper) {
        this.primaryMapper = primaryMapper;
    }

    @PostConstruct
    public void init(){
        mapperUtils = this;
        mapperUtils.primaryMapper = this.primaryMapper;
    }

    //此方法可供其他类静态方法调用
    public List<Map<String,Object>> getList(String sql) {

        return mapperUtils.primaryMapper.getList(sql);
    }

}
