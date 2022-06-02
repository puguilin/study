package com.guilin.studycode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.entrity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where id =#{id} and password =#{password}")
    User queryByIdAndPassward(int id,String password);

    @SelectProvider(type = UserMapper.Query.class, method = "pageQuery")
    IPage<User> pageQuery(IPage<User> result, @Param("name") String name, @Param("mobile") String mobile);

    public class Query {

        public String pageQuery(Map params) {
            StringBuilder stringBuilder = new StringBuilder(
                    "SELECT * FROM tb_user where 1=1 ");
            String name = (String) params.get("name");
            String mobile = (String) params.get("mobile");

            if (StringUtils.isNotBlank(mobile)) {
                stringBuilder.append(" and mobile =#{mobile}");
            }
            if (StringUtils.isNotBlank(name)) {
                stringBuilder.append(" and name like concat('%', #{name}, '%')");
            }
            return stringBuilder.toString();
        }

    }

    //获取seq_test中的序列id
    @Select("select nextval(#{tableName})")
    String getId(String tableName);

}
