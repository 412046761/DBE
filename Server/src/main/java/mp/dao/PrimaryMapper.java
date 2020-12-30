package mp.dao;

import jdk.nashorn.internal.runtime.regexp.joni.constants.TargetInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description: 主键校验
 * @author: liyue
 * @date: 2020/7/29 17:24
 */
@Mapper
public interface PrimaryMapper {

    /**
     * 动态查询语句
     * @param sql
     * @return
     */
    @Select("<script>${sql}</script>")
    @Options(flushCache = Options.FlushCachePolicy.FALSE,useCache = false,timeout = 10000)
    List<Map<String,Object>> getList(@Param("sql") String sql);
}
