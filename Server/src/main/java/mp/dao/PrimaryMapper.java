package mp.dao;

import org.apache.ibatis.annotations.*;

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
     * 校验主键一致性
     * @param sql
     * @return
     */
    @Select("<script>${sql}</script>")
    @Options(flushCache = Options.FlushCachePolicy.FALSE,useCache = false,timeout = 10000)
    List<Map<String,String>> getList(@Param("sql") String sql);
    /**
     * 插入数据
     * @param sql
     * @return
     */
    @Insert("<script>${sql}</script>")
    @Options(flushCache = Options.FlushCachePolicy.FALSE,useCache = false,timeout = 10000)
    void insert70(@Param("sql") String sql);
    /**
     * 校验数目一致性
     * @param sql
     * @return
     */
    @Select("<script>${sql}</script>")
    @Options(flushCache = Options.FlushCachePolicy.FALSE,useCache = false,timeout = 10000)
    Integer checkCountDB70(@Param("sql") String sql);

}
