package mp;

import mp.dao.PrimaryMapper;
import org.ho.yaml.Yaml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 测试
 * @author: liyue
 * @date: 2020/11/17 14:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Resource
    private PrimaryMapper mapper;

    @Test
    public void getListTest(){
        List<Map<String,Object>> rslist = mapper.getList(" select * from user where id ='1087982257332887553' ");
        rslist.forEach(System.out::println);
    }
    //读取yaml配置文件Map结构
    @Test
    public  void  read2() throws FileNotFoundException {
        File dumpFile=new File(System.getProperty("user.dir") + "/src/main/resources/application.yml");
        Map father = Yaml.loadType(dumpFile, HashMap.class);
        for(Object key:father.keySet()){
            System.out.println(key+":\t"+father.get(key).toString());
        }
    }
}
