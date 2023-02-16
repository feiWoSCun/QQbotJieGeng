package cn.jiegeng.luobin.mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
/**
 * @author: feiWoSCun
 * @Time: 2023/2/14
 * @Email: 2825097536@qq.com
 */
@Repository
public interface WeatherMapper {
    /**
     * 查找城市
     * @param s
     * @return
     */
    String getCity(@Param("cityName") String s);
}
