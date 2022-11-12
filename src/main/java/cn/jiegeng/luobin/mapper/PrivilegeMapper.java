package cn.jiegeng.luobin.mapper;

import cn.jiegeng.luobin.domain.vo.UserPri;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrivilegeMapper {
    /**
     * 添加number
     * @param number
     * @return
     */
    int addPrivilege(@Param("number") String number);

    /**
     * 得到所有的权限
     * @return
     */
    List<UserPri> getAllQQNumberAndItsPri();
}
