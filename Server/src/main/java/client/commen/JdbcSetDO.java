package client.commen;

import lombok.Data;

/**
 * @description: JdbcSetDO
 * @author: liyue
 * @date: 2020/11/23 15:01
 */
@Data
public class JdbcSetDO {
    private String driver;
    private String url;
    private String username;
    private String password;
}
